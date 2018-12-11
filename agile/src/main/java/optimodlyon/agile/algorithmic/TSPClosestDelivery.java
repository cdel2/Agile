package optimodlyon.agile.algorithmic;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TSPClosestDelivery extends TSPTemplate{
	
	@Override
	protected float bound(long current, List<Long> notVisited, List<Long> listDeliveries, Map<Long, Map<Long, Float>> graph)
	{
		return 0;
	}
	
	@Override
	protected  Iterator<Long> iterator(Long current,List<Long> notVisited, List<Long> listDeliveries, Map<Long, Map<Long, Float>> graph)
	{
		return new IteratorSeq(notVisited, current);
	}
		


}
