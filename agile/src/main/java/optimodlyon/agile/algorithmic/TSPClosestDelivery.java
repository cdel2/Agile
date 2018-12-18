package optimodlyon.agile.algorithmic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class TSPClosestDelivery extends TSPTemplate{
	
	/*
	 * Compute the upper bound for the branch and bound using the "Closest Delivery" heuristic
	 * @param current the id of the delivery point we are currently visiting
	 * @param notVisited the list of the delivery points' ids that have not been visited yet
	 * @param visited the list of the delivery points' ids that have been visited
	 * @param listDeliveries the list of the delivery points's ids that have to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @return the value of the upper bound, the distance of the closest next delivery point
	 */
	@Override
	public float bound(long current, List<Long> notVisited, List<Long> visited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph)
	{
		float nextStep =Integer.MAX_VALUE;
		for(long i : notVisited){
			if(nextStep>graph.get(current).get(i)){
				nextStep=graph.get(current).get(i);
			}
		}
		return nextStep;	
	}
	
	/*
	 * @param current the id of the delivery point we are currently visiting
	 * @param notVisited the list of the delivery points' ids that have not been visited yet
	 * @return the Iterator over Long
	 */
	@Override
	protected  Iterator<Long> iterator(Long current,List<Long> notVisited) 
	{
		return new IteratorSeq(notVisited, current);
	}
		


}
