package optimodlyon.agile.algorithmic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.Segment;

public class Dijkstra2 {
	
	protected Map<Long,Pair> dijkstraGraph;
	protected List<Long> grey;
	public Dijkstra2() {
    	//Map idIntersection, <idPredecessor,lengthFromSource> 
    	dijkstraGraph = new HashMap<Long, Pair>();
    	grey = new ArrayList<Long>();
	}
	
	public static void main(String[] args) {
		
    	Map<Long, List<Segment>>completeMap = new HashMap<Long, List<Segment>>();
        Intersection i0 = new Intersection((long)0,(float)5.0,(float)3.0);
        Intersection i1 = new Intersection((long)1,(float)3.0,(float)3.0);
        Intersection i2 = new Intersection((long)2,(float)4.0,(float)3.0);
        Intersection i3 = new Intersection((long)3,(float)1.0,(float)3.0);
        Segment s0 = new Segment(i0,i1,2);
        Segment s1 = new Segment(i1,i3,3);
        Segment s2 = new Segment(i0,i3,8);
        Segment s3 = new Segment(i0,i2,4);
        Segment s4 = new Segment(i2,i3,1);
        List<Segment> a0 = new ArrayList<Segment>();
        List<Segment> a1 = new ArrayList<Segment>();
        List<Segment> a2 = new ArrayList<Segment>();
        List<Segment> a3 = new ArrayList<Segment>();
        a0.add(s0);
        a0.add(s2);
        a0.add(s3);
        a1.add(s1);
        a2.add(s4);
        completeMap.put((long)0, a0);
        completeMap.put((long)1, a1);
        completeMap.put((long)2, a2);
        completeMap.put((long)3, a3);
        List<Long> listDeliveryPoints = new ArrayList<Long>();
        listDeliveryPoints.add((long)0);
        listDeliveryPoints.add((long)3);
        //System.out.println(completeMap.toString());
        Dijkstra dij = new Dijkstra();
        Pair p = new Pair((long)0, (float)0);
        dij.dijkstraGraph.put((long)0, p);
        List<Long> test = getIdSuccessors(completeMap, (long)0);
        System.out.println(test.toString());
        List<Long> grey = new ArrayList<Long>();
        grey.add((long)0);
        Long e = dij.findClosestNodeInGraph(grey);
        System.out.println(e);
        //test
    }
	
	/*works*/
    public static List<Long> getIdSuccessors(Map<Long, List<Segment>> completeMap, Long source){
    	ArrayList<Long> successors = new ArrayList<Long>();
    	for(Segment s : completeMap.get(source)) {
    		successors.add(s.getEnd().getId()) ;
    	}
    	return successors;
    }
    
    /*works*/
    public Long findClosestNodeInGraph (List<Long> grey){
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
}
