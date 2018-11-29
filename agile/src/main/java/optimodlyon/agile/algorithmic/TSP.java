package optimodlyon.agile.algorithmic;

import optimodlyon.agile.models.*;
import java.awt.Paint;
import java.awt.font.GraphicAttribute;
import java.io.Console;
import java.util.*;
import javafx.util.*;


import org.springframework.context.support.StaticApplicationContext;

//TODO : Commenter et mettre en forme cette merde parce que c'est vraiment pas beau;

public class TSP {
	/**
	 * Main class (used for tests) 
	 * To remove
	 */
	public static void main(String[] args) {
		TSP tsp = new TSP();
		Map<Long, Float> successors1 = new HashMap<Long, Float>();
		successors1.put((long)2, (float)8);
		successors1.put((long)3, (float)7);
		successors1.put((long)4, (float)6);

		Map<Long, Float> successors2 = new HashMap<Long, Float>();
		successors2.put((long)1, (float)2);
		successors2.put((long)3, (float)3);
		successors2.put((long)4, (float)1);

		Map<Long, Float> successors3 = new HashMap<Long, Float>();
		successors3.put((long)1, (float)2);
		successors3.put((long)2, (float)3);
		successors3.put((long)4, (float)1);

		Map<Long, Float> successors4 = new HashMap<Long, Float>();
		successors4.put((long)1, (float)8);
		successors4.put((long)2, (float)4);
		successors4.put((long)3, (float)5);

		Map<Long, Map<Long, Float>> map = new HashMap<Long, Map<Long, Float>>();
		map.put((long)1, successors1);
		map.put((long)2, successors2);
		map.put((long)3, successors3);
		map.put((long)4, successors4);
		tsp.doTSP(map, (long)1);
		
		
		
	}
	
	/**
     * doTSP is the only function needed to do the TSP algorithm, 
     * it retrieves a pair of an ordonned array describing the path and its length
     * from a graph that contains lengths between a Node and all possible destinations
     * 
     * @param map
     * @param idWarehouse
     * @return PathLength
     */
	public PathLength doTSP(Map<Long, Map<Long, Float>> map, Long idWarehouse){
		//Get all possible paths in the graph
		List<PathLength> possiblePaths = startTSP(map, idWarehouse);
		System.out.println("Liste des chemins possibles : " + possiblePaths);
		//Find the shortest path
		PathLength shortestPath = findShortestPath(possiblePaths);
		System.out.println("Chemin le plus court trouvé : " + shortestPath.getPath() + "de longueur : " + shortestPath.getLength());
		return shortestPath;
	}
	
	/**
     * startTSP gets all possible paths to travell through all points and their length
     * from an ordered graph (map)
     * 
     * @param 
     * @param idWarehouse
     * @return List<PathLength>
     */
	List<PathLength> startTSP(Map<Long, Map<Long, Float>> unorderedMap, Long idWarehouse) {
		System.out.println(unorderedMap);
		//This list will contain all the resulting pair of (path, length) possible.
		List<PathLength> finalResults = new ArrayList<PathLength>(); 
		//This contains all the remainings successors (we remove every visited node)
		
		Map<Long, Float> successors = new HashMap<Long, Float>(unorderedMap.get(idWarehouse));
		unorderedMap.remove(idWarehouse);
		List<Long> currentPath = new ArrayList();
    	Float currentLength;
    	//We iterate on all possible first node
    	Iterator it = successors.entrySet().iterator();
    	while (it.hasNext()) {
	    	currentPath.clear();
	    	currentPath.add(idWarehouse);
	    	currentLength =(float) 0;
	        Map.Entry currentPair = (Map.Entry)it.next();
	        it.remove(); // avoids a ConcurrentModificationException
	        Map<Long, Float> currentSuccessors = new HashMap<Long, Float>(unorderedMap.get(currentPair.getKey()));
	        Map<Long, Map<Long, Float>> newUnordoredMap = copyMap(unorderedMap);
	        //We add the current pair to current path
	        newUnordoredMap.remove(currentPair.getKey());
        	currentLength+=(float)currentPair.getValue();
        	currentPath.add((long)currentPair.getKey());
        	//We add all possible paths to finalResults
	        finalResults = nextNode(newUnordoredMap, currentSuccessors, currentPath, currentLength, finalResults);
    	}
    	return(finalResults);
	}
	
