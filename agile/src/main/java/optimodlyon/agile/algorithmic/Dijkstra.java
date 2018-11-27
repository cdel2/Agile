package optimodlyon.agile.algorithmic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * List of the Nodes already visited at least once but not yet evaluated (settled)
	 * Corresponds to the "grey" color in the algorithm
	 */
	protected List<Long> visitedNodes;
	/**
	 * List of the Nodes from which we are sure of the shortest distance form source
	 * The value of the Pair is hence definitive
	 * Corresponds to the "black" color in the algorithm
	 */
	Map<Long, Pair> settledNodes;
	
	/**
	 * Empty Constructor
	 */
	public Dijkstra() {
    	dijkstraGraph = new HashMap<Long, Pair>();
    	visitedNodes = new ArrayList<Long>();
    	settledNodes = new HashMap<Long, Pair>();
	}
	
	/**
	 * Constructor
	 * 
	 * @param completeMap : a Map matching the id of a Node with a list of all segments departing from this node
	 * @param source : the id of the Node from which we want to start the Dijkstra algorithm
	 */
	public Dijkstra(Map<Long, List<Segment>> completeMap, Long source) {
		dijkstraGraph = new HashMap<Long, Pair>();
    	visitedNodes = new ArrayList<Long>();
    	settledNodes = new HashMap<Long, Pair>();
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

	}
	
	public Map<Long, Pair> findShortestPathsFromSource (Map<Long, List<Segment>> completeMap, List<Long> listDeliveryPoints, Long source){
        /*
         * s0 is visited(grey)
         */
        visitedNodes.add(source);
        
        /*
         * While there exists a visited(grey) Node
         */
        while(!visitedNodes.isEmpty()) {
        	/*
        	 * Let si be the visited(grey) Node such that d[si] is minimal
        	 */
        	Long currentNode = findClosestNodeInGraph();
        	System.out.println("closestNode : " + currentNode);
        	/*
        	 * For each Node sj that belongs to the successors of si
        	 */
        	List<Long> successors = getIdSuccessors(completeMap,currentNode);
        	for(Long successor : successors) {
        		/*
        		 * if the Node sj is not already settled
        		 */
        		if(!settledNodes.containsKey(successor)) {
	            	System.out.println("	successor : " + successor);
	        		/*
	        		 * if sj is unvisited(white) or visited(grey) then update distance if necessary
	        		 * if sj is unvisited(white), we add it to the visited(grey) list
	        		 */
	        		if(!visitedNodes.contains(successor)) {
	        			visitedNodes.add(successor);
	        			System.out.println("	successor " + successor + " added to graph");
	        			System.out.println("	dist of successor: " + dijkstraGraph.get(successor).getDistFromSource());
	        		}
	        		Float newDist = UpdateDistance(currentNode, successor, completeMap);
	        		/*
	        		 * If the distance from source is shorter passing by the currentNode,
	        		 * we update the distance from source of the successor node sj
	        		 */
	        		if(newDist != -1) {
	        			dijkstraGraph.get(successor).setIdPredecessor(currentNode);
	        			dijkstraGraph.get(successor).setDistFromSource(newDist);
	        		}
	        		System.out.println("	dist of successor: " + successor +" is "+ dijkstraGraph.get(successor).getDistFromSource());
	        	}
        	}
	        	/*
	        	 * the current node is settled, its distance from source cannot be reduced
	        	 * we remove the node from the visited list and from the DijsktraGraph since 
	        	 * we won't pass through this node anymore
	        	 */
	        	settledNodes.put(currentNode, dijkstraGraph.get(currentNode));
	        	dijkstraGraph.remove(currentNode);
	        	visitedNodes.remove(currentNode);
        
        }
        return settledNodes;
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
    	for(Segment s : completeMap.get(source)) {
    		successors.add(s.getEnd().getId()) ;
    	}
    	return successors;
    }
    
    /**
     * Function retrieving the id of the closest (from source) Node belonging to the visited Nodes
     * 
     * @return
     */
    public Long findClosestNodeInGraph (){
    	Long closestNode = (long) -1;
    	float minDistance = Float.MAX_VALUE;
    	float distFromSource;
		for (Long key : visitedNodes) {
			System.out.println("Looking for closestNode for :" + key);
    		distFromSource = dijkstraGraph.get(key).getDistFromSource();
    		if(distFromSource < minDistance) {
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
    		if(idNode == goalNode) {
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
			// TODO Auto-generated catch block
			System.out.println("Couldn't get the current and/or goal Distances from dijkstraGraph");
			e.printStackTrace();
		}
    	
    	return newDist;
    }
}
