package optimodlyon.agile.algorithmic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Path;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.models.Warehouse;
import optimodlyon.agile.util.Time;

public class TSPTest {
	Map<Long, Map<Long, Float>> myGraph;
	Map<Long, TreeMap<Long, Float>> myTreeGraph;
	Map<Long, Float> myRi;
	Map<Long, Float> myCi;
	List<Long> myVisited;
	List<Long> myListDeliveries;

	Map<Long, Map<Long, Float>> myGeneratedGraph;
	List<Long> myBestSolution;
	Time myTime;
	
	Dijkstra myDijkstra;

	Map<Long, Map<Long, Float>> graphDijkstra;
	List<Long> myDijListDeliveries;
	
	Round myRound;


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myGraph = new HashMap<Long, Map<Long, Float>>();
		
		Map<Long, Float> mySucc1 = new HashMap<Long, Float>();
		mySucc1.put((long)2, (float) 12);
		mySucc1.put((long)3, (float) 7);
		mySucc1.put((long)4, (float) 2);
		
		Map<Long, Float> mySucc2 = new HashMap<Long, Float>();
		mySucc2.put((long)1, (float) 10);
		mySucc2.put((long)3, (float) 5);
		mySucc2.put((long)4, (float) 12);
		
		Map<Long, Float> mySucc3 = new HashMap<Long, Float>();
		mySucc3.put((long)1, (float) 1);
		mySucc3.put((long)2, (float) 2);
		mySucc3.put((long)4, (float) 9);
		
		Map<Long, Float> mySucc4 = new HashMap<Long, Float>();
		mySucc4.put((long)1, (float) 7);
		mySucc4.put((long)2, (float) 13);
		mySucc4.put((long)3, (float) 6);
		
		myGraph.put((long)1, mySucc1);
		myGraph.put((long)2, mySucc2);
		myGraph.put((long)3, mySucc3);
		myGraph.put((long)4, mySucc4);
		
		myTreeGraph = new HashMap<Long, TreeMap<Long, Float>>();
		
		TreeMap<Long, Float> myTreeSucc1 = new TreeMap<Long, Float>();
		myTreeSucc1.put((long)2, (float) 12);
		myTreeSucc1.put((long)3, (float) 7);
		myTreeSucc1.put((long)4, (float) 2);
		
		TreeMap<Long, Float> myTreeSucc2 = new TreeMap<Long, Float>();
		myTreeSucc2.put((long)1, (float) 10);
		myTreeSucc2.put((long)3, (float) 5);
		myTreeSucc2.put((long)4, (float) 12);
		
		TreeMap<Long, Float> myTreeSucc3 = new TreeMap<Long, Float>();
		myTreeSucc3.put((long)1, (float) 1);
		myTreeSucc3.put((long)2, (float) 2);
		myTreeSucc3.put((long)4, (float) 9);
		
		TreeMap<Long, Float> myTreeSucc4 = new TreeMap<Long, Float>();
		myTreeSucc4.put((long)1, (float) 7);
		myTreeSucc4.put((long)2, (float) 13);
		myTreeSucc4.put((long)3, (float) 6);
		
		myTreeGraph.put((long)1, myTreeSucc1);
		myTreeGraph.put((long)2, myTreeSucc2);
		myTreeGraph.put((long)3, myTreeSucc3);
		myTreeGraph.put((long)4, myTreeSucc4);
		
		myRi = new HashMap<Long, Float>();
		myRi.put((long)2,(float)0);
		myRi.put((long)3,(float)7);
		myRi.put((long)4,(float)1);
		
		myCi = new HashMap<Long, Float>();
		myCi.put((long)1,(float)1);
		myCi.put((long)3,(float)5);
		myCi.put((long)4,(float)3);
		
		myVisited = new ArrayList<Long>();
		
		myVisited.add((long)1);
		myVisited.add((long)2);

		Map<Long, Float> myNewSucc2 = new HashMap<Long, Float>(mySucc2);
		Map<Long, Float> myNewSucc3 = new HashMap<Long, Float>(mySucc3);
		Map<Long, Float> myNewSucc4 = new HashMap<Long, Float>(mySucc4);
		
		myNewSucc2.remove((long)1);
		myNewSucc3.remove((long)1);
		myNewSucc4.remove((long)1);

		myGeneratedGraph = new HashMap<Long, Map<Long, Float>>();
		
		myGeneratedGraph.put((long)2, myNewSucc2);
		myGeneratedGraph.put((long)3, myNewSucc3);
		myGeneratedGraph.put((long)4, myNewSucc4);
		
		myListDeliveries = new ArrayList<Long>();
		myListDeliveries.add((long)1);
		myListDeliveries.add((long)2);
		myListDeliveries.add((long)3);
		myListDeliveries.add((long)4);

		myBestSolution = new ArrayList<Long>();
		myBestSolution.add((long)1);
		myBestSolution.add((long)4);
		myBestSolution.add((long)3);
		myBestSolution.add((long)2);
		
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
		
		myTime = new Time(8,0,0);
		myDijkstra = new Dijkstra();
		myDijListDeliveries = new ArrayList<Long>();
	    myDijListDeliveries.add(i0.getId());
	    myDijListDeliveries.add(i5.getId());
	    myDijListDeliveries.add(i3.getId());
		graphDijkstra = myDijkstra.doDijkstra(completeMap, myDijListDeliveries);
		
		MapManagement.getInstance().getMap().setGraph(completeMap);

		MapManagement.getInstance().setWarehouse(new Warehouse((long)0,myTime));
	
		myRound = new Round(MapManagement.getInstance().getWarehouse(), myTime);
		
		List<Long> myListPath = new ArrayList<Long>();
		myListPath.add((long)0);
		myListPath.add((long)5);
		myListPath.add((long)3);
		myListPath.add((long)0);
		List<Long> intersectionIds;
		Path pathFound;
		Time currentTime = new Time(myTime);

		
		for (int i = 0; i < myListPath.size()-1; i++) {
			intersectionIds = myDijkstra.createPathIds(myListPath.get(i), myListPath.get(i + 1)); //get the full ordered list of ids
			Long arrivalId = myListPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes(); //we compute the time at which the deliverer arrives for each delivery point
			myRound.addPath(pathFound);
		}
	}
	
	@Test
	public final void testMapToTreeMap() throws Exception
	{
		TSP tsp = new TSP();
		Map<Long, TreeMap<Long, Float>> treeMap = tsp.mapToTreeMap(myGraph);
		assertEquals(myTreeGraph, treeMap);
	}
	
	@Test
	public final void testStartTSPClosestDelivery() throws Exception
	{
		TSP tsp = new TSP();
		Round round = tsp.startTSPClosestDelivery(10000, myDijListDeliveries.size(), graphDijkstra, myTime, myDijkstra);
		assertEquals(myRound.toString(), round.toString());
		
	}
	
	@Test
	public final void testStartTSPMinDistance() throws Exception
	{
		TSP tsp = new TSP();
		Round round = tsp.startTSPMinDistance(10000, myDijListDeliveries.size(), graphDijkstra, myTime, myDijkstra);
		assertEquals(myRound.toString(), round.toString());
		
	}
	
	@Test
	public final void testStartTSPMatrix() throws Exception
	{
		TSP tsp = new TSP();
		Round round = tsp.startTSPMatrix(10000, myDijListDeliveries.size(), graphDijkstra, myTime, myDijkstra);
		assertEquals(myRound.toString(), round.toString());
		
	}
}
