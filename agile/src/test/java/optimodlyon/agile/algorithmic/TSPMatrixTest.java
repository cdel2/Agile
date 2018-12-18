package optimodlyon.agile.algorithmic;

import static org.junit.Assert.assertEquals;

import java.util.*;
import java.util.Map.Entry;

import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TSPMatrixTest {
	Map<Long, Map<Long, Float>> myGraph;
	Map<Long, TreeMap<Long, Float>> myTreeGraph;
	Map<Long, Float> myRi;
	Map<Long, Float> myCi;
	List<Long> myVisited;
	List<Long> myListDeliveries;

	Map<Long, Map<Long, Float>> myGeneratedGraph;
	





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



	}


	
	@Test
	public final void testComputeRi() throws Exception {
		Map<Long, Float> map = myGraph.get((long)1);
		TSPMatrix tspMatrix = new TSPMatrix();
		float ri = tspMatrix.computeRi(map);
		float expectedResult = 2;
		assertEquals(ri,expectedResult,0);		
		
	}
	
	@Test
	public final void testComputeCi() throws Exception {
		Map<Long, Float> map = myGraph.get((long)1);
		TSPMatrix tspMatrix = new TSPMatrix();
		float ci = tspMatrix.computeCi(map, myRi);
		float expectedResult = 0;
		assertEquals(ci,expectedResult,0);		
	}
	
	@Test
	public final void testGenerateRi() throws Exception {
		TSPMatrix tspMatrix = new TSPMatrix();
		Map<Long, Float> ri = tspMatrix.generateRi(myGraph);
		Map<Long, Float> expectedRi = new HashMap<Long, Float>();
		expectedRi.put((long)1, (float)2);
		expectedRi.put((long)2, (float)5);
		expectedRi.put((long)3, (float)1);
		expectedRi.put((long)4, (float)6);
		assertEquals(expectedRi, ri);

	}
	
	@Test
	public final void testGenerateCi() throws Exception {
		TSPMatrix tspMatrix = new TSPMatrix();
		Map<Long, Float> ri = tspMatrix.generateRi(myGraph);
		Map<Long, Float> ci = tspMatrix.generateCi(myGraph,ri);
		Map<Long, Float> expectedCi = new HashMap<Long, Float>();
		expectedCi.put((long)1, (float)0);
		expectedCi.put((long)2, (float)1);
		expectedCi.put((long)3, (float)0);
		expectedCi.put((long)4, (float)0);
		assertEquals(expectedCi, ci);
	}
	
	@Test
	public final void testComputeB() throws Exception {
		TSPMatrix tspMatrix = new TSPMatrix();
		float b = tspMatrix.computeB(myRi, myCi);
		float expectedResult = 17;
		assertEquals(b,expectedResult,0);		
	}
	
	@Test
	public final void testRemoveColumn() throws Exception {
		TSPMatrix tspMatrix = new TSPMatrix();
		Map<Long, Map<Long, Float>> graph = new HashMap<Long, Map<Long, Float>>(myGraph);
		Map<Long, Map<Long, Float>> newGraph = tspMatrix.removeColumn((long)1, graph);
		Iterator<Entry<Long, Map<Long, Float>>> it = newGraph.entrySet().iterator();
		long key;
		while(it.hasNext())
		{
			key = (long) (it.next().getKey());
			assertTrue(!(newGraph.get(key).containsKey((long)1)));
		}
	}
	
	@Test
	public final void testGenerateGraph() throws Exception {
		TSPMatrix tspMatrix = new TSPMatrix();
		Map<Long, TreeMap<Long, Float>> treeGraph = new HashMap<Long, TreeMap<Long,Float>>(myTreeGraph);
		Map<Long, Map<Long, Float>> newGraph = tspMatrix.generateGraph(myVisited, treeGraph);
		assertEquals(newGraph,myGeneratedGraph);
		
	}
	
	@Test
	public final void testBoundMatrix() throws Exception {
		TSPMatrix tspMatrix = new TSPMatrix();
		float value = tspMatrix.bound((long)2, myVisited, myVisited, myListDeliveries, myTreeGraph);
		System.out.println(myTreeGraph);
		float expectedValue = 20;
		assertEquals(expectedValue,value,0);
	}
}
