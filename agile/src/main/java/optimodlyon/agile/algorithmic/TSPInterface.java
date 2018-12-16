package optimodlyon.agile.algorithmic;

import java.util.Map;
import java.util.TreeMap;

public interface TSPInterface {

	/*
	 * Starts the calculations of the best Solution and best duration with the TSP algorithm
	 * @param timeLimit time to execute the algorithm, if it takes more time, the calculations stop
	 * @param nbIntersections the number of intersections (or delivery) that needs to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * 
	 */
	public void startTSP(int timeLimit, int nbIntersections, Map<Long, TreeMap<Long, Float>> graph);
	
}
