/**
 * 
 */
package optimodlyon.agile.algorithmic;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	 */
	@Test
	public final void testDoDijkstra() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link optimodlyon.agile.algorithmic.Dijkstra#findShortestPathsFromSource(java.util.HashMap, java.util.ArrayList, java.lang.Long)}.
	 */
	@Test
	public final void testFindShortestPathsFromSource() {
		fail("weshalors"); // TODO
	}

//	/**
//	 * Test method for {@link optimodlyon.agile.algorithmic.Dijkstra#findClosestNode(java.util.ArrayList)}.
//	 */
//	@Test
//	public final void testFindClosestNode() {
//		Dijkstra dijkstra = new Dijkstra();
//		ArrayList<Segment> segments = new ArrayList<Segment>();
//	    Intersection i0 = new Intersection((long)0,(float)0.0,(float)0.0);
//	    Intersection i1 = new Intersection((long)1,(float)0.0,(float)1.0);
//	    Intersection i2 = new Intersection((long)2,(float)1.0,(float)0.0);
//	    Intersection i3 = new Intersection((long)3,(float)1.0,(float)1.0);
//	    Segment s0 = new Segment(i0,i1,1);
//	    Segment s1 = new Segment(i0,i2,2);
//	    Segment s2 = new Segment(i0,i3,-1);
//	    segments.add(s0);
//	    segments.add(s1);
//	    segments.add(s2);
//	    long id = dijkstra.findClosestNode(segments);
//	    long result = 0;
//	    assertTrue("negative dist in segment", id==result);
//	}

	/**
	 * Test method for {@link optimodlyon.agile.algorithmic.Dijkstra#UpdateDistance(java.lang.Long, java.lang.Long, java.util.HashMap, java.util.Map)}.
	 */
	@Test
	public final void testUpdateDistance() {
		fail("Not yet implemented"); // TODO
	}

}
