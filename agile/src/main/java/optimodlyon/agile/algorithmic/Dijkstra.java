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

    public Map<Integer,Map<Integer,Integer>> doDijkstra (HashMap<Integer, ArrayList<Segment>> completeMap, ArrayList<Integer> listDeliveryPoints){
    	Map<Integer,Map<Integer,Integer>> dijkstraGraph = new HashMap<Integer,Map<Integer,Integer>>();

        return dijkstraGraph;
    }

    public Map<Integer,Integer> findShortestPathsFromSource (HashMap<Integer, ArrayList<Segment>> completeMap, ArrayList<Integer> listDeliveryPoints, Integer source){
        Map<Integer, Integer> shortestPaths = new HashMap<Integer, Integer>();
        //Initialize all distances to infinite except source (=0)
        for (Integer key : completeMap.keySet()) {
            if(key != source){
                shortestPaths.put(key, Integer.MAX_VALUE);
            } else {
                shortestPaths.put(key, 0);
            }
        }
        
        return shortestPaths;
    }
}