package optimodlyon.agile.algorithmic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TSPMinDistance extends TSPTemplate{
	
	/*
	 * Compute the upper bound for the branch and bound using the "Sum of minimum distances" heuristic
	 * @param current the id of the delivery point we are currently visiting
	 * @param notVisited the list of the delivery points' ids that have not been visited yet
	 * @param visited the list of the delivery points' ids that have been visited
	 * @param listDeliveries the list of the delivery points's ids that have to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @return the value of the upper bound, the sum of the minimal distances between the current intersection and the next ones
	 */
	@Override
	protected float bound(long current, List<Long> notVisited, List<Long> visited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph)
	{
		float value =0;
		
		for(Long l : notVisited)
		{
			value = value + graph.get(current).get(l);
		}
		
		return (value);
	}
	
	/*
	 * @param current the id of the delivery point we are currently visiting
	 * @param notVisited the list of the delivery points' ids that have not been visited yet
	 * @param listDeliveries the list of the delivery points's ids that have to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @return the Iterator over Long
	 */
	@Override
	protected  Iterator<Long> iterator(Long current,List<Long> notVisited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph)
	{
		return new IteratorSeq(notVisited, current);
	}
}
