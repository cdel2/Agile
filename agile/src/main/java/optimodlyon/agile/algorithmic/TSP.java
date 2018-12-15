package optimodlyon.agile.algorithmic;

import optimodlyon.agile.controller.Controller;
import optimodlyon.agile.exceptions.FunctionalException;
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
		
		Controller controller = new Controller();
		controller.initializeGraph("grand");
		controller.getDeliveries("dl-grand-20");
		Clustering clustering = new Clustering();
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		MapManagement.getInstance().getMap();
		int nb =2;
		MapManagement.getInstance().initializeListDeliverer(nb);
		List<List<Delivery>> clusters = clustering.dispatchCluster(MapManagement.getInstance().getMap(), nb); 
		
		List<Round> finalRound = new ArrayList<Round>();
		for(List<Delivery> cluster : clusters) {			
			List<Long> arrayOfIntersectionIds = Clustering.createIdArray(cluster);
			MapManagement.getInstance().getWarehouse();
			arrayOfIntersectionIds.add(MapManagement.getInstance().getWarehouse().getId());
			Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(MapManagement.getInstance().getMap().getGraph(), arrayOfIntersectionIds);
			Time startTime=new Time("8:00:00");
			Round round = tsp.startTSPMatrix(100000000, graph.size(), graph, startTime, dijkstra);
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

	
	
	
	public Map<Long, TreeMap<Long, Float>> mapToTreeMap(Map<Long, Map<Long, Float>> map)
	{
		TreeMap<Long,TreeMap<Long, Float>> newGraph = new TreeMap<Long,TreeMap<Long, Float>>();
		Iterator<Entry<Long, Map<Long, Float>>> it = map.entrySet().iterator();
		long key;
		
		while(it.hasNext())
		{
			key = (long) (it.next().getKey());
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
		Time currentTime = new Time(startTime);
		Path pathFound;
		List<Long> intersectionIds;
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		
		for (int i = 0; i < listPath.size()-1; i++) {
			intersectionIds = dijkstra.createPathIds(listPath.get(i), listPath.get(i + 1));
			Long arrivalId = listPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes();
			round.addPath(pathFound);
		}
		return round;
		
		
	}
	
	public Round startTSPMinDistance(int timeLimit, int nbIntersections, Map<Long, Map<Long, Float>> graph, Time startTime, Dijkstra dijkstra)
	{
		TSPMinDistance tsp = new TSPMinDistance();
		Map<Long, TreeMap<Long, Float>> newGraph = mapToTreeMap(graph);
		tsp.startTSP(timeLimit, nbIntersections, newGraph);
		List<Long>listPath =tsp.getBestSolution();
		listPath.add(MapManagement.getInstance().getWarehouse().getId());
		Time currentTime = new Time(startTime);
		Path pathFound;
		List<Long> intersectionIds;
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		
		for (int i = 0; i < listPath.size()-1; i++) {
			intersectionIds = dijkstra.createPathIds(listPath.get(i), listPath.get(i + 1));
			Long arrivalId = listPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes();
			round.addPath(pathFound);
		}
		return round;
		
		
	}
	
	public Round startTSPMatrix(int timeLimit, int nbIntersections, Map<Long, Map<Long, Float>> graph, Time startTime, Dijkstra dijkstra) throws Exception
	{
		TSPMatrix tsp = new TSPMatrix();
		Map<Long, TreeMap<Long, Float>> newGraph = mapToTreeMap(graph);
		tsp.startTSP(timeLimit, nbIntersections, newGraph);
		List<Long>listPath =tsp.getBestSolution();
		listPath.add(MapManagement.getInstance().getWarehouse().getId());
		Time currentTime = new Time(startTime);
		Path pathFound;
		List<Long> intersectionIds;
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		
		for (int i = 0; i < listPath.size() - 1; i++) {
			intersectionIds = dijkstra.createPathIds(listPath.get(i), listPath.get(i + 1));
			Long arrivalId = listPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes();
			round.addPath(pathFound);
		}
		
		if(!round.getEndTime().isBefore(MapManagement.getInstance().getEndOfDay()) || round.getTotalDuration()>36000) {
			throw new FunctionalException("The round finishes after the end of the working day");
		}
		
		return round;	
		
	}
	
	public void stopAlgorithm(){
            stop.set(true);
        }




	
}
