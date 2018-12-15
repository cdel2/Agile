package optimodlyon.agile.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import optimodlyon.agile.util.Time;

public class DeliveryTest {
	Delivery myDelivery,myDelivery2,myDelivery3,myDelivery4,myDelivery5,myDeliveryLatLong;
	Time myTime;
	float myDuration;
	long myId;
	float myLatitude;
	float myLongitude;
	Intersection myIntersection;
	
	@Before
	public void setUp() throws Exception {
		myTime=new Time(12,30,50);
		myDuration=30;
		myId=1;
		myLatitude=5;
		myLongitude=10;
		myDelivery=new Delivery(myTime,myDuration,myId,myLatitude,myLongitude);
		myDelivery2=new Delivery(myId,myDuration);
		myDelivery3=new Delivery(myDelivery);
		myIntersection= new Intersection(myId, myLatitude, myLongitude);
		myDelivery4=new Delivery(myIntersection,myDuration);
		myDelivery5=new Delivery(myId,myTime);
		myDeliveryLatLong=new Delivery((long) 10,myTime);
		
	}

	@Test
	public void testToString() {
		assertEquals("Delivery id : 1 lat : 5.0 long : 10.0 timeArrival : 12:30:50", myDelivery.toString());
	}

	@Test
	public void testDeliveryTimeFloatLongFloatFloat() {
		assertNotNull(myDelivery);
		assertEquals(myDelivery.getId(),myId,0);
		assertEquals(myDelivery.getDuration(),myDuration,0);
		assertEquals(myDelivery.getLatitude(),myLatitude,0);
		assertEquals(myDelivery.getLongitude(),myLongitude,0);
		assertEquals(myDelivery.getTimeArrival(),myTime);
	}

	@Test
	public void testDeliveryLongFloat() {
		assertNotNull(myDelivery2);
		assertEquals(myDelivery.getId(),myId,0);
		assertEquals(myDelivery.getDuration(),myDuration,0);
	}

	@Test
	public void testDeliveryDelivery() {
		assertNotNull(myDelivery3);
		assertEquals(myDelivery3.getId(),myDelivery.getId());
		assertEquals(myDelivery3.getDuration(),myDelivery.getDuration(),0);
		assertEquals(myDelivery3.getLatitude(),myDelivery.getLatitude(),0);
		assertEquals(myDelivery3.getLongitude(),myDelivery.getLongitude(),0);
		assertEquals(myDelivery3.getTimeArrival(),myDelivery.getTimeArrival());
	}

	@Test
	public void testDeliveryIntersectionFloat() {
		assertNotNull(myDelivery4);
		assertEquals(myDelivery4.getId(),myIntersection.getId());
		assertEquals(myDelivery4.getDuration(),myDelivery.getDuration(),0);
		assertEquals(myDelivery4.getLatitude(),myIntersection.getLatitude(),0);
		assertEquals(myDelivery4.getLongitude(),myIntersection.getLongitude(),0);
	}

	@Test
	public void testDeliveryLongTime() {
		assertNotNull(myDelivery5);
		assertEquals(myDelivery5.getId(),myId,0);
		assertEquals(myDelivery5.getTimeArrival(),myTime);
	}

	@Test
	public void testSetDuration() {
		myDelivery.setDuration(100);
		assertEquals(myDelivery.getDuration(),100,0);
	}

	@Test
	public void testSetTimeArrival() {
		Time myTimeTest= new Time (0,0,30);
		myDelivery.setTimeArrival(myTimeTest);
		assertEquals(myDelivery.getTimeArrival().toString(),myTimeTest.toString());
	}

	@Test
	public void testFindLatitudeLongitude() {
		Map<Long, List<Segment>>completeMap = new HashMap<Long, List<Segment>>();
        Intersection i0 = new Intersection((long)0,(float)5.0,(float)3.0);
        Intersection i2 = new Intersection((long)2,(float)4.0,(float)3.0);
        Intersection i3 = new Intersection((long)3,(float)1.0,(float)3.0);
        Intersection i4 = new Intersection((long)4,(float)1.0,(float)3.0);
        Intersection i5 = new Intersection((long)5,(float)1.0,(float)3.0);
        Segment s0 = new Segment(i0,myDelivery,2);
        Segment s1 = new Segment(myDelivery,i3,3);
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
		Map<Long, List<Segment>> graph = MapManagement.getInstance().getMap().getGraph();
		assertTrue(myDelivery.findLatitudeLongitude(graph));
		assertFalse(myDeliveryLatLong.findLatitudeLongitude(graph));
		
	}

}
