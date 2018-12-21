package optimodlyon.agile.algorithmic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class IteratorSeqTest {
	IteratorSeq myIterator;
	IteratorSeq myIterator2;

	List<Long> myList;

	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myList = new ArrayList<Long>();
		myList.add((long)1);
		myList.add((long)2);
		myList.add((long)3);
		myList.add((long)4);
		myIterator= new IteratorSeq(myList,(long)4);
		myIterator2= new IteratorSeq(myList,(long)4);


	}
	
	@Test
	public final void testHasNextTrue() throws Exception
	{
		assertTrue(myIterator.hasNext());
	}
	
	@Test
	public final void testHasNextFalse() throws Exception
	{
		myIterator.next();
		myIterator.next();
		myIterator.next();
		myIterator.next();
		assertTrue(!(myIterator.hasNext()));
	}
	
	@Test
	public final void testNext() throws Exception
	{
		long expectedValue = 4;
		assertEquals(expectedValue, myIterator2.next(),0);
	}
}
