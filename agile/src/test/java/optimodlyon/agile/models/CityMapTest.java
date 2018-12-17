package optimodlyon.agile.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class CityMapTest {
	CityMap cmTest;
	Map<Long, List<Segment>> graph;
	List<Segment> listSegments;
	
	@Before
	public void setUp() {
		cmTest = new CityMap();
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		Intersection destination = new Intersection((long)2, (float)50, (float)50);
		Segment mySeg1 = new Segment(origin,destination,100);
		listSegments = new ArrayList<>();
		listSegments.add(mySeg1);
		long i = 1;
		graph = new HashMap<>();
		graph.put(i, listSegments);
	}

	@Test
	public void testCityMap() {
		CityMap cm = new CityMap();
		assertNotNull(cm);
	}

	@Test
	public void testSetGraph() {
		cmTest.setGraph(graph);
		assertEquals(cmTest.getGraph(), graph);
	}

	@Test
	public void testGetHeight() {
		cmTest.setHeight(2);
		assertEquals(cmTest.getHeight(), 2, 0);
	}

	@Test
	public void testGetWidth() {
		cmTest.setWidth(1);
		assertEquals(cmTest.getWidth(), 1, 0);
	}

	@Test
	public void testSetWidth() {
		cmTest.setWidth(1);
		assertEquals(cmTest.getWidth(), 1, 0);
	}

	@Test
	public void testSetHeight() {
		cmTest.setHeight(2);
		assertEquals(cmTest.getHeight(), 2, 0);
	}

	@Test
	public void testGetSegmentFromGraph() {
		long id1 = 1;
		long id2 = 2;

		cmTest.setGraph(graph);
		Segment s = cmTest.getSegmentFromGraph(id1, id2);
    	assertNotNull(s);
    	Assertions.assertThat(cmTest.getGraph().containsValue(s));
    	long id3 = 3;
    	long id4 = 4;
    	
    	Assertions.assertThat(cmTest.getSegmentFromGraph(id3, id4)).isNull();
	}

	@Test
	public void testGetGraph() {
		cmTest.setGraph(graph);
		assertEquals(cmTest.getGraph(), graph);
	}

}
