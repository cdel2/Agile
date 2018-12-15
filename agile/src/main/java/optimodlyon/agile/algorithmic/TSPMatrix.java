package optimodlyon.agile.algorithmic;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class TSPMatrix extends TSPTemplate{
	
	@Override
	protected float bound(long current, List<Long> notVisited, List<Long> visited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph)
	{
		Map<Long, Map<Long, Float>> updatedGraph= generateGraph(current, visited, graph);
		Map<Long, Float> ri = generateRi(updatedGraph);
		Map<Long, Float> ci = generateCi(updatedGraph, ri);
		float b = computeB(ri, ci);
		return b;
	}
	
	@Override
	protected  Iterator<Long> iterator(Long current,List<Long> notVisited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph)
	{
		return new IteratorSeq(notVisited, current);
	}
	
	protected Map<Long, Map<Long, Float>> generateGraph(long current, List<Long> visited, Map<Long, TreeMap<Long, Float>> graph)
	{
		
		Map<Long, Map<Long, Float>> currentGraph = new HashMap<Long, Map<Long, Float>>(graph);
		Map<Long, Map<Long, Float>> newGraph = new HashMap<Long, Map<Long, Float>>();

		for(int i =0; i<visited.size()-1; i++)
		{
			newGraph = removeColumn(visited.get(i), currentGraph);
			currentGraph = new TreeMap<Long, Map<Long, Float>>(newGraph);

		}
		
		for(int i =0; i<visited.size()-1; i++)		{
			currentGraph.remove(visited.get(i));
		}
		
		return currentGraph;
	}
	
	public Map<Long, Map<Long, Float>> removeColumn(long id, Map<Long, Map<Long, Float>> graph)
	{
		Iterator<Entry<Long, Map<Long, Float>>> it = graph.entrySet().iterator();
		Map<Long, Map<Long, Float>> newGraph = new HashMap<Long, Map<Long, Float>>();
		long key;
		while (it.hasNext()) {
			key = (long) (it.next().getKey());
			Map<Long, Float> currentSuccessors = new HashMap<Long, Float>(graph.get(key));
			currentSuccessors.remove(id);
			Map<Long, Float> newSuccessors = currentSuccessors;
			newGraph.put(key, newSuccessors);
		}
		return newGraph;
	}
	
	public Map<Long, TreeMap<Long, Float>> mapToTreeMap(Map<Long, Map<Long, Float>> map)
	{
		TreeMap<Long,TreeMap<Long, Float>> newGraph = new TreeMap<Long,TreeMap<Long, Float>>();
		Iterator<Entry<Long, Map<Long, Float>>> it = map.entrySet().iterator();
		long key;
		
		while(it.hasNext())
		{
			key = (long) (it.next().getKey());
			Map<Long, Float> currentMap = map.get(key);
			TreeMap<Long, Float> treeMap = new TreeMap<Long, Float>(currentMap);
			newGraph.put(key, treeMap);
		}
		
		return newGraph;


	}
	
	public Map<Long,Float> generateRi(Map<Long, Map<Long, Float>> graph)
	{
		Map<Long,Float> ri = new HashMap<Long,Float>();
		Iterator<Entry<Long, Map<Long, Float>>> it = graph.entrySet().iterator();
		float r;
		long key;
		while (it.hasNext()) {
			key = (long) (it.next().getKey());
			Map<Long, Float> currentSuccessors = new HashMap<Long, Float>(graph.get(key));
			r = computeRi(currentSuccessors);
			ri.put(key, r);
		}
		return ri;
	}
	
	public Map<Long,Float> generateCi(Map<Long, Map<Long, Float>> graph,Map<Long,Float> ri )
	{
		Map<Long,Float> ci = new HashMap<Long,Float>();
		Iterator<Entry<Long, Map<Long, Float>>> it = graph.entrySet().iterator();
		float c;
		long key;
		Iterator<Entry<Long, Float>> itCol;
		long keyCol;
		Map<Long, Float> successorsRow = new HashMap<Long, Float>();
		Map<Long, Float> successorsCol = new HashMap<Long, Float>();
		Iterator<Entry<Long, Map<Long, Float>>>itRow;
		long keyRow;
		
		while (it.hasNext()) {
			key = (long) (it.next().getKey());
			Map<Long, Float> currentSuccessors = graph.get(key);
			itCol = currentSuccessors.entrySet().iterator();
			while(itCol.hasNext())
			{
				keyCol = (long) (itCol.next().getKey());
				successorsCol.clear();
				if(!ci.containsKey(keyCol))
				{
					itRow = graph.entrySet().iterator();
					while(itRow.hasNext())
					{
						keyRow = (long) (itRow.next().getKey());
						successorsRow = graph.get(keyRow);
						if(successorsRow.containsKey(keyCol))
						{
							successorsCol.put(keyRow,successorsRow.get(keyCol));
						}
						
						
					}
					c = computeCi(successorsCol, ri);
					ci.put(keyCol, c);
				}
			}			
			
		}
		return ci;

	}
	
	
	public float computeRi(Map<Long, Float> successors)
	{
		Iterator<Entry<Long, Float>> it = successors.entrySet().iterator();
		long key = (long) (it.next().getKey());
		float min = successors.get(key);
		float current;
		while(it.hasNext())
		{
			key = (long) (it.next().getKey());			
			current = successors.get(key);
			if(current < min)
			{
				min = current;
			}
		}
		return min;
	}
	
	public float computeCi(Map<Long, Float> successors, Map<Long,Float> ri)
	{
		Iterator<Entry<Long, Float>> it = successors.entrySet().iterator();
		long key = (long) (it.next().getKey());
		float min = successors.get(key) - ri.get(key);
		float current;
		while(it.hasNext())
		{
			key = (long) (it.next().getKey());			
			current = successors.get(key) - ri.get(key);
			if(current < min)
			{
				min = current;
			}
		}
		return min;
	}
	
	public Map<Long, Map<Long, Float>> generateReducedGraph(Map<Long, Map<Long, Float>> graph,Map<Long,Float> ri, Map<Long,Float> ci)
	{
		Map<Long, Map<Long, Float>> newGraph = new HashMap<Long, Map<Long, Float>>();
		Iterator<Entry<Long, Map<Long, Float>>> it = graph.entrySet().iterator();
		long key;
		while (it.hasNext()) {
			key = (long) (it.next().getKey());
			Map<Long, Float> currentSuccessors = new HashMap<Long, Float>(graph.get(key));
			Map<Long, Float> newSuccessors = computeDij(key, currentSuccessors, ri, ci );
			newGraph.put(key, newSuccessors);
		}
		
		return newGraph;
	}
	
	public Map<Long, Float> computeDij(long keySource, Map<Long, Float> successors, Map<Long,Float> ri, Map<Long,Float> ci)
	{
		
		Iterator<Entry<Long, Float>> it = successors.entrySet().iterator();
		long key ;
		float oldD;
		float newD;
		Map<Long, Float> newMap = new HashMap<Long,Float>();
		while(it.hasNext())
		{
			key = (long) (it.next().getKey());			
			oldD = successors.get(key);
			newD = oldD - ri.get(keySource) - ci.get(key);
			newMap.put(key, newD);
			
		}
		return newMap;
	}
	
	public float computeB(Map<Long,Float> ri, Map<Long,Float> ci)
	{
		Iterator<Entry<Long, Float>> itR = ri.entrySet().iterator();
		long key;
		float b = 0;
		while(itR.hasNext())
		{
			key = (long)  (itR.next().getKey());
			b = b + ri.get(key);
			
		}
		Iterator<Entry<Long, Float>> itC = ci.entrySet().iterator();
		while(itC.hasNext())
		{
			key = (long) (itC.next().getKey());
			b = b + ci.get(key);
			
		}
		return b;
	}
	
	public Map<Long, Pair> computePi(Map<Long, Map<Long, Float>> graph)
	{
		Iterator<Entry<Long, Map<Long, Float>>> it1 = graph.entrySet().iterator();
		Iterator<Entry<Long, Float>>  it2;
		float pi = 0;
		float current;
		long key1;
		long key2;
		Map<Long, Pair> result = new HashMap<Long,Pair>();
		while(it1.hasNext())
		{
			key1 = (long) (it1.next().getKey());
			it2 = (graph.get(key1)).entrySet().iterator();
			while(it2.hasNext())
			{
				key2 = (long) (it2.next().getKey());
				current = computePiij(graph, key1, key2);
				if(current > pi)
				{
					pi = current;
					Pair pair = new Pair(key2,pi);
					result.clear();
					result.put(key1, pair);
				}
			}
		}
		return result;
	}
	
	public Map<Long, Pair> computeFirstPi(Map<Long, Map<Long, Float>> graph, long start)
	{
		Iterator<Entry<Long, Float>> it;
		float pi = 0;
		float current;
		long key1 = start;//MapManagement.getInstance().getWarehouse().getId();

		long key2;

		Map<Long, Pair> result = new HashMap<Long,Pair>();

		it = (graph.get(key1)).entrySet().iterator();
		while(it.hasNext())
		{
			key2 = (long) (it.next().getKey());

			current = computePiij(graph, key1, key2);

			if(current >= pi )
			{
				pi = current;
				Pair pair = new Pair(key2,pi);
				result.clear();
				result.put(key1, pair);
			}
		}
		
		return result;
	}
	
	public float computePiij(Map<Long, Map<Long, Float>> graph, long i, long j)
	{
		Iterator<Entry<Long, Map<Long, Float>>> itI = graph.entrySet().iterator();
		float piij = 0;
		Map<Long, Float> successors = new HashMap<Long, Float>();
		float di = 100000;
		float dj = 100000;
		float current;
		long keyJ;
		successors = graph.get(i);
		Iterator<Entry<Long, Float>> itJ = successors.entrySet().iterator();
		while(itJ.hasNext())
		{
			keyJ = (long) (itJ.next().getKey());
			if((keyJ != j))
			{
				current = successors.get(keyJ);
				if(current < di)
				{
					di = current;
				}
				
			}

			
		}
		long keyI;
		while(itI.hasNext())
		{
			keyI = (long) (itI.next().getKey());
			if(i != keyI)
			{
				successors = graph.get(keyI);
				if(successors.containsKey(j))
				{
					current = successors.get(j);
					if(current < dj)
					{
						dj = current;
					}
				}
			}
			
		}
		piij = di + dj;
			
		return piij;
	}

}