	/**
     * Recursively add a node to a path from an ordered graph (map) until this map is empty. 
     * Everytime it's empty, adds the final path to finalPaths and returns it.
     * 
     * @param unordered
     * @param currentSuccessors
     * @param currentPath
     * @param currentLength
     * @param finalResults
     * @return List<PathLength>
     */
	@SuppressWarnings("rawtypes")
	List<PathLength> nextNode(Map<Long, Map<Long, Float>> unorderedMap, Map<Long, Float> currentSuccessors, List<Long> currentPath, Float currentLength, List<PathLength> finalResults) {
    	//We check if there are still nodes to visit
		if(unorderedMap.isEmpty()) {
			//if not, we add the current path (which is a possible final path) to finalResults.
			PathLength pathFound = new PathLength(currentPath, currentLength);
			finalResults.add(pathFound);
			return finalResults;
		}
		else {
			//If there are still nodes to visit, add every possible remaining successor to the path
			Iterator it = currentSuccessors.entrySet().iterator();
			// we iterate other all remaining nodes and make a new current path for all of them
			while (it.hasNext()) {
		        Map.Entry newPair = (Map.Entry)it.next();
		        it.remove();
		        if(unorderedMap.containsKey(newPair.getKey())) {
		        	ArrayList<Long> p = new ArrayList<Long>(currentPath);
		        	List<Long> newPath = (List) p.clone();
		        	Float newLength = currentLength;
		        	newLength+=(float)newPair.getValue();
		        	newPath.add((long)newPair.getKey());
			        Map<Long, Float> newSuccessors = new HashMap<Long, Float>(unorderedMap.get(newPair.getKey()));
			        Map<Long, Map<Long, Float>> newUnordoredMap =copyMap(unorderedMap);
			        newUnordoredMap.remove(newPair.getKey());
			        finalResults = nextNode(newUnordoredMap, newSuccessors, newPath, newLength, finalResults);
				}
			}
			return finalResults;
		}
	}
	
	/**
     * Copy a Map<Long, Map<Long, Float>> 
     * 
     * @param map
     * @return Map<Long, Map<Long, Float>>
     */
	Map<Long, Map<Long, Float>> copyMap(Map<Long, Map<Long, Float>> map){
		Map<Long, Map<Long, Float>> newMap = new HashMap<Long, Map<Long, Float>>();
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	    	Map<Long, Float> insideMap = new HashMap<Long, Float>(map.get(pair.getKey()));
	        newMap.put((long)pair.getKey(), insideMap);
		}
		return newMap;
	}
	
	/**
     * Returns the shortest path from a list of paths.
     * 
     * @param possiblePaths
     * @return PathLength
     */
	PathLength findShortestPath(List<PathLength> possiblePaths) {
		if(possiblePaths.size()==0) return null;
		else {
			PathLength bestPath = possiblePaths.get(0);
			for(PathLength path : possiblePaths) {
				if(path.getLength()<bestPath.getLength()) bestPath = path;
			}
			return bestPath;
		}
	}
	
	public List<List<Intersection>> makeRounds(ArrayList<PathLength> list, CityMap map) {
		List<List<Intersection>> rounds = new ArrayList<List<Intersection>>();
		for (PathLength pl : list) {
			List<Intersection> round = new ArrayList<Intersection>();
			List<Long> r = pl.getPath();
			for(Long id : r) {
				System.out.println(list);
				System.out.println(pl);
				System.out.println(id);
				System.out.println(map.graph.containsKey(id));
				ArrayList<Segment> segments = (map.graph).get(id);
				Segment seg = segments.get(0);
				Intersection intersection = seg.getStart();
				round.add(intersection);
			}
			rounds.add(round);
		}
		return rounds;
	}
	
}
