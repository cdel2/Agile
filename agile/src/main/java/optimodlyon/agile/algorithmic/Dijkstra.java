package optimodlyon.agile.algorithmic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.exceptions.DijkstraException;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Segment;

/**
 * @author William Occelli
 *
 */
public class Dijkstra {
        
    /**
     * Contains the id of the Intersection and a Pair 
     * with its predecessor and its distance from the source
     */
    protected Map<Long,Pair> dijkstraGraph;

    /**
     * Complete Dijkstra Graph
     * Every idnode(A) is mapped with the id of the studied source deliveryPoint(B)
     * and the if of the predecessor(C) of the node(A) to this source(B) in order to find the closest path
     * 
     * each time we execute a dijkstra from a source, we complete the graph, adding the predecessor(C)
     * of the current node(A) on the shortest path to the studied delivery point B
     */
    protected Map<Long, Map<Long, Long>> fullDijkstra;

    /**
     * Empty Constructor
     */
    public Dijkstra() {
    dijkstraGraph = new HashMap<Long, Pair>();
    fullDijkstra = new HashMap<Long, Map<Long,Long>>();
    }

    /**
     * Function called to retrieve a graph containing the minimum length
     * between all deliveryPoints
     * 
     * @param completeMap
     * @param listDeliveryPoints
     * @return Map<idDelivery, Map<idDelivery, lengthBetweenTheTwoDeliveries>>
     */
    public Map<Long, Map<Long, Float>> doDijkstra (Map<Long, List<Segment>> completeMap, List<Long> listDeliveryPoints) throws Exception{
        Map<Long, Map<Long, Float>> tspGraph = new HashMap<Long, Map<Long,Float>>();

        // Initialize the fullGraph
        for (Long key : completeMap.keySet()) {
            Map<Long, Long> m = new HashMap<Long, Long>();
            fullDijkstra.put(key, m);
        }

        for(Long idDeliveryNode : listDeliveryPoints) {
			Map<Long,Float> tspSubGraph = new HashMap<Long, Float>();
			dijkstraGraph.clear();
			tspSubGraph.clear();
			tspSubGraph = findShortestPathsFromSource(completeMap, listDeliveryPoints, idDeliveryNode);
			tspGraph.put(idDeliveryNode,tspSubGraph);
        }
        
        return tspGraph;
    }

	
	public Map<Long, Float> findShortestPathsFromSource (Map<Long, List<Segment>> completeMap, List<Long> listDeliveryPoints, Long source) throws RuntimeException {
            // Map containing the delivery points and the distance between the two
            Map<Long,Float> tspSubGraph = new HashMap<Long, Float>();
            int nbOfDeliveryPointsFound =0;
        
            // List of nodes of which the distance from source is the shortest
            List<Long> settledNodes = new ArrayList<Long>();

            // List of the Nodes already visited at least once but not yet evaluated (settled)
            // Corresponds to the "grey" color in the algorithm
            List<Long> visitedNodes = new ArrayList<Long>();
        
            /*for each Node sj in S do
            *  d[sj] <- MAXVALUE ; 
            *  pred(sj) <- -1;
            *  sj is white;
            *  d[s0] <- 0;
            */
            for (Long key : completeMap.keySet()) {
                if((long)key != (long)source){
                    Pair p = new Pair((long)-1, Float.MAX_VALUE);
                    dijkstraGraph.put(key,p);
                } else {
                    Pair p = new Pair(key, (float)0);
                    dijkstraGraph.put(key, p);
                }
            }
		
        // s0 is visited(grey)
        visitedNodes.add(source);
        
        // While there exists a visited(grey) Node
        while(!visitedNodes.isEmpty()) {
            // Let si be the visited(grey) Node such that d[si] is minimal
            Long currentNode = findClosestNodeInGraph(visitedNodes);
            
            // For each Node sj that belongs to the successors of si
            List<Long> successors = getIdSuccessors(completeMap,currentNode);
            
            for(Long successor : successors) {
                // if the Node sj is not already settled
                if(!settledNodes.contains(successor)) {
                    /*
                     * if sj is unvisited(white) or visited(grey) then update distance if necessary
                     * if sj is unvisited(white), we add it to the visited(grey) list
                     */
                    if(!visitedNodes.contains(successor)) {
                        visitedNodes.add(successor);
                    }

                    // If the node is an end point of a segment but not yet an intersection
                    if(!dijkstraGraph.containsKey(successor)) {
                        Pair p = new Pair(currentNode, Float.MAX_VALUE);
                        dijkstraGraph.put(successor,p);
                        Map<Long,Long> m = new HashMap<Long, Long>();
                        fullDijkstra.put(successor, m);
                    }

                    Float newDist = UpdateDistance(currentNode, successor, completeMap);

                    // If the distance from source is shorter passing by the currentNode,
                    // we update the distance from source of the successor node sj
                    if(newDist != -1) {
                        dijkstraGraph.get(successor).setIdPredecessor(currentNode);
                        dijkstraGraph.get(successor).setDistFromSource(newDist);
                    }
                }
            }
            
            /*
             * the current node is settled, its distance from source cannot be reduced
             * we remove the node from the visited list and from the DijsktraGraph since 
             * we won't pass through this node anymore
             */
            settledNodes.add(currentNode);
            if(listDeliveryPoints.contains(currentNode) && (long)currentNode != (long)source) {
                tspSubGraph.put(currentNode,dijkstraGraph.get(currentNode).getDistFromSource());
            }
            
            fullDijkstra.get(currentNode).put(source, dijkstraGraph.get(currentNode).getIdPredecessor());
            dijkstraGraph.remove(currentNode);
            visitedNodes.remove(currentNode);

            /*
             * If we have found all delivery points, there
             * is no need continuing the algorithm,
             * hence we stop the function and return the current settled nodes
             */
            if(listDeliveryPoints.contains(currentNode)) {
                nbOfDeliveryPointsFound++;
                if(nbOfDeliveryPointsFound == listDeliveryPoints.size()) {
                    break;
                }
            }

        }
        if(nbOfDeliveryPointsFound != listDeliveryPoints.size()) {
        	throw new DijkstraException("One way road");
        }
        return tspSubGraph;
    }
	
