package telran.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import telran.util.*;

class IntRangeIterator implements Iterator<Integer> {
	int max;
	int nextValue;

	public IntRangeIterator(int min, int max) {
		super();
		if (max < min || max + 1 <= min) { // avoid invalid order and overflow
			throw new IllegalArgumentException();
		}
		this.nextValue = min;
		this.max = max;
	}

	@Override
	public boolean hasNext() {
		return nextValue < max;
	}

	@Override
	public Integer next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return nextValue++;
	}
}

class FilteredIteratorTests {
	private IntRangeIterator intIter;
	private FilteredIterator<Number> filteredIter;
    @Test
	void allValuesPassedTest() {
    	intIter = new IntRangeIterator(0, 10);
		filteredIter = new FilteredIterator<>(intIter, i -> true);
		Integer[] expected = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Integer[] res = setResult(filteredIter);
		assertArrayEquals(expected, res);
    }
	@Test
	void noValuesPassedTest() {
		intIter = new IntRangeIterator(1, 1);
		filteredIter = new FilteredIterator<>(intIter, i -> false);
		Integer[] expected = {};
		Integer[] res = setResult(filteredIter);
		assertArrayEquals(expected, res);		
	}
	@Test
	void firstButNotLastValuesPassedTest() {
		intIter = new IntRangeIterator(0, 10);
		filteredIter = new FilteredIterator<>(intIter, i -> (int)i % 2 == 0);
		Integer[] expected = { 0, 2, 4, 6, 8 };
		Integer[] res = setResult(filteredIter);
		assertArrayEquals(expected, res);
	}
	@Test
	void notFirstButLastValuesPassedTest() {
		intIter = new IntRangeIterator(1, 11);
		filteredIter = new FilteredIterator<>(intIter, i -> (int)i % 2 == 0);
		Integer[] expected = { 2, 4, 6, 8, 10 };
		Integer[] res = setResult(filteredIter);
		assertArrayEquals(expected, res);
	}
	@Test
	void firstAndLastValuesDontPassTest() {
		intIter = new IntRangeIterator(1, 10);
		filteredIter = new FilteredIterator<>(intIter, i -> (int)i % 2 == 0);
		Integer[] expected = { 2, 4, 6, 8 };
		Integer[] res = setResult(filteredIter);
		assertArrayEquals(expected, res);
	}
	
	@Test
	void nullSupportTest() {
		Iterator<Integer> Iter = Arrays.asList(1, null).iterator();
		filteredIter = new FilteredIterator<>(Iter, i -> true);
		Integer[] expected = { 1, null };
		Integer[] res = setResult(filteredIter);
		assertArrayEquals(expected, res);
	}

	private Integer[] setResult(FilteredIterator<Number> filteredIter) {
		Integer[] res = {};
		int index = 0;
		while (filteredIter.hasNext()) {
			res = Arrays.copyOf(res, res.length + 1);
			res[index++] = (Integer) filteredIter.next();
		}
		return res;
	}
	

}
