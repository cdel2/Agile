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

    public Map<Long,Map<Long,Float>> doDijkstra (HashMap<Long, ArrayList<Segment>> completeMap, ArrayList<Long> listDeliveryPoints){
    	Map<Long,Map<Long,Float>> dijkstraGraph = new HashMap<Long,Map<Long,Float>>();

        return dijkstraGraph;
    }

    public Map<Long, Map<Long, Float>> findShortestPathsFromSource (HashMap<Long, ArrayList<Segment>> completeMap, ArrayList<Long> listDeliveryPoints, Long source){
        //Map idIntersection, length from source
    	Map<Long, Float> white = new HashMap<Long, Float>();
    	//Map idIntersection, <idPredecessor,lengthFromSource> 
    	Map<Long, Map<Long, Float>> black = new HashMap<Long, Map<Long, Float>>();
        //Initialize all distances to infinite except source (=0)
        for (Long key : completeMap.keySet()) {
            if(key != source){
                white.put(key, Float.MAX_VALUE);
            } else {
                white.put(key, (float)0);
            }
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
}