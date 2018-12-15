package optimodlyon.agile.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import optimodlyon.agile.util.Time;

public class PathTest {

	Delivery myDelivery;
	Time myTime;
	Time myDeparture;
	Time myTiming;
	Time myArrival;
	Intersection i3;
	Path myPath;
	Segment s3, s4,s6;
	List<Segment> a0;
	
	
	@Before
	public void setUp() throws Exception {
		Map<Long, List<Segment>>completeMap = new HashMap<Long, List<Segment>>();
        Intersection i0 = new Intersection((long)0,(float)5.0,(float)3.0);
        Intersection i1 = new Intersection((long)1,(float)3.0,(float)3.0);
        Intersection i2 = new Intersection((long)2,(float)4.0,(float)3.0);
        i3 = new Intersection((long)3,(float)1.0,(float)3.0);
        Intersection i4 = new Intersection((long)4,(float)1.0,(float)3.0);
        Intersection i5 = new Intersection((long)5,(float)1.0,(float)3.0);
        Segment s0 = new Segment(i0,i1,2);
        Segment s1 = new Segment(i1,i3,3);
        Segment s2 = new Segment(i0,i3,4);
        s3 = new Segment(i0,i2,4);
        s4 = new Segment(i2,i3,1);
        Segment s5 = new Segment(i2,i4,3);
         s6 = new Segment(i3,i4,20);
        Segment s7 = new Segment(i3,i5,15);
        a0 = new ArrayList<Segment>();
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
    List<Long> listIdInter = new ArrayList<Long>();
    listIdInter.add(i0.getId());
    listIdInter.add(i2.getId());
    listIdInter.add(i3.getId());
	myDelivery= new Delivery(i3, 10);
	myTime = new Time(0,0,30);
	myDeparture= new Time(0,0,30);
	myTiming= new Time(0,0,15);
    myArrival = new Time(0,0,35);
    myPath=new Path(listIdInter,myDelivery,myTime);
	}
	
	@Test
	public void testPath() {
		Path myPath= new Path();
		assertEquals(myPath.getDuration(),0,0);
		assertEquals(myPath.getListSegment().isEmpty(),true);
		assertEquals(myPath.getArrival(),null);
	}
	
	@Test
	public void testPathPath() {
		Path myPath2= new Path(myPath);
		assertEquals(myPath.getDuration(),myPath2.getDuration(),0);
		assertEquals(myPath.getListSegment().toString(),myPath2.getListSegment().toString());
		assertEquals(myPath.getArrival(),myPath2.getArrival());
		assertEquals(myPath.getDepartureTime(),myPath2.getDepartureTime());
	}
	
	@Test
	public void testPathListOfSegmentDeliveryFloatTime() {
		Path myPath= new Path((ArrayList<Segment>) a0,myDelivery,(float)30.0,myDeparture);
		assertEquals(myPath.getDuration(),30.0,0);
		assertEquals(myPath.getListSegment().toString(),a0.toString());
		assertEquals(myPath.getArrival(),myDelivery);
		assertEquals(myPath.getDepartureTime().toString(),myDeparture.toString());
	}


	@Test
	public void testPathListOfLongDeliveryTime() {
    assertNotNull(myPath);
    assertEquals(myPath.getDepartureTime().toString(),myDeparture.toString());
    assertEquals((myPath.getDepartureTime().addTime(myTiming)).toString(),myTime.toString());
    assertSame(myPath.getArrival().getId(),myDelivery.getId());
    assertEquals(myPath.getArrival().getLatitude(),myDelivery.getLatitude(),0);
    assertEquals(myPath.getArrival().getLongitude(),myDelivery.getLongitude(),0);
    assertEquals(myPath.getArrival().getDuration(),myDelivery.getDuration(),0);
    Time test = new Time(0,0,35);
    assertEquals(myPath.getArrival().getTimeArrival().getHours(),test.getHours(),0);
    assertEquals(myPath.getArrival().getTimeArrival().getMinutes(),test.getMinutes(),0);
    assertEquals(myPath.getArrival().getTimeArrival().getSeconds(),test.getSeconds(),0);
    assertEquals(myPath.getDuration(),s3.getDuration()+s4.getDuration(),0);
	}


	@Test
	public void testSetSegmentsPassageTimes() {
    myPath.setSegmentsPassageTimes();
    assertEquals(myPath.getListSegment().get(0).toString(),s3.toString());
    assertEquals(myPath.getListSegment().get(0).getPassageTime().toString(),myDeparture.toString());
    assertEquals(myPath.getListSegment().get(1).getPassageTime().toString(),myDeparture.addTime(s3.getDuration()).toString());
	}

	@Test
	public void testGetDepartureTime() {
    assertEquals(myPath.getDepartureTime().toString(),myDeparture.toString());
	}

	@Test
	public void testFindStartFindEnd() {
    assertEquals(myPath.findStart().toString(),myPath.getListSegment().get(0).getStart().toString());
    assertEquals(myPath.findEnd().toString(),myPath.getListSegment().get(1).getEnd().toString());
	}

	@Test
	public void testGetDuration() {
    assertEquals(myPath.getDuration(),s3.getDuration()+s4.getDuration(),0);
	}

	@Test
	public void testGetArrival() {
    assertEquals(myPath.getArrival().getId(),myDelivery.getId());
    assertEquals(myPath.getArrival().getTimeArrival().toString(),myArrival.toString());
	}

	@Test
	public void testGetListSegment() {
    List<Segment> myList = new ArrayList<Segment>();
    myList.add(s3);
    myList.add(s4);
    assertEquals(myPath.getListSegment().toString(),myList.toString());
	}

	@Test
	public void testSet() {
    myPath.setDuration(30);
    assertEquals(myPath.getDuration(),30,0);
    Delivery myDeliveryTest= new Delivery(i3, 40);
    myPath.setArrival(myDeliveryTest);
    assertEquals(myPath.getArrival(),myDeliveryTest);
    Time myDepartureSet= new Time(0,12,15);
    myPath.setDepartureTime(myDepartureSet);
    assertEquals(myPath.getDepartureTime().toString(),myDepartureSet.toString());
	}
	
	@Test
	public void testToString() {
		assertEquals("start : Intersection id : 0 lat : 5.0 long : 3.0 end : Intersection id : 3 lat : 1.0 long : 3.0 arrival : Delivery id : 3 lat : 1.0 long : 3.0 timeArrival : 0:0:35\n",myPath.toString());
		}

	@Test
	public void testAddSegment() {
		myPath.addSegment(s6);
		assertEquals(myPath.getListSegment().get(2),s6);
	}

}
