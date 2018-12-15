package optimodlyon.agile.algorithmic;

import java.util.*;
import java.util.Map.Entry;

import optimodlyon.agile.models.MapManagement;

public abstract class  TSPTemplate implements TSPInterface {
	private List<Long> bestSolution;
	private float bestDuration;
	
	public void startTSP(int timeLimit, int nbIntersections, Map<Long, TreeMap<Long, Float>> graph)
	{
		setBestDuration(Float.MAX_VALUE);
		setBestSolution(new ArrayList<Long>());
		List<Long> notVisited = new ArrayList<Long>();
		List<Long> listDeliveries = new ArrayList<Long>();
		Iterator<Entry<Long, TreeMap<Long, Float>>> it = graph.entrySet().iterator(); 
		long key;
		listDeliveries.add(MapManagement.getInstance().getWarehouse().getId());
		outerloop:
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
		notVisited.remove(MapManagement.getInstance().getWarehouse().getId());
		List<Long> visited = new ArrayList<Long>();
		visited.add(MapManagement.getInstance().getWarehouse().getId()); 
		branchAndBound(MapManagement.getInstance().getWarehouse().getId(), notVisited, visited, 0, listDeliveries, graph, System.currentTimeMillis(), timeLimit);
		
	}


	void branchAndBound(Long id, List<Long >notVisited,  List<Long> visited, float currentDuration, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph, long timeStart, int timeLimit) {
		if (System.currentTimeMillis() - timeStart > timeLimit){
			 return;
		 }
	    if (notVisited.size() == 0){ 
	    	currentDuration += (graph.get(id)).firstEntry().getValue();
	    	if (currentDuration < getBestDuration()){ // on a trouve une solution meilleure que meilleureSolution
	    		
	    		setBestSolution(new ArrayList<Long>(visited));
	    		setBestDuration(currentDuration);
	    	}
	    } else if (currentDuration + bound(id, notVisited, visited, listDeliveries, graph) < getBestDuration()){
	        Iterator<Long> it = iterator(id, notVisited, listDeliveries, graph );
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
	
	
	protected abstract float bound(long current, List<Long> notVisited, List<Long> visited,List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph);
	
	protected abstract Iterator<Long> iterator(Long current,List<Long> notVisited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph);


	public float getBestDuration() {
		return bestDuration;
	}


	public void setBestDuration(float bestDuration) {
		this.bestDuration = bestDuration;
	}


	public List<Long> getBestSolution() {
		return bestSolution;
	}


	public void setBestSolution(List<Long> bestSolution) {
		this.bestSolution = bestSolution;
	}
		


		
		
		
	
	
	
}
