package optimodlyon.agile.algorithmic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class TSPClosestDelivery extends TSPTemplate{
	
	@Override
	protected float bound(long current, List<Long> notVisited, List<Long> visited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph)
	{
		float nextStep =Integer.MAX_VALUE;
		for(long i : notVisited){
			if(nextStep>graph.get(current).get(i)){
				nextStep=graph.get(current).get(i);
			}
		}
		return nextStep;	}
	
	@Override
	protected  Iterator<Long> iterator(Long current,List<Long> notVisited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph)
	{
		return new IteratorSeq(notVisited, current);
	}
		


}