    /**
     * Function used to retrieve the id of all successors of a Node (source) in the complete graph
     * Can be improved in order to retrieve only the successors in the dijkstraGraph
     * 
     * @param completeMap
     * @param source : id of the currentNode 
     * @return : list of the id of the successors
     */
    public static List<Long> getIdSuccessors(Map<Long, List<Segment>> completeMap, Long source){
    	ArrayList<Long> successors = new ArrayList<Long>();
    	if(completeMap.containsKey(source)) {
            for(Segment s : completeMap.get(source)) {
                successors.add(s.getEnd().getId()) ;
            }
    	}
    	return successors;
    }
    
    /**
     * Function retrieving the id of the closest (from source) Node belonging to the visited Nodes
     * 
     * @return
     */
    public Long findClosestNodeInGraph (List<Long> visitedNodes){
    	Long closestNode = (long) -1;
    	float minDistance = Float.MAX_VALUE;
    	float distFromSource;
        
        for (Long key : visitedNodes) {
            distFromSource = dijkstraGraph.get(key).getDistFromSource();

            if((float)distFromSource < (float)minDistance) {
                closestNode = key;
                minDistance = distFromSource;
            }
        }
    	return closestNode;
    }
    
    /**
     * Function allowing to know whether it is shorter to access the goalNode from the currentNode
     * In other words, compares the distance from source of the goalNode
     * with the distance from source of the currentNode + the distance between 
     * these two consecutive Nodes
     * 
     * @param currentNode
     * @param goalNode
     * @param completeMap
     * @return -1 if the current distance from source of the goal Node is shorter, the newly calculated distance from source otherwise.
     */
    public Float UpdateDistance(Long currentNode, Long goalNode, Map<Long, List<Segment>> completeMap) {
    	Float newDist = (float)-1;
    	Long idNode;
    	Float distFromCurrentToGoal = Float.MAX_VALUE;
    	Float currentDistToGoal;
    	Float currentDistToCurrent;
    	/*
    	 * We get the length between the currentNode and the goalNode
    	 */
    	for(Segment s : completeMap.get(currentNode)) {
            idNode = s.getEnd().getId();
            if((long)idNode == (long)goalNode) {
                distFromCurrentToGoal = s.getDuration();
                break;
            }
    	}	
    	
    	try {
            /*
             * We get the distance from source of the two nodes
             */
            currentDistToCurrent = dijkstraGraph.get(currentNode).getDistFromSource();
            currentDistToGoal = dijkstraGraph.get(goalNode).getDistFromSource();
            
            if(currentDistToCurrent + distFromCurrentToGoal < currentDistToGoal) {
                newDist = currentDistToCurrent + distFromCurrentToGoal;
            }
        } catch (Exception e) {
            System.out.println("Couldn't get the current and/or goal Distances from dijkstraGraph");
            e.printStackTrace();
        }
    	
    	return newDist;
    }
    
    /**
     * Function that creates the shortestPath between an origin and a destination.
     * This path is an array of ID of intersection
     * 
     * @param origin
     * @param destination
     * @return List<Long> the ordered list of ID of intersection to visit to run the shortest path
     */
    public List<Long> createPathIds(Long origin, Long destination){
    	List<Long> idIntersections = new ArrayList<Long>();
    	Map<Long, Long> map = new HashMap<>();
    	Long currentId = destination;
    	idIntersections.add(destination);
        
    	while((long)currentId!=(long)origin) {
            map = fullDijkstra.get(currentId);
            currentId = map.get(origin);
            idIntersections.add(currentId);
    	}
        
        Collections.reverse(idIntersections);
    	return idIntersections;
    }
}
