package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredIterator<T> implements Iterator<T> {
	private T curr;
	private boolean currIsSet;
	private T checkedValue;
	private Predicate<? super T> filter;
	private Iterator<? extends T> srcIterator;

	public FilteredIterator(Iterator<? extends T> srcIterator, Predicate<? super T> filter) {
		super();
		this.srcIterator = srcIterator;
		this.filter = filter;
		this.currIsSet = false;

	}

	@Override
	public boolean hasNext() {
		return currIsSet || setNext();
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		currIsSet = false;
		return curr;
	}

    private boolean setNext() {
		while (srcIterator.hasNext()) {
			checkedValue = srcIterator.next();
			if (filter.test(checkedValue)) {
				curr = checkedValue;
				currIsSet = true;
				return true;
			}
		}
		return false;
	}

}
