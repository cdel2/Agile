package optimodlyon.agile.algorithmic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Warehouse;

public class TSPMinDistanceTest {
	Map<Long, Map<Long, Float>> myGraph;
	List<Long> myVisited;
	List<Long> myListDeliveries;
	List<Long> myNotVisited;
	Map<Long, TreeMap<Long, Float>> myTreeGraph;
	List<Long> myBestSolution;

	
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
		
		myVisited = new ArrayList<Long>();
		
		myVisited.add((long)1);
		myVisited.add((long)2);
		
		myNotVisited = new ArrayList<Long>();
		myNotVisited.add((long)3);
		myNotVisited.add((long)4);

		
		myListDeliveries = new ArrayList<Long>();
		myListDeliveries.add((long)1);
		myListDeliveries.add((long)2);
		myListDeliveries.add((long)3);
		myListDeliveries.add((long)4);
		
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
		
		myBestSolution = new ArrayList<Long>();
		myBestSolution.add((long)1);
		myBestSolution.add((long)4);
		myBestSolution.add((long)3);
		myBestSolution.add((long)2);

		MapManagement.getInstance().setWarehouse(new Warehouse((long)1,null));

	}
	
	@Test
	public final void testBoundMinDistance() throws Exception
	{
		TSPMinDistance tspMinDistance = new TSPMinDistance();
		float value = tspMinDistance.bound((long)2, myNotVisited, myVisited, myListDeliveries, myTreeGraph);
		float expectedValue = 17;
		assertEquals(expectedValue, value,0);
	}
	
	@Test
	public final void testStartTSP() throws Exception {
		TSPMinDistance tspMinDistance = new TSPMinDistance();
		tspMinDistance.startTSP(1000, myListDeliveries.size(), myTreeGraph);
		assertEquals(myBestSolution, tspMinDistance.getBestSolution());
		
	}
	
	
}
