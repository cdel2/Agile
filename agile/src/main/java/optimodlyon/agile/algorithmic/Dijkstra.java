package optimodlyon.agile.algorithmic;
import optimodlyon.agile.models.*;
import java.util.*;

public class Dijkstra {
    public static void main(String[] args) {
        Map<Long, ArrayList<Segment>> completeMap = new HashMap<Long, ArrayList<Segment>>();
        Intersection i0 = new Intersection((long)0,(float)5.0,(float)3.0);
        Intersection i1 = new Intersection((long)1,(float)3.0,(float)3.0);
        Intersection i2 = new Intersection((long)2,(float)4.0,(float)3.0);
        Intersection i3 = new Intersection((long)3,(float)1.0,(float)3.0);
        Segment s0 = new Segment(i0,i1,2);
        Segment s1 = new Segment(i1,i3,3);
        Segment s2 = new Segment(i0,i3,8);
        Segment s3 = new Segment(i0,i2,4);
        Segment s4 = new Segment(i2,i3,1);
        ArrayList<Segment> a0 = new ArrayList<Segment>();
        ArrayList<Segment> a1 = new ArrayList<Segment>();
        ArrayList<Segment> a2 = new ArrayList<Segment>();
        ArrayList<Segment> a3 = new ArrayList<Segment>();
        a0.add(s0);
        a0.add(s2);
        a0.add(s3);
        a1.add(s1);
        a2.add(s4);
        completeMap.put((long)0, a0);
        completeMap.put((long)1, a1);
        completeMap.put((long)2, a2);
        completeMap.put((long)3, a3);
    }

    public Map<Long,Pair> doDijkstra (HashMap<Long, List<Segment>> completeMap, ArrayList<Long> listDeliveryPoints){
    	Map<Long,Pair> dijkstraGraph = new HashMap<Long,Pair>();

        return dijkstraGraph;
    }

    public Map<Long, Pair> findShortestPathsFromSource (Map<Long, List<Segment>> completeMap, List<Long> listDeliveryPoints, Long source){
    	ArrayList<Long> grey = new ArrayList<Long>();
    	grey.add(source);
    	//Map idIntersection, <idPredecessor,lengthFromSource> 
    	Map<Long, Pair> dijkstraGraph = new HashMap<Long, Pair>();
    	//Map idIntersection, <idPredecessor,lengthFromSource> 
    	Map<Long, Pair> black = new HashMap<Long, Pair>();
    	
        /*for each Node sj in S do
    	*  d[sj] <- MAXVALUE ; 
    	*  pred(sj) <- -1;
    	*  sj is white;
    	*  d[s0] <- 0;
    	*/
        for (Long key : completeMap.keySet()) {
            if(key != source){
            	Pair p = new Pair((long)-1, Float.MAX_VALUE);
                dijkstraGraph.put(key,p);
            } else {
            	Pair p = new Pair(key, (float)0);
                dijkstraGraph.put(key, p);
            }
        }
        /*
         * s0 is grey;
         */
        grey.add(source);
        
        /*
         * While there exists a grey Node
         */
        while(!grey.isEmpty()) {
        	/*
        	 * Let si be the grey Node such that d[si] is minimal
        	 */
        	Long currentNode = findClosestNodeInGraph(dijkstraGraph, grey);
        	/*
        	 * For each Node sj that belongs to the successors of si
        	 */
        	List<Long> successors = getIdSuccessors(completeMap,currentNode);
        	for(Long successor : successors) {
        		/*
        		 * if sj is white or grey then update distance if necessary
        		 */
        		if(!grey.contains(successor)) {
        			grey.add(successor);
        		}
        		Float newDist = UpdateDistance(currentNode, successor, completeMap, dijkstraGraph);
        		if(newDist != -1) {
        			dijkstraGraph.get(successor).setIdPredecessor(currentNode);
        			dijkstraGraph.get(successor).setDistFromSource(newDist);
        		}
        	}
        	/*
        	 * sj is black
        	 */
        	black.put(currentNode, dijkstraGraph.get(currentNode));
        	dijkstraGraph.remove(currentNode);
        }
        return black;
    }
    /**
     * 
     * @param adjacentSegments
     * @return
     */
    public Long findClosestNode (ArrayList<Segment> adjacentSegments) {
    	Long closestNode = (long) -1;
    	float minDistance = Float.MAX_VALUE;
    	
    	Iterator<Segment> it = adjacentSegments.iterator();
    	while(it.hasNext()) {
    		if(it.next().getDuration() < minDistance) {
    			closestNode= it.next().getEnd().getId();
    		}
    	}
    	return closestNode;
    }
    
    public Long findClosestNodeInGraph (Map<Long,Pair> dijkstraGraph, List<Long> grey){
    	Long closestNode = (long) -1;
    	float minDistance = Float.MAX_VALUE;
    	float distFromSource;
    	for (Long key : grey) {
    		distFromSource = dijkstraGraph.get(key).getDistFromSource();
    		if(distFromSource < minDistance) {
    			closestNode = key;
    			minDistance = distFromSource;
    		}
    	}
    	return closestNode;
    }
    
    public List<Long> getIdSuccessors(Map<Long, List<Segment>> completeMap, Long source){
    	ArrayList<Long> successors = new ArrayList<Long>();
    	Iterator<Segment> it = completeMap.get(source).iterator();
    	while(it.hasNext()) {
    		successors.add(it.next().getEnd().getId()) ;
    	}
    	return successors;
    }
    /**
     * 
     * @param currentNode
     * @param goalNode
     * @param completeMap
     * @param dijkstraGraph
     * @return newDistance if it is shorter to reach the goalNode passing by the currentNode, -1 otherwise
     */
    public Float UpdateDistance(Long currentNode, Long goalNode, Map<Long, List<Segment>> completeMap, Map<Long, Pair> dijkstraGraph) {
    	Float newDist = (float)-1;
    	Long idNode;
    	Float distFromCurrentToGoal = Float.MAX_VALUE;
    	Float currentDistToGoal;
    	Float currentDistToCurrent;
    	
    	Iterator<Segment> it = completeMap.get(currentNode).iterator();
    	while(it.hasNext()) {
    		idNode = it.next().getEnd().getId() ;
    		if(idNode == goalNode) {
    			distFromCurrentToGoal = it.next().getDuration();
    			break;
    		}
    	}
    	if(dijkstraGraph.get(currentNode) != null) {
    		currentDistToCurrent = dijkstraGraph.get(currentNode).getDistFromSource();
    	} else {
    		currentDistToCurrent = Float.MAX_VALUE;
    	}
    	if(dijkstraGraph.get(goalNode) != null) {
    		currentDistToGoal = dijkstraGraph.get(goalNode).getDistFromSource();
    	} else {
    		currentDistToGoal = Float.MAX_VALUE;
    	}
    	
    	if(currentDistToCurrent + distFromCurrentToGoal < currentDistToGoal) {
    		newDist = currentDistToCurrent + distFromCurrentToGoal;
    	}
    	return newDist;
    }
}