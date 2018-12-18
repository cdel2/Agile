package optimodlyon.agile.algorithmic;

import java.util.*;
import java.util.Map.Entry;

import optimodlyon.agile.models.MapManagement;

public abstract class  TSPTemplate implements TSPInterface {
	private List<Long> bestSolution;
	private float bestDuration;
	
	/*
	 * Starts the calculations of the best Solution and best duration with the TSP algorithm
	 * @param timeLimit time to execute the algorithm, if it takes more time, the calculations stop
	 * @param nbIntersections the number of intersections (or delivery) that needs to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * 
	 */
	public void startTSP(int timeLimit, int nbIntersections, Map<Long, TreeMap<Long, Float>> graph)
	{
		setBestDuration(Float.MAX_VALUE);
		setBestSolution(new ArrayList<Long>());
		List<Long> notVisited = new ArrayList<Long>();
		List<Long> listDeliveries = new ArrayList<Long>();
		Iterator<Entry<Long, TreeMap<Long, Float>>> it = graph.entrySet().iterator(); 
		long key;
		//The first intersection that needs to be visited is the warehouse
		listDeliveries.add(MapManagement.getInstance().getWarehouse().getId());
		outerloop:
		//we add the list of deliveries and the list of unvisited deliveries all the ids contained in graph
		while(it.hasNext())
		{
			if(!MapManagement.getInstance().getIsRunning()) break outerloop;
			key = (long) ( it.next().getKey());
			notVisited.add(key);
			if(!listDeliveries.contains(key))
			{
				listDeliveries.add(key);

			}
			
		}
		
		//the first intersection we visit is the warehouse
		notVisited.remove(MapManagement.getInstance().getWarehouse().getId());
		List<Long> visited = new ArrayList<Long>();
		visited.add(MapManagement.getInstance().getWarehouse().getId()); 
		branchAndBound(MapManagement.getInstance().getWarehouse().getId(), notVisited, visited, 0, listDeliveries, graph, System.currentTimeMillis(), timeLimit);
		
	}

	/*
	 * Branch Bound method let explore all the different possibilities but reduces the number 
	 * thanks to a computed upper bound
	 * @param id the id of the delivery point we are currently visiting
	 * @param notVisited the list of the delivery points' ids that have not been visited yet
	 * @param currentDuration the duration of the round calculated yet
	 * @param listDeliveries the list of the delivery points's ids that have to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @param timeStart the time at which we started the calculations
	 * @param timeLimit time to execute the algorithm, if it takes more time, the calculations stop
	 */
	void branchAndBound(Long id, List<Long >notVisited,  List<Long> visited, float currentDuration, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph, long timeStart, int timeLimit) {
		//if we passed the time limit
		if (System.currentTimeMillis() - timeStart > timeLimit){
			 return;
		 }
		//if we visited all the delivery points
	    if (notVisited.size() == 0){ 
	    	currentDuration += (graph.get(id)).firstEntry().getValue();
	    	if (currentDuration < getBestDuration()){ //we found a better solution than the current better solution
	    		
	    		setBestSolution(new ArrayList<Long>(visited));
	    		setBestDuration(currentDuration);
	    	}
	    //if it still worth it to explore the tree of possibilities
	    } else if (currentDuration + bound(id, notVisited, visited, listDeliveries, graph) < getBestDuration()){
	        Iterator<Long> it = iterator(id, notVisited);
	        outerloop:
	        while (it.hasNext()){
	        	if(!MapManagement.getInstance().getIsRunning()) break outerloop;
	        	Long next = (Long) it.next();

	        	visited.add(next);
	        	notVisited.remove(next);
	        	branchAndBound(next, notVisited, visited, currentDuration + (graph.get(id)).get(next), listDeliveries, graph,  timeStart, timeLimit);
		        visited.remove(next);
		        notVisited.add(next);
				
	        	
	        }	    
	    }
	}
	
	/*
	 * Compute the upper bound for the branch and bound
	 * @param current the id of the delivery point we are currently visiting
	 * @param notVisited the list of the delivery points' ids that have not been visited yet
	 * @param visited the list of the delivery points' ids that have been visited
	 * @param listDeliveries the list of the delivery points's ids that have to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @return the value of the upper bound
	 */
	public abstract float bound(long current, List<Long> notVisited, List<Long> visited,List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph);
	
	/*
	 * @param current the id of the delivery point we are currently visiting
	 * @param notVisited the list of the delivery points' ids that have not been visited yet
	 * @return the Iterator over Long
	 */
	protected abstract Iterator<Long> iterator(Long current,List<Long> notVisited);

	/*
	 * @return bestDuration
	 */
	public float getBestDuration() {
		return bestDuration;
	}

	/*
	 * @param bestDuration the new value of bestDuration
	 */
	public void setBestDuration(float bestDuration) {
		this.bestDuration = bestDuration;
	}

	
	/*
	 * @return the bestSolution
	 */
	public List<Long> getBestSolution() {
		return bestSolution;
	}

	/*
	 * @param bstSolution the new value of bestSolution
	 */
	public void setBestSolution(List<Long> bestSolution) {
		this.bestSolution = bestSolution;
	}
		


		
		
		
	
	
	
}
