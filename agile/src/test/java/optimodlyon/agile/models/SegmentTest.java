package optimodlyon.agile.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class SegmentTest {

	@Test
	public void testSegment() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		Intersection destination = new Intersection((long)2, (float)50, (float)50);
		Segment mySeg1 = new Segment(origin,destination,100);
		assertNotNull(mySeg1);
		assertNotNull(mySeg1.getStart());
		assertNotNull(mySeg1.getEnd());
		assertNotNull(mySeg1.getDuration());
		assertEquals(mySeg1.getDuration(),100,0);
	}

	@Test
	public void testGetStart() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		Intersection destination = new Intersection((long)2, (float)50, (float)50);
		Segment mySeg1 = new Segment(origin,destination,100);
		assertNotNull(mySeg1.getStart());
		assertEquals(mySeg1.getStart().getId(),1,0);
		assertEquals(mySeg1.getStart().getLatitude(),-50,0);
		assertEquals(mySeg1.getStart().getLongitude(),50,0);
	}

	@Test
	public void testGetDuration() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		Intersection destination = new Intersection((long)2, (float)50, (float)50);
		Segment mySeg1 = new Segment(origin,destination,100);
		assertNotNull(mySeg1.getDuration());
		assertEquals(mySeg1.getDuration(),100,0);
	}

	@Test
	public void testSetDuration() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		Intersection destination = new Intersection((long)2, (float)50, (float)50);
		Segment mySeg1 = new Segment(origin,destination,100);
		assertNotNull(mySeg1.getDuration());
		assertEquals(mySeg1.getDuration(),100,0);
		mySeg1.setDuration(500);
		assertNotNull(mySeg1.getDuration());
		assertEquals(mySeg1.getDuration(),500,0);
	}

	@Test
	public void testGetEnd() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		Intersection destination = new Intersection((long)2, (float)50, (float)50);
		Segment mySeg1 = new Segment(origin,destination,100);
		assertNotNull(mySeg1.getEnd());
		assertEquals(mySeg1.getEnd().getId(),2,0);
		assertEquals(mySeg1.getEnd().getLatitude(),50,0);
		assertEquals(mySeg1.getEnd().getLongitude(),50,0);
	}

	@Test
	public void testToString() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		Intersection destination = new Intersection((long)2, (float)50, (float)50);
		Segment mySeg1 = new Segment(origin,destination,100);
		assertEquals(mySeg1.toString(),"start : Intersection id : 1 lat : -50.0 long : 50.0 end : Intersection id : 2 lat : 50.0 long : 50.0 duration : 100.0");
	}

}
