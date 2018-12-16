package optimodlyon.agile.algorithmic;

import optimodlyon.agile.exceptions.FunctionalException;
import optimodlyon.agile.models.*;
import optimodlyon.agile.util.Time;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

public class TSP {
    private volatile AtomicBoolean stop;

	/**
	 * Transforms the data structure given by the dijkstra algorithm into a data structure that
	 * can be used to execute the TSP algorithm
	 * @param map the structure as given after executing the dijkstra algorithm
	 * @return the same datas but in different structure in order to execute the TSP algorithm
	 */
	public Map<Long, TreeMap<Long, Float>> mapToTreeMap(Map<Long, Map<Long, Float>> map)
	{
		TreeMap<Long,TreeMap<Long, Float>> newGraph = new TreeMap<Long,TreeMap<Long, Float>>();
		//We iterate over the existing data structure
		Iterator<Entry<Long, Map<Long, Float>>> it = map.entrySet().iterator();
		long key;
		
		while(it.hasNext())
		{
			//for each element of map we create a TreeMap with the same data as the Map
			key = (long) (it.next().getKey());
			Map<Long, Float> currentMap = map.get(key);
			TreeMap<Long, Float> treeMap = new TreeMap<Long, Float>(currentMap);
			//We add the key (the id of the delivery) and the TreeMap (containing the successors and their distances
			newGraph.put(key, treeMap);
		}
		
		return newGraph;


	}
	
	/**
	 * Start the execution of the TSP using the "Closest Delivery" heuristic
	 * @param timeLimit time to execute the algorithm, if it takes more time, the calculations stop
	 * @param nbIntersections the number of intersections (or delivery) that needs to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @param startTime the time at which the Round starts
	 * @param dijkstra the object containing the path to take to go from a delivery point to another
	 * @return the Round that one deliverer has to do
	 */
	public Round startTSPClosestDelivery(int timeLimit, int nbIntersections, Map<Long, Map<Long, Float>> graph, Time startTime, Dijkstra dijkstra)
	{
		TSPClosestDelivery tsp = new TSPClosestDelivery();
		//We transform the data structure to execute the TSP algorithm
		Map<Long, TreeMap<Long, Float>> newGraph = mapToTreeMap(graph);
		//We execute the algorithm
		tsp.startTSP(timeLimit, nbIntersections, newGraph);
		List<Long>listPath =tsp.getBestSolution(); //Ordered list of ids of the delivery points
		listPath.add(MapManagement.getInstance().getWarehouse().getId()); //we add the warehouse'id to complete the Round
		Time currentTime = new Time(startTime);
		Path pathFound;
		List<Long> intersectionIds;
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		
		for (int i = 0; i < listPath.size()-1; i++) {
			intersectionIds = dijkstra.createPathIds(listPath.get(i), listPath.get(i + 1)); //get the full ordered list of ids
			Long arrivalId = listPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes(); //we compute the time at which the deliverer arrives for each delivery point
			round.addPath(pathFound);
		}
		
		//In order to respect the deliverers work schedule 
		if(!round.getEndTime().isBefore(MapManagement.getInstance().getEndOfDay()) || round.getTotalDuration()>36000) {
			throw new FunctionalException("The round finishes after the end of the working day");
		}
		return round;
		
		
	}
	
	/**
	 * Start the execution of the TSP using the "Sum of minimal distance" heuristic
	 * @param timeLimit time to execute the algorithm, if it takes more time, the calculations stop
	 * @param nbIntersections the number of intersections (or delivery) that needs to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @param startTime the time at which the Round starts
	 * @param dijkstra the object containing the path to take to go from a delivery point to another
	 * @return the Round that one deliverer has to do
	 */
	public Round startTSPMinDistance(int timeLimit, int nbIntersections, Map<Long, Map<Long, Float>> graph, Time startTime, Dijkstra dijkstra)
	{
		TSPMinDistance tsp = new TSPMinDistance();
		//We transform the data structure to execute the TSP algorithm
		Map<Long, TreeMap<Long, Float>> newGraph = mapToTreeMap(graph);
		//We execute the algorithm
		tsp.startTSP(timeLimit, nbIntersections, newGraph);
		List<Long>listPath =tsp.getBestSolution(); //Ordered list of ids of the delivery points
		listPath.add(MapManagement.getInstance().getWarehouse().getId()); //we add the warehouse'id to complete the Round
		Time currentTime = new Time(startTime);
		Path pathFound;
		List<Long> intersectionIds;
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		
		for (int i = 0; i < listPath.size()-1; i++) {
			intersectionIds = dijkstra.createPathIds(listPath.get(i), listPath.get(i + 1)); //get the full ordered list of ids
			Long arrivalId = listPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes(); //we compute the time at which the deliverer arrives for each delivery point
			round.addPath(pathFound);
		}
		
		//In order to respect the deliverers work schedule 
		if(!round.getEndTime().isBefore(MapManagement.getInstance().getEndOfDay()) || round.getTotalDuration()>36000) {
			throw new FunctionalException("The round finishes after the end of the working day");
		}
		
		return round;
		
		
	}
	
	/**
	 * Start the execution of the TSP using the "Closest Delivery" heuristic
	 * @param timeLimit time to execute the algorithm, if it takes more time, the calculations stop
	 * @param nbIntersections the number of intersections (or delivery) that needs to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @param startTime the time at which the Round starts
	 * @param dijkstra the object containing the path to take to go from a delivery point to another
	 * @return the Round that one deliverer has to do
	 */
	public Round startTSPMatrix(int timeLimit, int nbIntersections, Map<Long, Map<Long, Float>> graph, Time startTime, Dijkstra dijkstra) throws Exception
	{
		TSPMatrix tsp = new TSPMatrix();
		//We transform the data structure to execute the TSP algorithm
		Map<Long, TreeMap<Long, Float>> newGraph = mapToTreeMap(graph);
		//We execute the algorithm
		tsp.startTSP(timeLimit, nbIntersections, newGraph);
		List<Long>listPath =tsp.getBestSolution(); //Ordered list of ids of the delivery points
		listPath.add(MapManagement.getInstance().getWarehouse().getId()); //we add the warehouse'id to complete the Round
		Time currentTime = new Time(startTime);
		Path pathFound;
		List<Long> intersectionIds;
		Round round = new Round(MapManagement.getInstance().getWarehouse(), startTime);
		
		for (int i = 0; i < listPath.size() - 1; i++) {
			intersectionIds = dijkstra.createPathIds(listPath.get(i), listPath.get(i + 1)); //get the full ordered list of ids
			Long arrivalId = listPath.get(i+1);
			Delivery arrival = MapManagement.getInstance().getDeliveryById(arrivalId);
			pathFound = new Path(intersectionIds, arrival, currentTime);
			pathFound.setSegmentsPassageTimes(); //we compute the time at which the deliverer arrives for each delivery point
			round.addPath(pathFound);
		}
		
		//In order to respect the deliverers work schedule 
		if(!round.getEndTime().isBefore(MapManagement.getInstance().getEndOfDay()) || round.getTotalDuration()>36000) {
			throw new FunctionalException("The round finishes after the end of the working day");
		}
		
		return round;	
		
	}
	
	/**
	 * stops the calculation manually
	 */
	public void stopAlgorithm(){
            stop.set(true);
        }




	
}
