package optimodlyon.agile.algorithmic;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
/*
 * To implement this TSP, we used certain element of thesis we found online
 * link : https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=12&cad=rja&uact=8&ved=2ahUKEwjon5eE-q7fAhUDxoUKHQHiAMQQFjALegQIAhAC&url=https%3A%2F%2Fhrcak.srce.hr%2Ffile%2F236378&usg=AOvVaw3H65VkEVp7BKfJwvkDXgzx
 */
public class TSPMatrix extends TSPTemplate{
	
	
	/*
	 * Compute the upper bound for the branch and bound using the "Matrix" heuristic
	 * @param current the id of the delivery point we are currently visiting
	 * @param notVisited the list of the delivery points' ids that have not been visited yet
	 * @param visited the list of the delivery points' ids that have been visited
	 * @param listDeliveries the list of the delivery points's ids that have to be visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @return the value of the upper bound
	 */
	@Override
	public float bound(long current, List<Long> notVisited, List<Long> visited, List<Long> listDeliveries, Map<Long, TreeMap<Long, Float>> graph)
	{
		//We consider a table represented by the updatedGraph where each (row,column) pair
		//is a path that has not been visited yet
		//We get this graph
		Map<Long, Map<Long, Float>> updatedGraph= generateGraph(visited, graph);
		//we computer the list of ri. ri is for the row number i, the minimum distance
		Map<Long, Float> ri = generateRi(updatedGraph);
		//we computer the list of ri. ci is for the colum number i, the minimum distance-ri
		Map<Long, Float> ci = generateCi(updatedGraph, ri);
		//we compute b, the sum of ri and ci
		float b = computeB(ri, ci);
		return b;
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
	
	/*
	 * From the list of visited intersections we create a graph representing a table with only the unvisited paths
	 * @param current the delivery point we are currently visiting
	 * @param visited the list of the delivery points' ids that have been visited
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 */
	public Map<Long, Map<Long, Float>> generateGraph(List<Long> visited, Map<Long, TreeMap<Long, Float>> graph)
	{
		
		Map<Long, Map<Long, Float>> currentGraph = new HashMap<Long, Map<Long, Float>>(graph);
		Map<Long, Map<Long, Float>> newGraph = new HashMap<Long, Map<Long, Float>>();

		//we remove the visited delivery point from the columns of the table
		for(int i =0; i<visited.size()-1; i++)
		{
			newGraph = removeColumn(visited.get(i), currentGraph);
			currentGraph = new TreeMap<Long, Map<Long, Float>>(newGraph);

		}
		
		//we remove the visited delivery point from the rows of the table
		for(int i =0; i<visited.size()-1; i++)		{
			currentGraph.remove(visited.get(i));
		}
		
		return currentGraph;
	}
	
	/*
	 * @param id the id of the delivery point that has to be removed
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @return the graph without the id 
	 * 
	 */
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

	/*
	 * For each row, compute the corresponding ri
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @return a Map with the id of the delivery as the key and the value of ri as the value
	 */
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
	
	/*
	 * For each row, compute the corresponding ci
	 * @param graph the data structure that contains the deliveries, their successors and the distance between them
	 * @param ri, the Map containing for each delivery (corresponding row) the value of ri
	 * @return a Map with the id of the delivery as the key and the value of ci as the value
	 */
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
			//For each column we create a data structure containing the existing row and the value 
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
	
	/*
	 * @param successors, for each column (key), the value corresponding to the row for which we compute ri
	 * @return the value of ri
	 */
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
	
	/*
	 * @param successors, for each row (key), the value corresponding to the column for which we compute ri
	 * @return the value of ci
	 */
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
	
	/*
	 * @param ri, the map containing for each row (key) the value of the corresponding ri (value)
	 * @param ci, the map containing for each column (key) the value of the corresponding ci (value)
	 * @return the value of b
	 */
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
	
	
	

}
