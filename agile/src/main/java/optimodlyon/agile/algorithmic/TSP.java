package optimodlyon.agile.algorithmic;

import optimodlyon.agile.controller.Controller;
import optimodlyon.agile.models.*;
import optimodlyon.agile.util.Time;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

public class TSP {
    private volatile AtomicBoolean stop;
	/**
	 * Main class (used for tests) To remove
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/*TSP tsp = new TSP();
		Map<Long, Float> successors1 = new HashMap<Long, Float>();
		successors1.put((long) 2, (float) 600);
		successors1.put((long) 3, (float) 1000);
		successors1.put((long) 4, (float) 1900);
		successors1.put((long) 5, (float) 1100);
		

		Map<Long, Float> successors2 = new HashMap<Long, Float>();
		successors2.put((long) 1, (float) 600);
		successors2.put((long) 3, (float) 1900);
		successors2.put((long) 4, (float) 1900);
		successors2.put((long) 5, (float) 1500);


		Map<Long, Float> successors3 = new HashMap<Long, Float>();
		successors3.put((long) 1, (float) 1000);
		successors3.put((long) 2, (float) 1900);
		successors3.put((long) 4, (float) 1700);
		successors3.put((long) 5, (float) 1200);


		Map<Long, Float> successors4 = new HashMap<Long, Float>();
		successors4.put((long) 1, (float) 1900);
		successors4.put((long) 2, (float) 1900);
		successors4.put((long) 3, (float) 1700);
		successors4.put((long) 5, (float) 1900);
		
		Map<Long, Float> successors5 = new HashMap<Long, Float>();
		successors5.put((long) 1, (float) 1100);
		successors5.put((long) 2, (float) 1500);
		successors5.put((long) 3, (float) 1200);
		successors5.put((long) 4, (float) 1900);


		Map<Long, Map<Long, Float>> graph = new HashMap<Long, Map<Long, Float>>();
		graph.put((long) 1, successors1);
		graph.put((long) 2, successors2);
		graph.put((long) 3, successors3);
		graph.put((long) 4, successors4);
		graph.put((long) 5, successors5);*/

