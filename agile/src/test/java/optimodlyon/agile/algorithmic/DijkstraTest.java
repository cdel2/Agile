/**
 * 
 */
package optimodlyon.agile.algorithmic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Segment;

/**
 * @author etien
 *
 */
public class DijkstraTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
        
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link optimodlyon.agile.algorithmic.Dijkstra#doDijkstra(java.util.HashMap, java.util.ArrayList)}.
	 * @throws Exception 
	 */
	@Test
	public final void testDoDijkstra() throws Exception {
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
        Segment s8 = new Segment(i5,i3,1);
        Segment s9 = new Segment(i3,i0,10);
        Segment s10 = new Segment(i4,i0,2);
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
        a5.add(s8);
        a3.add(s9);
        a4.add(s10);
        completeMap.put((long)0, a0);
        completeMap.put((long)1, a1);
        completeMap.put((long)2, a2);
        completeMap.put((long)3, a3);
        completeMap.put((long)4, a4);
        completeMap.put((long)5, a5);
		MapManagement.getInstance().getMap().setGraph(completeMap);
	    List<Long> listIdInter = new ArrayList<Long>();
	    listIdInter.add(i0.getId());
	    listIdInter.add(i5.getId());
	    listIdInter.add(i3.getId());
	    Dijkstra dij = new Dijkstra();
	    Map<Long, Map<Long, Float>> tspG = dij.doDijkstra(MapManagement.getInstance().getMap().getGraph(), listIdInter);
	    Assertions.assertThat(tspG).containsKeys((long)0,(long)5,(long)3);
	}

	/**
	 * Test method for {@link optimodlyon.agile.algorithmic.Dijkstra#doDijkstra(java.util.HashMap, java.util.ArrayList)}.
	 * @throws Exception 
	 */
	@Test
	public final void testOneWayRoad() throws Exception {
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
        Segment s8 = new Segment(i5,i3,1);
        Segment s9 = new Segment(i3,i0,10);
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
        a5.add(s8);
        a3.add(s9);
        completeMap.put((long)0, a0);
        completeMap.put((long)1, a1);
        completeMap.put((long)2, a2);
        completeMap.put((long)3, a3);
        completeMap.put((long)4, a4);
        completeMap.put((long)5, a5);
		MapManagement.getInstance().getMap().setGraph(completeMap);
	    List<Long> listIdInter = new ArrayList<Long>();
	    listIdInter.add(i0.getId());
	    listIdInter.add(i4.getId());
	    Dijkstra dij = new Dijkstra();
	    //i0 -> i4 is a one way road
	    try{
	    	Map<Long, Map<Long, Float>> tspG = dij.doDijkstra(MapManagement.getInstance().getMap().getGraph(), listIdInter);
	    } catch (Exception e) {
	    	Assertions.assertThat(e).isNotNull();
	    }
	}
	
	/**
	 * Test method for {@link optimodlyon.agile.algorithmic.Dijkstra#doDijkstra(java.util.HashMap, java.util.ArrayList)}.
	 * @throws Exception 
	 */
	@Test
	public final void testIntersWithoutSeg() throws Exception {
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
        Segment s8 = new Segment(i5,i3,1);
        Segment s9 = new Segment(i3,i0,10);
        Segment s10 = new Segment(i4,i0,2);
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
        a5.add(s8);
        a3.add(s9);
        a4.add(s10);
        completeMap.put((long)0, a0);
        completeMap.put((long)1, a1);
        completeMap.put((long)2, a2);
        completeMap.put((long)3, a3);
        completeMap.put((long)5, a5);
		MapManagement.getInstance().getMap().setGraph(completeMap);
	    List<Long> listIdInter = new ArrayList<Long>();
	    listIdInter.add(i0.getId());
	    listIdInter.add(i5.getId());
	    listIdInter.add(i3.getId());
	    Dijkstra dij = new Dijkstra();
	    //i4 is the end of a segment but not included into the ntersection list
	    Map<Long, Map<Long, Float>> tspG = dij.doDijkstra(MapManagement.getInstance().getMap().getGraph(), listIdInter);
	    Assertions.assertThat(tspG).containsKeys((long)0,(long)5,(long)3);
	}

	/**
	 * Test method for {@link optimodlyon.agile.algorithmic.Dijkstra#doDijkstra(java.util.HashMap, java.util.ArrayList)}.
	 * @throws Exception 
	 */
	@Test
	public final void testEmptyMap() throws Exception {
	    List<Long> listIdInter = new ArrayList<Long>();
	    listIdInter.add((long)0);
	    listIdInter.add((long)2);
	    listIdInter.add((long)3);
	    Dijkstra dij = new Dijkstra();
		 Map<Long, Map<Long, Float>> tspG = dij.doDijkstra(MapManagement.getInstance().getMap().getGraph(), listIdInter);
	}
	
	/**
	 * Test method for {@link optimodlyon.agile.algorithmic.Dijkstra#List<Long> createPathIds(Long origin, Long destination)}.
	 * @throws Exception 
	 */
	@Test
	public final void testcreatePathIds() throws Exception {
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
        Segment s8 = new Segment(i5,i3,1);
        Segment s9 = new Segment(i3,i0,10);
        Segment s10 = new Segment(i4,i0,2);
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
        a5.add(s8);
        a3.add(s9);
        a4.add(s10);
        completeMap.put((long)0, a0);
        completeMap.put((long)1, a1);
        completeMap.put((long)2, a2);
        completeMap.put((long)3, a3);
        completeMap.put((long)4, a4);
        completeMap.put((long)5, a5);
		MapManagement.getInstance().getMap().setGraph(completeMap);
	    List<Long> listIdInter = new ArrayList<Long>();
	    listIdInter.add(i0.getId());
	    listIdInter.add(i5.getId());
	    listIdInter.add(i3.getId());
	    Dijkstra dij = new Dijkstra();
	    dij.doDijkstra(MapManagement.getInstance().getMap().getGraph(), listIdInter);
	    List<Long> path = dij.createPathIds((long)0, (long)5);
	    Assertions.assertThat(path).contains((long)0,(long)3,(long)5);
	}
}
