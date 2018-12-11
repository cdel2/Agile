package optimodlyon.agile.algorithmic;

import java.util.*;
import java.util.Map.Entry;

import optimodlyon.agile.models.MapManagement;

public abstract class  TSPTemplate implements TSPInterface {
	private List<Long> bestSolution;
	private float bestDuration;
	private boolean timeReached;
	
	
	public void startTSP(int timeLimit, int nbIntersections, Map<Long, Map<Long, Float>> graph)
	{
		timeReached = false;
		bestDuration = Float.MAX_VALUE;
		bestSolution = new ArrayList<Long>();
		List<Long> notVisited = new ArrayList<Long>();
		List<Long> listDeliveries = new ArrayList<Long>();
		Iterator it = graph.entrySet().iterator(); 
		long key;
		while(it.hasNext())
		{
			key = (long) (((Entry) it.next()).getKey());
			notVisited.add(key);
			listDeliveries.add(key);
		}
		List<Long> visited = new ArrayList<Long>();
		visited.add(MapManagement.getInstance().getWarehouse().getId()); 
		branchAndBound(MapManagement.getInstance().getWarehouse().getId(), notVisited, visited, 0, listDeliveries, graph, System.currentTimeMillis(), timeLimit);
		
	}


	void branchAndBound(Long id, List<Long >notVisited,  List<Long> visited, float currentDuration, List<Long> listDeliveries, Map<Long, Map<Long, Float>> graph, long timeStart, int timeLimit) {
		
		long key;
		if (System.currentTimeMillis() - timeStart > timeLimit){
			 timeReached = true;
			 return;
		 }
	    if (notVisited.size() == 0){ 
	    	currentDuration += (graph.get(id)).get(listDeliveries.get(0));
	    	if (currentDuration < bestDuration){ // on a trouve une solution meilleure que meilleureSolution
	    		
	    		bestSolution = new ArrayList<Long>(visited);
	    		bestDuration = currentDuration;
	    	}
	    } else if (currentDuration + bound(id, notVisited, listDeliveries, graph) < bestDuration){
	        Iterator it = iterator(id, notVisited, listDeliveries, graph );
	        while (it.hasNext()){
	        	Long next = (Long) it.next();
	        	visited.add(next);
	        	notVisited.remove(next);
	        	branchAndBound(next, notVisited, visited, currentDuration + (graph.get(id)).get(next), listDeliveries, graph,  timeStart, timeLimit);
	        	visited.remove(next);
	        	notVisited.add(next);
	        }	    
	    }
	}
	
	
	protected abstract float bound(long current, List<Long> notVisited, List<Long> listDeliveries, Map<Long, Map<Long, Float>> graph);
	
	protected abstract Iterator<Long> iterator(Long current,List<Long> notVisited, List<Long> listDeliveries, Map<Long, Map<Long, Float>> graph);
		


		
		
		
	
	
	
}