		// tsp.doTSP(graph, map);
		/*System.out.println(graph);
		Map<Long,Float> ri = tsp.generateRi(graph);
		System.out.println("ri : "+ri);
		Map<Long,Float> ci = tsp.generateCi(graph, ri);
		System.out.println("ci : " +ci);
		
		Map<Long, Map<Long, Float>> graphique = graph;
		Map<Long, Map<Long, Float>> map = tsp.generateReducedGraph(graphique,ri,ci);

		System.out.println(map);
		float b = tsp.computeB(ri, ci);
		System.out.println(b);
		float piij = tsp.computePiij(map,(long)2, (long)1);
		System.out.println(piij);
		Map<Long, Pair> pi = tsp.computePi(map);
		System.out.println(pi);
		System.out.println("map: "+map);
		Map<Long, Map<Long, Float>> newGraph = tsp.generateNewGraph(map, (long)2, (long)1);
		System.out.println("newGraph : " + newGraph);*/
		/*Dijkstra dij = new Dijkstra();
		Time start = new Time(3,3,3);
		tsp.startBranchBoundTSP(graph,dij, start);*/
		Controller controller = new Controller();
		MapManagement instance = MapManagement.getInstance();
		controller.initializeGraph("grand");
		controller.getDeliveries("dl-grand-20");
		Clustering clustering = new Clustering();
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		MapManagement.getInstance().getMap();
		int nb =2;
		MapManagement.getInstance().initializeListDeliverer(nb);
		List<List<Delivery>> clusters = clustering.dispatchCluster(MapManagement.getInstance().getMap(), nb); 
		int i =0;
		List<Round> finalRound = new ArrayList<Round>();
		for(List<Delivery> cluster : clusters) {
			i++;
			List<Long> arrayOfIntersectionIds = Clustering.createIdArray(cluster);
			MapManagement.getInstance().getWarehouse();
			arrayOfIntersectionIds.add(MapManagement.getInstance().getWarehouse().getId());
			//Map<Long, List<Segment>> mapGraph = clustering.reform(map.getGraph());
			Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(MapManagement.getInstance().getMap().getGraph(), arrayOfIntersectionIds);
			Time startTime=new Time("8:00:00");
			//Round round = tsp.brutForceTSP(graph, dijkstra, startTime);
			//Round round = tsp.startBranchBoundTSP(graph, dijkstra, startTime);
			System.out.println(graph);
			Round round = tsp.startTSPMatrix(100000000, graph.size(), graph, startTime, dijkstra);
			System.out.println("heeeey");
			finalRound.add(round);
		}

	}

	/**
	 * doTSP is the only function needed to do the TSP algorithm, it retrieves a
	 * pair of an ordonned array describing the path and its length from a graph
	 * that contains lengths between a Node and all possible destinations
	 * 
	 * @param map
	 * @param idWarehouse
	 * @return PathLength
	 */
	public Map<Long, List<Pair>> transformDataStructure(Map<Long, Map<Long, Float>> graph)
	{
		Iterator it = graph.entrySet().iterator();
		Map<Long, List<Pair>> newGraph = new HashMap<Long, List<Pair>>();
		long key;
		while(it.hasNext())
		{
			key = (long) (((Entry) it.next()).getKey());
			List<Pair> currentSuccessors = mapToList(graph.get(key));
			newGraph.put(key, currentSuccessors);

		}
		return newGraph;
		
	}
	
	public List<Pair> mapToList(Map<Long, Float> map)
	{
		List<Pair> list = new ArrayList<Pair>();
		Iterator it = map.entrySet().iterator();
		long key;
		while(it.hasNext())
		{
			key = (long) (((Entry) it.next()).getKey());
			Pair pair = new Pair(key, map.get(key));
			list.add(pair);
		}
		
		return list;


	}
	
	public Map<Long, TreeMap<Long, Float>> mapToTreeMap(Map<Long, Map<Long, Float>> map)
	{
		TreeMap<Long,TreeMap<Long, Float>> newGraph = new TreeMap<Long,TreeMap<Long, Float>>();
		Iterator it = map.entrySet().iterator();
		long key;
		while(it.hasNext())
		{
			key = (long) (((Entry) it.next()).getKey());
			Map<Long, Float> currentMap = map.get(key);
			TreeMap<Long, Float> treeMap = new TreeMap<Long, Float>(currentMap);
			newGraph.put(key, treeMap);
		}
		
		return newGraph;


	}
	
	public Round startTSPClosestDelivery(int timeLimit, int nbIntersections, Map<Long, Map<Long, Float>> graph, Time startTime, Dijkstra dijkstra)
	{
		TSPClosestDelivery tsp = new TSPClosestDelivery();
		Map<Long, TreeMap<Long, Float>> newGraph = mapToTreeMap(graph);
		tsp.startTSP(timeLimit, nbIntersections, newGraph);
		List<Long>listPath =tsp.getBestSolution();
		listPath.add(MapManagement.getInstance().getWarehouse().getId());
		System.out.println("list  :" + listPath);
		Time currentTime = new Time(startTime);
		Path pathFound;
		List<Long> intersectionIds;
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		for (int i = 1; i < listPath.size() - 1; i++) {
			intersectionIds = dijkstra.createPathIds(listPath.get(i), listPath.get(i + 1));
			Long arrivalId = listPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes();
			round.addPath(pathFound);
		}
		return round;
		
		
	}
	
	public Round startTSPMatrix(int timeLimit, int nbIntersections, Map<Long, Map<Long, Float>> graph, Time startTime, Dijkstra dijkstra)
	{
		System.out.println("warehiuse : " + MapManagement.getInstance().getWarehouse().getId());
		TSPMatrix tsp = new TSPMatrix();
		Map<Long, TreeMap<Long, Float>> newGraph = mapToTreeMap(graph);
		tsp.startTSP(timeLimit, nbIntersections, newGraph);
		List<Long>listPath =tsp.getBestSolution();
		listPath.add(MapManagement.getInstance().getWarehouse().getId());
		System.out.println("list  :" + listPath);
		Time currentTime = new Time(startTime);
		Path pathFound;
		List<Long> intersectionIds;
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		for (int i = 1; i < listPath.size() - 1; i++) {
			intersectionIds = dijkstra.createPathIds(listPath.get(i), listPath.get(i + 1));
			Long arrivalId = listPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes();
			round.addPath(pathFound);
		}
		return round;
		
		
	}
	
	public Round brutForceTSP(Map<Long, Map<Long, Float>> graph, Dijkstra dijkstra, Time startTime) {
                stop = new AtomicBoolean(false);
		// Get all possible paths in the graph
		List<Round> possibleRounds = startTSP(graph, dijkstra, startTime);
		//System.out.println("Liste des chemins possibles : " + possiblePaths);
		// Find the shortest path
		Round shortestRound = findShortestRound(possibleRounds);
		//System.out.println("Chemin le plus court trouvé : " + shortestRound.getClass() + "de longueur : " + shortestRound.getTotalDuration());
		return shortestRound;
	}
	
	
	public Round startBranchBoundTSP(Map<Long, Map<Long, Float>> graph, Dijkstra dijkstra, Time startTime)
	{
		System.out.println("graph debut : " + graph);
		System.out.println("warehouse : " + MapManagement.getInstance().getWarehouse().getId());
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		List<Long> currentRound = new ArrayList<Long>();
		Map<Long,Float> ri = generateRi(graph);
		Map<Long, Float> ci = generateCi(graph,ri);//result after locating the minimal element in the map
		Map<Long, Map<Long, Float>> reducedGraph = 	generateReducedGraph(graph, ri, ci);
		System.out.println("not the end, ri : " + ri);
		System.out.println("not the end, ci : " + ci);
		System.out.println("not the end, reducedGraph : " + reducedGraph);
		float b = computeB(ri, ci);
		System.out.println("not the end, b : " + b);
		Map<Long, Pair> pi = computeFirstPi(reducedGraph, MapManagement.getInstance().getWarehouse().getId()); //To change to firstPi
		
		Iterator it = pi.entrySet().iterator();
		long row = (long) (((Entry) it.next()).getKey());
		long col = (pi.get(row)).getIdPredecessor();
		System.out.println("not the end, pi : " + pi);

		Map<Long, Map<Long, Float>> newGraph = generateNewGraph(reducedGraph, row, col);
		Map<Long,Float> newRi = generateRi(newGraph);
		Map<Long, Float> newCi = generateCi(newGraph,newRi);
		float sigma = computeB(newRi, newCi);
		System.out.println("not the end, newRi : " + newRi);
		System.out.println("not the end, newCi : " + newCi);
		System.out.println("not the end, sigma : " + sigma);

		float labelSeg = b + sigma;
		float labelNonSeg = b + (pi.get(row)).getDistFromSource();
		if(labelSeg <= labelNonSeg)
		{
			if(newGraph.containsKey(col))
			{
				if(newGraph.get(col).size()> 1)
				{
					(newGraph.get(col)).remove(row);

				}

			}
			System.out.println("choose seg");
			currentRound.add(row);
			currentRound.add(col);
			System.out.println("graph avant : "+ graph);
			currentRound = branchBoundTSP(newGraph, newRi, newCi, labelSeg, currentRound, dijkstra, startTime, col);
		} else {
			System.out.println("choose NOT seg");
			if(reducedGraph.containsKey(row))
			{
				if(reducedGraph.get(row).size()> 1)
				{
					(reducedGraph.get(row)).remove(col);

				}

			}
			newRi = generateRi(reducedGraph);
			newCi = generateCi(reducedGraph, newRi);
			System.out.println("graph avant : "+ graph);
			currentRound = branchBoundTSP(reducedGraph, newRi, newCi, labelNonSeg, currentRound, dijkstra, startTime, MapManagement.getInstance().getWarehouse().getId());
		}
		System.out.println("final : "+currentRound);
		System.out.println(col);
		if(currentRound.size() != 2*graph.size())
		{
			long beforeLastRow = currentRound.get(currentRound.size()-4);
			long beforeLastCol = currentRound.get(currentRound.size()-3);
			if(reducedGraph.containsKey(beforeLastRow))
			{
				if(graph.get(beforeLastRow).size()> 1)
				{
					(graph.get(beforeLastRow)).remove(beforeLastCol);

				}

			}
			
		}
		Time currentTime = new Time(startTime);
		Path pathFound;
		List<Long> intersectionIds;
		List<Long> listPath = createRoundId(currentRound);
		System.out.println("list ordered :" + listPath);
		for (int i = 1; i < listPath.size() - 1; i++) {
			intersectionIds = dijkstra.createPathIds(listPath.get(i), listPath.get(i + 1));
			Long arrivalId = listPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes();
			round.addPath(pathFound);
		}
		System.out.println(round);
		return round;
	}
	
	public List<Long> createRoundId(List<Long>currentRound)
	{
		List<Long> round = new ArrayList<Long>();
		Map<Long,Long> roundMap = new HashMap<Long,Long>();
		for(int i = 0; i<currentRound.size()-1; i=i+2)
		{
			roundMap.put(currentRound.get(i), currentRound.get(i+1));
		}
		System.out.println("round map : " + roundMap);
		long key = MapManagement.getInstance().getWarehouse().getId();
		round.add(key);
		long value;
		System.out.println(key);

		while(!(roundMap.isEmpty()))
		{
			
			value = roundMap.get(key);
			round.add(value);
			roundMap.remove(key);
			key = value;
			System.out.println(key);

		}
		return round;
	}
	
	public List<Long> branchBoundTSP(Map<Long, Map<Long, Float>> graph, Map<Long, Float> ri, Map<Long, Float> ci, float label, List<Long> round, Dijkstra dijkstra, Time startTime, long start)
	{
		if(graph.isEmpty())
		{
			System.out.println("the end");
			return round;
		} else if((start == MapManagement.getInstance().getWarehouse().getId()) && (graph.size()>2)) {
			System.out.println("the end bis");
			return round;
			
		} else {
			System.out.println("not the end, graph : " + graph);
			System.out.println("not the end, round : " + round);
			float b = label;
			Map<Long, Map<Long, Float>> reducedGraph = generateReducedGraph(graph, ri, ci);
			System.out.println("not the end, ri: " + ri);
			System.out.println("not the end, ci : " + ci);
			System.out.println("not the end, reducedGraph : " + reducedGraph);
			Map<Long, Pair> pi = computeFirstPi(reducedGraph, start); //To change to firstPi
			Iterator it = pi.entrySet().iterator();
			long row = (long) (((Entry) it.next()).getKey());
			long col = (pi.get(row)).getIdPredecessor();
			System.out.println("not the end, pi : " + pi);
			Map<Long, Map<Long, Float>> newGraph = generateNewGraph(reducedGraph, row, col);
			Map<Long,Float> newRi = generateRi(newGraph);
			Map<Long, Float> newCi = generateCi(newGraph,newRi);
			System.out.println("not the end, newGraph : " + newGraph);
			float sigma = computeB(newRi, newCi);
			float labelSeg = b + sigma;
			float labelNonSeg = b + (pi.get(row)).getDistFromSource();
			if(labelSeg <= labelNonSeg)
			{
				System.out.println("choose seg");
				if(newGraph.containsKey(col))
				{
					if(newGraph.get(col).size()> 1)
					{
						(newGraph.get(col)).remove(row);

					}

				}
				round.add(row);
				round.add(col);
				System.out.println("graph avant : "+ graph);
				round = branchBoundTSP(newGraph, newRi, newCi, labelSeg, round, dijkstra, startTime, col);
			} else {
				System.out.println("choose NOT seg");
				if(reducedGraph.containsKey(row))
				{
					if(reducedGraph.get(row).size()> 1)
					{
						(reducedGraph.get(row)).remove(col);

					}

				}
				newRi = generateRi(reducedGraph);
				newCi = generateCi(reducedGraph, newRi);
				System.out.println("graph avant : "+ graph);
				round = branchBoundTSP(reducedGraph, newRi, newCi, labelNonSeg, round, dijkstra, startTime, start);
			}
			return round;
		}
		
	}
	
	public Map<Long,Float> generateRi(Map<Long, Map<Long, Float>> graph)
	{
		Map<Long,Float> ri = new HashMap<Long,Float>();
		Iterator it = graph.entrySet().iterator();
		float r;
		long key;
		while (it.hasNext()) {
			key = (long) (((Entry) it.next()).getKey());
			Map<Long, Float> currentSuccessors = new HashMap<Long, Float>(graph.get(key));
			r = computeRi(currentSuccessors);
			ri.put(key, r);
		}
		return ri;
	}
	
	public Map<Long,Float> generateCi(Map<Long, Map<Long, Float>> graph,Map<Long,Float> ri )
	{
		Map<Long,Float> ci = new HashMap<Long,Float>();
		Iterator it = graph.entrySet().iterator();
		float c;
		long key;
		Iterator itCol;
		long keyCol;
		Map<Long, Float> successorsRow = new HashMap<Long, Float>();
		Map<Long, Float> successorsCol = new HashMap<Long, Float>();
		Iterator itRow;
		long keyRow;
		float val;
		while (it.hasNext()) {
			key = (long) (((Entry) it.next()).getKey());
			Map<Long, Float> currentSuccessors = graph.get(key);
			itCol = currentSuccessors.entrySet().iterator();
			while(itCol.hasNext())
			{
				keyCol = (long) (((Entry) itCol.next()).getKey());
				successorsCol.clear();
				if(!ci.containsKey(keyCol))
				{
					itRow = graph.entrySet().iterator();
					while(itRow.hasNext())
					{
						keyRow = (long) (((Entry) itRow.next()).getKey());
						successorsRow = graph.get(keyRow);
						if(successorsRow.containsKey(keyCol))
						{
							successorsCol.put(keyRow,successorsRow.get(keyCol));
						}
						
						
					}
					c = computeCi(successorsCol, ri);
					ci.put(keyCol, c);
				}
			}
			
			
			
		}
		return ci;

	}
	
	
	public float computeRi(Map<Long, Float> successors)
	{
		Iterator it = successors.entrySet().iterator();
		long key = (long) (((Entry) it.next()).getKey());
		float min = successors.get(key);
		float current;
		while(it.hasNext())
		{
			key = (long) (((Entry) it.next()).getKey());			
			current = successors.get(key);
			if(current < min)
			{
				min = current;
			}
		}
		return min;
	}
	
	public float computeCi(Map<Long, Float> successors, Map<Long,Float> ri)
	{
		Iterator it = successors.entrySet().iterator();
		long key = (long) (((Entry) it.next()).getKey());
		float min = successors.get(key) - ri.get(key);
		float current;
		while(it.hasNext())
		{
			key = (long) (((Entry) it.next()).getKey());			
			current = successors.get(key) - ri.get(key);
			if(current < min)
			{
				min = current;
			}
		}
		return min;
	}
	
	public Map<Long, Map<Long, Float>> generateReducedGraph(Map<Long, Map<Long, Float>> graph,Map<Long,Float> ri, Map<Long,Float> ci)
	{
		Map<Long, Map<Long, Float>> newGraph = new HashMap<Long, Map<Long, Float>>();
		Iterator it = graph.entrySet().iterator();
		long key;
		while (it.hasNext()) {
			key = (long) (((Entry) it.next()).getKey());
			Map<Long, Float> currentSuccessors = new HashMap<Long, Float>(graph.get(key));
			Map<Long, Float> newSuccessors = computeDij(key, currentSuccessors, ri, ci );
			newGraph.put(key, newSuccessors);
		}
		
		return newGraph;
	}
	
	public Map<Long, Map<Long, Float>> generateNewGraph(Map<Long, Map<Long, Float>> graph, long row, long col)
	{
		Map<Long, Map<Long, Float>> suppGraph = new HashMap<Long, Map<Long, Float>>(graph);
		suppGraph.remove(row);
		suppGraph = removeColumn(col, suppGraph);
		return suppGraph;
	}
	
	public Map<Long, Map<Long, Float>> removeColumn(long id, Map<Long, Map<Long, Float>> graph)
	{
		Iterator it = graph.entrySet().iterator();
		Map<Long, Map<Long, Float>> newGraph = new HashMap<Long, Map<Long, Float>>();
		long key;
		while (it.hasNext()) {
			key = (long) (((Entry) it.next()).getKey());
			Map<Long, Float> currentSuccessors = new HashMap<Long, Float>(graph.get(key));
			currentSuccessors.remove(id);
			Map<Long, Float> newSuccessors = currentSuccessors;
			newGraph.put(key, newSuccessors);
		}
		return newGraph;
	}
	
	public Map<Long, Float> computeDij(long keySource, Map<Long, Float> successors, Map<Long,Float> ri, Map<Long,Float> ci)
	{
		
		Iterator it = successors.entrySet().iterator();
		long key ;
		float oldD;
		float newD;
		Map<Long, Float> newMap = new HashMap<Long,Float>();
		while(it.hasNext())
		{
			key = (long) (((Entry) it.next()).getKey());			
			oldD = successors.get(key);
			newD = oldD - ri.get(keySource) - ci.get(key);
			newMap.put(key, newD);
			
		}
		return newMap;
	}
	
	//Compute Lower Bound
	public float computeB(Map<Long,Float> ri, Map<Long,Float> ci)
	{
		Iterator itR = ri.entrySet().iterator();
		long key;
		float b = 0;
		while(itR.hasNext())
		{
			key = (long) (((Entry) itR.next()).getKey());
			b = b + ri.get(key);
			
		}
		Iterator itC = ci.entrySet().iterator();
		while(itC.hasNext())
		{
			key = (long) (((Entry) itC.next()).getKey());
			b = b + ci.get(key);
			
		}
		return b;
	}
	
	public Map<Long, Pair> computePi(Map<Long, Map<Long, Float>> graph)
	{
		List<Float> listPiij = new ArrayList<Float>();
		Iterator it1 = graph.entrySet().iterator();
		Iterator it2;
		float pi = 0;
		float current;
		long key1;
		long key2;
		Map<Long, Pair> result = new HashMap<Long,Pair>();
		while(it1.hasNext())
		{
			key1 = (long) (((Entry) it1.next()).getKey());
			it2 = (graph.get(key1)).entrySet().iterator();
			while(it2.hasNext())
			{
				key2 = (long) (((Entry) it2.next()).getKey());
				current = computePiij(graph, key1, key2);
				System.out.println("key1 : " + key1 + " key2 : "+ key2+ " : current : " + current);
				if(current > pi)
				{
					pi = current;
					Pair pair = new Pair(key2,pi);
					result.clear();
					result.put(key1, pair);
				}
			}
		}
		return result;
	}
	
	public Map<Long, Pair> computeFirstPi(Map<Long, Map<Long, Float>> graph, long start)
	{
		List<Float> listPiij = new ArrayList<Float>();
		Iterator it;
		float pi = 0;
		float current;
		long key1 = start;//MapManagement.getInstance().getWarehouse().getId();

		long key2;

		Map<Long, Pair> result = new HashMap<Long,Pair>();

		it = (graph.get(key1)).entrySet().iterator();
		while(it.hasNext())
		{
			key2 = (long) (((Entry) it.next()).getKey());

			current = computePiij(graph, key1, key2);

			if(current >= pi )
			{
				pi = current;
				Pair pair = new Pair(key2,pi);
				result.clear();
				result.put(key1, pair);
			}
		}
		
		return result;
	}
	
	public float computePiij(Map<Long, Map<Long, Float>> graph, long i, long j)
	{
		Iterator itI = graph.entrySet().iterator();
		float piij = 0;
		Map<Long, Float> successors = new HashMap<Long, Float>();
		float di = 100000;
		float dj = 100000;
		float current;
		long keyJ;
		successors = graph.get(i);
		Iterator itJ = successors.entrySet().iterator();
		while(itJ.hasNext())
		{
			keyJ = (long) (((Entry) itJ.next()).getKey());
			if((keyJ != j))
			{
				current = successors.get(keyJ);
				if(current < di)
				{
					di = current;
				}
				
			}

			
		}
		System.out.println("min I pi :" + di);
		long keyI;
		while(itI.hasNext())
		{
			keyI = (long) (((Entry) itI.next()).getKey());
			if(i != keyI)
			{
				successors = graph.get(keyI);
				if(successors.containsKey(j))
				{
					current = successors.get(j);
					if(current < dj)
					{
						dj = current;
					}
				}
			}
			
		}
		System.out.println("min J pi :" + dj);

		
		piij = di + dj;
			
		return piij;
	}

        public void stopAlgorithm(){
            System.out.println("stop algo");
            stop.set(true);
        }

	/**
	 * startTSP gets all possible paths to travell through all points and their
	 * length from an ordered graph (map)
	 * 
	 * @param
	 * @param idWarehouse
	 * @return List<PathLength>
	 */
	List<Round> startTSP(Map<Long, Map<Long, Float>> unorderedMap, Dijkstra dijkstra, Time startTime) {
                
		Long idWarehouse = MapManagement.getInstance().getWarehouse().getId();
		// This list will contain all the resulting pair of (path, length) possible.
		List<Round> possibleRounds = new ArrayList<Round>();
		// This contains all the remainings successors (we remove every visited node)
		Map<Long, Float> successors = new HashMap<Long, Float>(unorderedMap.get(idWarehouse));
		unorderedMap.remove(idWarehouse);
		List<Long> currentPath = new ArrayList();
		Float currentLength;
		// We iterate on all possible first node
		Iterator it = successors.entrySet().iterator();
		while (it.hasNext() && !stop.get()) {
			currentPath.clear();
			currentPath.add(idWarehouse);
			currentLength = (float) 0;
			Map.Entry currentPair = (Map.Entry) it.next();
			it.remove(); // avoids a ConcurrentModificationException
			Map<Long, Float> currentSuccessors = new HashMap<Long, Float>(unorderedMap.get(currentPair.getKey()));
			Map<Long, Map<Long, Float>> newUnordoredMap = copyMap(unorderedMap);
			// We add the current pair to current path
			newUnordoredMap.remove(currentPair.getKey());
			currentLength += (float) currentPair.getValue();
			currentPath.add((long) currentPair.getKey());
			// We add all possible paths to finalResults
			possibleRounds = nextNode(newUnordoredMap, currentSuccessors, currentPath, currentLength, possibleRounds,
					dijkstra, startTime);
		}
		if (stop.get()) {
			Round r = new Round();
			possibleRounds.add(r);
		}
		
		return (possibleRounds);
	}

	/**
	 * Recursively add a node to a path from an ordered graph (map) until this map
	 * is empty. Everytime it's empty, adds the final path to finalPaths and returns
	 * it.
	 * 
	 * @param unordered
	 * @param currentSuccessors
	 * @param currentPath
	 * @param currentLength
	 * @param possibleRounds
	 * @return List<PathLength>
	 */
	@SuppressWarnings("rawtypes")
	List<Round> nextNode(Map<Long, Map<Long, Float>> unorderedMap, Map<Long, Float> currentSuccessors,
			List<Long> currentPath, Float currentLength, List<Round> possibleRounds, Dijkstra dijkstra, Time startTime) {
		// We check if there are still nodes to visit
		if (unorderedMap.isEmpty()) {
			// if not, we add the current path (which is a possible final path) to
			// finalResults.
			//System.out.println(currentPath + ">>>>>>>>>>>>>>>" + map);
			Time currentTime = new Time(startTime);
			currentPath.add(MapManagement.getInstance().getWarehouse().getId());
			currentLength+=(currentSuccessors.get(MapManagement.getInstance().getWarehouse().getId()));
			List<Long> IntersectionIds = dijkstra.createPathIds(currentPath.get(0), currentPath.get(1));
			Long firstArrivalId = currentPath.get(1);
			Delivery firstArrival = MapManagement.getInstance().getDeliveryById(firstArrivalId);
			Path pathFound = new Path(IntersectionIds, firstArrival, currentTime);
			Round currentRound = new Round(MapManagement.getInstance().getWarehouse(), startTime);
			pathFound.setSegmentsPassageTimes();
			currentRound.addPath(pathFound);
			for (int i = 1; i < currentPath.size() - 1; i++) {
				IntersectionIds = dijkstra.createPathIds(currentPath.get(i), currentPath.get(i + 1));
				Long arrivalId = currentPath.get(i+1);
				Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
				pathFound = new Path(IntersectionIds, arrival, currentTime);
				pathFound.setSegmentsPassageTimes();
				currentRound.addPath(pathFound);
			}
			possibleRounds.add(currentRound);
			return possibleRounds;
		} else {
			// If there are still nodes to visit, add every possible remaining successor to
			// the path
			Iterator it = currentSuccessors.entrySet().iterator();
			// we iterate other all remaining nodes and make a new current path for all of
			// them
			while (it.hasNext()) {
				Map.Entry newPair = (Map.Entry) it.next();
				it.remove();
				if (unorderedMap.containsKey(newPair.getKey())) {
					ArrayList<Long> p = new ArrayList<Long>(currentPath);
					List<Long> newPath = (List) p.clone();
					Float newLength = currentLength;
					newLength += (float) newPair.getValue();
					newPath.add((long) newPair.getKey());
					Map<Long, Float> newSuccessors = new HashMap<Long, Float>(unorderedMap.get(newPair.getKey()));
					Map<Long, Map<Long, Float>> newUnordoredMap = copyMap(unorderedMap);
					newUnordoredMap.remove(newPair.getKey());
					possibleRounds = nextNode(newUnordoredMap, newSuccessors, newPath, newLength, possibleRounds,
							dijkstra, startTime);
				}
			}
			return possibleRounds;
		}
	}

	/**
	 * Copy a Map<Long, Map<Long, Float>>
	 * 
	 * @param map>>>>>>>>>>>
	 * @return Map<Long, Map<Long, Float>>
	 */
	Map<Long, Map<Long, Float>> copyMap(Map<Long, Map<Long, Float>> map) {
		Map<Long, Map<Long, Float>> newMap = new HashMap<Long, Map<Long, Float>>();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Map<Long, Float> insideMap = new HashMap<Long, Float>(map.get(pair.getKey()));
			newMap.put((long) pair.getKey(), insideMap);
		}
		return newMap;
	}

	/**
	 * Returns the shortest path from a list of paths.
	 * 
	 * @param possiblePaths
	 * @return PathLength
	 */
	Round findShortestRound(List<Round> possibleRounds) {
		if (possibleRounds.size() == 0)
			return null;
		else {
			Round bestRound = possibleRounds.get(0);
			for (Round round : possibleRounds) {
				if (round.getTotalDuration() < bestRound.getTotalDuration())
					bestRound = round;
			}
			return bestRound;
		}
	}

	
}
