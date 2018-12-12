package optimodlyon.agile.algorithmic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TSPMinDistance extends TSPTemplate{
	
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
	
	@Override
	protected  Iterator<Long> iterator(Long current,List<Long> notVisited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph)
	{
		return new IteratorSeq(notVisited, current);
	}
}
