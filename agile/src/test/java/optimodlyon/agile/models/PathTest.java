package optimodlyon.agile.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class PathTest {

	@Test
	public void testPath() {
		Path myPath= new Path();
		assertEquals(myPath.getDuration(),0,0);
		assertEquals(myPath.getListSegment().isEmpty(),true);
		assertEquals(myPath.getArrival(),null);
	}


	@Test
	public void testPathListOfLongDeliveryTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSegmentsPassageTimes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDepartureTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindStart() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDuration() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetArrival() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPath() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDuration() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetArrival() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindEnd() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddSegment() {
		fail("Not yet implemented");
	}

}
