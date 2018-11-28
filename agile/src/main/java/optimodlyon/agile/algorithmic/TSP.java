package optimodlyon.agile.algorithmic;

import java.awt.Paint;
import java.io.Console;
import java.util.*;
import javafx.util.*;


import org.springframework.context.support.StaticApplicationContext;

//TODO : Commenter et mettre en forme cette merde parce que c'est vraiment pas beau;

public class TSP {

	public void main(String[] args) {
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
		doTSP(map, (long)1);
		
	}
	
	/**
     * 
     * @param map a map containing idNodes as Keys and a map of (idDestinations, distance) as Values
     * @param idWarehouse id of Entrepot in map
     * @return PathLength, which is a class created for TSP which is a pair of an ordonned array symbolising the path and its length
     */
	public PathLength doTSP(Map<Long, Map<Long, Float>> map, Long idWarehouse){
		List<PathLength> possiblePaths = startTSP(map, idWarehouse);
		System.out.println("Liste des chemins possibles : " + possiblePaths);
		PathLength shortestPath = findShortestPath(possiblePaths);
		System.out.println("Chemin le plus court trouvé : " + shortestPath.getPath() + "de longueur : " + shortestPath.getLength());
		return shortestPath;
	}
	
	public List<PathLength> startTSP(Map<Long, Map<Long, Float>> unorderedMap, Long idWarehouse) {
		List<PathLength> finalResults = new ArrayList<PathLength>(); //This list will contain all the resulting pair of (path, length) possible.
		Map<Long, Float> successors = new HashMap<Long, Float>(unorderedMap.get(idWarehouse));
		unorderedMap.remove(idWarehouse);
		List<Long> currentPath = new ArrayList();
    	Float currentLength;
    	Iterator it = successors.entrySet().iterator();
    	while (it.hasNext()) {
	    	currentPath.clear();
	    	currentPath.add(idWarehouse);
	    	currentLength =(float) 0;
	        Map.Entry currentPair = (Map.Entry)it.next();
	        it.remove(); // avoids a ConcurrentModificationException
	        Map<Long, Float> currentSuccessors = new HashMap<Long, Float>(unorderedMap.get(currentPair.getKey()));
	        Map<Long, Map<Long, Float>> newUnordoredMap = copyMap(unorderedMap);
	        newUnordoredMap.remove(currentPair.getKey());
        	currentLength+=(float)currentPair.getValue();
        	currentPath.add((long)currentPair.getKey());
	        finalResults = nextNode(newUnordoredMap, currentSuccessors, currentPath, currentLength, finalResults);
    	}
    	return(finalResults);
	}
	
	@SuppressWarnings("rawtypes")
	public List<PathLength> nextNode(Map<Long, Map<Long, Float>> unordoredMap, Map<Long, Float> currentSuccessors, List<Long> currentPath, Float currentLength, List<PathLength> finalResults) {
//		System.out.println(currentPath);
//		System.out.println(currentLength);
//		System.out.println(unordoredMap);
//		System.out.println(currentSuccessors);
    	if(unordoredMap.isEmpty()) {
			PathLength pathFound = new PathLength(currentPath, currentLength);
			finalResults.add(pathFound);
			return finalResults;
		}
		else {
			Iterator it = currentSuccessors.entrySet().iterator();
			while (it.hasNext()) {
		        Map.Entry newPair = (Map.Entry)it.next();
		        it.remove();
		        if(unordoredMap.containsKey(newPair.getKey())) {
		        	ArrayList<Long> p = new ArrayList<Long>(currentPath);
		        	List<Long> newPath = (List) p.clone();
		        	Float newLength = currentLength;
		        	newLength+=(float)newPair.getValue();
		        	newPath.add((long)newPair.getKey());
			        Map<Long, Float> newSuccessors = new HashMap<Long, Float>(unordoredMap.get(newPair.getKey()));
			        Map<Long, Map<Long, Float>> newUnordoredMap =copyMap(unordoredMap);
			        newUnordoredMap.remove(newPair.getKey());
			        finalResults = nextNode(newUnordoredMap, newSuccessors, newPath, newLength, finalResults);
				}
			}
			return finalResults;
		}
	}
	
	public Map<Long, Map<Long, Float>> copyMap(Map<Long, Map<Long, Float>> map){
		Map<Long, Map<Long, Float>> newMap = new HashMap<Long, Map<Long, Float>>();
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	    	Map<Long, Float> insideMap = new HashMap<Long, Float>(map.get(pair.getKey()));
	        newMap.put((long)pair.getKey(), insideMap);
		}
		return newMap;
	}
	
	public PathLength findShortestPath(List<PathLength> possiblePaths) {
		if(possiblePaths.size()==0) return null;
		else {
			PathLength bestPath = possiblePaths.get(0);
			for(PathLength path : possiblePaths) {
				if(path.getLength()<bestPath.getLength()) bestPath = path;
			}
			return bestPath;
		}
	}
	
}
