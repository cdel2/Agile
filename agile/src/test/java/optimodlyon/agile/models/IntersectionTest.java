package optimodlyon.agile.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class IntersectionTest {
	
	@Test
	public void testIntersectionLongFloatFloat() {
		long id=1;
		float latitude=-50;
		float longitude=50;
		Intersection myInter = new Intersection(id,latitude,longitude);
		assertNotNull(myInter);
	    assertSame(myInter.getId(),id);
		assertEquals(myInter.getLatitude(),-50,0);
		assertEquals(myInter.getLongitude(),50,0);
	}

	@Test
	public void testIntersectionLong() {
		long id=1;
		Intersection myInter = new Intersection(id);
		assertNotNull(myInter);
		assertSame(myInter.getId(),id);
	}
	
	@Test
	public void testIntersectionIntersection() {
		Intersection i0 = new Intersection((long)0,(float)5.0,(float)3.0);
		Intersection myInter=new Intersection(i0);
		assertSame(myInter.getId(),i0.getId());
		assertEquals(myInter.getLatitude(),i0.getLatitude(),0);
		assertEquals(myInter.getLongitude(),i0.getLongitude(),0);
	}

	@Test
	public void testGetLatitude() {
		long id=1;
		float latitude=-50;
		float longitude=50;
		Intersection myInter = new Intersection(id,latitude,longitude);
		assertEquals(myInter.getLatitude(),-50,0);
	}

	@Test
	public void testSetLatitude() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		assertEquals(origin.getLatitude(),-50,0);
		origin.setLatitude(100);
		assertEquals(origin.getLatitude(),100,0);
	}

	@Test
	public void testGetLongitude() {
		long id=1;
		float latitude=-50;
		float longitude=50;
		Intersection myInter = new Intersection(id,latitude,longitude);
		assertEquals(myInter.getLongitude(),50,0);
	}

	@Test
	public void testSetLongitude() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		assertEquals(origin.getLongitude(),50,0);
		origin.setLongitude(-100);
		assertEquals(origin.getLongitude(),-100,0);
	}

	@Test
	public void testGetId() {
		long id=1;
		float latitude=-50;
		float longitude=50;
		Intersection myInter = new Intersection(id,latitude,longitude);
		assertSame(myInter.getId(),id);

	}

	@Test
	public void testSetId() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		assertEquals(origin.getId(),1,0);
		origin.setId((long)-100);
		assertEquals(origin.getId(),-100,0);
	}
	
	@Test
	public void testDistance() {
		Intersection i0 = new Intersection((long)0,(float)0,(float)0);
		Intersection i1 = new Intersection((long)0,(float)0,(float)3.0);
		double distance=3;
		double test=Intersection.distance(i0,i1);
		assertEquals(test,distance,0);
    }

	@Test
	public void testFindSuccessorSegments() {
		Map<Long, List<Segment>>completeMap = new HashMap<Long, List<Segment>>();
        Intersection i0 = new Intersection((long)0,(float)5.0,(float)3.0);
        Intersection i1 = new Intersection((long)1,(float)3.0,(float)3.0);
        Intersection i2 = new Intersection((long)2,(float)4.0,(float)3.0);
        Intersection i3 = new Intersection((long)3,(float)1.0,(float)3.0);
        Intersection i4 = new Intersection((long)4,(float)1.0,(float)3.0);
        Intersection i5 = new Intersection((long)5,(float)1.0,(float)3.0);
        Segment s0 = new Segment(i0,i1,2);
        Segment s1 = new Segment(i1,i3,3);
        Segment s2 = new Segment(i0,i3,4);
        Segment s3 = new Segment(i0,i2,4);
        Segment s4 = new Segment(i2,i3,1);
        Segment s5 = new Segment(i2,i4,3);
        Segment s6 = new Segment(i3,i4,20);
        Segment s7 = new Segment(i3,i5,15);
        List<Segment> a0 = new ArrayList<Segment>();
        List<Segment> a1 = new ArrayList<Segment>();
        List<Segment> a2 = new ArrayList<Segment>();
        List<Segment> a3 = new ArrayList<Segment>();
        List<Segment> a4 = new ArrayList<Segment>();
        List<Segment> a5 = new ArrayList<Segment>();
        a0.add(s0);
        a0.add(s2);
        a0.add(s3);
        a1.add(s1);
        a2.add(s4);
        a2.add(s5);
        a3.add(s6);
        a3.add(s7);
        completeMap.put((long)0, a0);
        completeMap.put((long)1, a1);
        completeMap.put((long)2, a2);
        completeMap.put((long)3, a3);
        completeMap.put((long)4, a4);
        completeMap.put((long)5, a5);
		MapManagement.getInstance().getMap().setGraph(completeMap);
		
		List<Intersection> answer = new ArrayList<Intersection>();
		answer.add(i1);
		answer.add(i3);
		answer.add(i2);
		for(int i=0;i<answer.size();i++) {
			assertSame(i0.findSuccessorSegments().get(i),answer.get(i));
		}
	}
	
	@Test
	public void testAngle() {
		Intersection i0 = new Intersection((long)0,(float)0,(float)0);
        Intersection i1 = new Intersection((long)1,(float)0,(float)1.0);
        Intersection i2 = new Intersection((long)2,(float)1.0,(float)0.0);
        Intersection i3 = new Intersection((long)3,(float)1.0,(float)1.0);
        Intersection i4 = new Intersection((long)4,(float)3.0,(float)3.0);
        assertEquals(Intersection.angle(i0,i1,i2),Math.PI/2.0,0.0001);
        assertEquals(Intersection.angle(i0,i3,i4),0,0.0001);
	}

	@Test
	public void testToString() {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		assertEquals(origin.toString(),"Intersection id : 1 lat : -50.0 long : 50.0");
	}

}
