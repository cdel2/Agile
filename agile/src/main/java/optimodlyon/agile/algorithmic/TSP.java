package optimodlyon.agile.algorithmic;

import optimodlyon.agile.models.*;
import optimodlyon.agile.util.Time;

import java.awt.Paint;
import java.awt.font.GraphicAttribute;
import java.io.Console;
import java.util.*;
import java.util.Map.Entry;

import javafx.util.*;

import org.apache.logging.log4j.Marker;
import org.springframework.context.support.StaticApplicationContext;


public class TSP {
	/**
	 * Main class (used for tests) To remove
	 */
	public static void main(String[] args) {
		TSP tsp = new TSP();
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
		graph.put((long) 5, successors5);

		// tsp.doTSP(graph, map);
		System.out.println(graph);
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
		System.out.println("newGraph : " + newGraph);
		
		

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
	public Round brutForceTSP(Map<Long, Map<Long, Float>> graph, Dijkstra dijkstra, Time startTime) {
		// Get all possible paths in the graph
		List<Round> possibleRounds = startTSP(graph, dijkstra, startTime);
		//System.out.println("Liste des chemins possibles : " + possiblePaths);
		// Find the shortest path
		Round shortestRound = findShortestRound(possibleRounds);
		//System.out.println("Chemin le plus court trouvé : " + shortestRound.getClass() + "de longueur : " + shortestRound.getTotalDuration());
		return shortestRound;
	}
	
	public Round startBrandBoundTSP(Map<Long, Map<Long, Float>> graph, Dijkstra dijkstra, Time startTime)
	{
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		Map<Long,Float> ri = generateRi(graph);
		Map<Long, Float> ci = generateCi(graph,ri);//result after locating the minimal element in the map
		Map<Long, Map<Long, Float>> reducedGraph = 	generateReducedGraph(graph, ri, ci);
		float b = computeB(ri, ci);
		Map<Long, Pair> pi = computeFirstPi(reducedGraph);
		
		Iterator it = pi.entrySet().iterator();
		long row = (long) (((Entry) it.next()).getKey());
		long col = (pi.get(row)).getIdPredecessor();
		Map<Long, Map<Long, Float>> newGraph = generateNewGraph(reducedGraph, row, col);
		Map<Long,Float> newRi = generateRi(newGraph);
		Map<Long, Float> newCi = generateCi(newGraph,ri);
		float sigma = computeB(newRi, newCi);
		
		return round;
	}
	
	public Map<Long, Map<Long, Float>> branchBoundTSP(Map<Long, Map<Long, Float>> graph, Dijkstra dijkstra)
	{
		Map<Long,Float> ri = generateRi(graph);
		Map<Long, Float> ci = generateCi(graph,ri);
		Map<Long, Map<Long, Float>> newGraph = generateReducedGraph(graph, ri, ci);
		System.out.println(newGraph);
		return newGraph;
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
		(suppGraph.get(col)).remove(row);
		suppGraph = removeColumn(col, suppGraph);
		Map<Long, Float> ri = generateRi(suppGraph);
		Map<Long, Float> ci = generateCi(suppGraph, ri);

		//Map<Long, Map<Long, Float>> newGraph= generateReducedGraph(suppGraph, ri, ci);
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
		Iterator it = ri.entrySet().iterator();
		long key;
		float b = 0;
		while(it.hasNext())
		{
			key = (long) (((Entry) it.next()).getKey());
			b = b + ri.get(key) + ci.get(key);
			
		}
		return b;
	}
	
	public Map<Long, Pair> computePi(Map<Long, Map<Long, Float>> graph)
	{
		List<Float> listPiij = new ArrayList<Float>();
		Iterator it1 = graph.entrySet().iterator();
		Iterator it2 = graph.entrySet().iterator();
		float pi = 0;
		float current;
		long key1;
		long key2;
		Map<Long, Pair> result = new HashMap<Long,Pair>();
		while(it1.hasNext())
		{
			key1 = (long) (((Entry) it1.next()).getKey());
			it2 = graph.entrySet().iterator();
			while(it2.hasNext())
			{
				key2 = (long) (((Entry) it2.next()).getKey());
				current = computePiij(graph, key1, key2);
				if(current > pi)
				{
					pi = current;
					Pair pair = new Pair(key2,pi);
					result.clear();;
					result.put(key1, pair);
				}
			}
		}
		return result;
	}
	
	public Map<Long, Pair> computeFirstPi(Map<Long, Map<Long, Float>> graph)
	{
		List<Float> listPiij = new ArrayList<Float>();
		Iterator it = graph.entrySet().iterator();
		float pi = 0;
		float current;
		long key1 = MapManagement.getInstance().getWarehouse().getId();
		long key2;
		Map<Long, Pair> result = new HashMap<Long,Pair>();
		it = graph.entrySet().iterator();
		while(it.hasNext())
		{
			key2 = (long) (((Entry) it.next()).getKey());
			current = computePiij(graph, key1, key2);
			if(current > pi)
			{
				pi = current;
				Pair pair = new Pair(key2,pi);
				result.clear();;
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
		float di;
		float dj;
		float current;
		long keyJ;
		successors = graph.get(i);
		Iterator itJ = successors.entrySet().iterator();
		keyJ = (long) (((Entry) itJ.next()).getKey());
		if(keyJ != j)
		{
			di = successors.get(keyJ);
		} else {
			keyJ = (long) (((Entry) itJ.next()).getKey());
			di = successors.get(keyJ);
		}
		
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
		long keyI;
		keyI = (long) (((Entry) itI.next()).getKey());
		if(i != keyI)
		{
			successors = graph.get(keyI);
			if(successors.containsKey(j))
			{
				dj = successors.get(j);
			} else {
				keyI = (long) (((Entry) itI.next()).getKey());
				successors = graph.get(keyI);
				dj = successors.get(j);
			}
		} else {
			keyI = (long) (((Entry) itI.next()).getKey());
			successors = graph.get(keyI);
			if(successors.containsKey(j))
			{
				dj = successors.get(j);
			} else {
				keyI = (long) (((Entry) itI.next()).getKey());
				successors = graph.get(keyI);
				dj = successors.get(j);
			}
			
		}
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
		
		piij = di + dj;
			
		return piij;
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
		while (it.hasNext()) {
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
