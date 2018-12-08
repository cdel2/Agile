package optimodlyon.agile.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityMap {
    private float height;
    private float width;
    private Map<Long, List<Segment>> graph = new HashMap<Long, List<Segment>> ();	

    public CityMap() {}
    
    /**
     * @param graph the graph to set
     */
    public void setGraph(Map<Long, List<Segment>> graph) {
        this.graph = graph;
    }

    /**
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * @return the width
     */
    public float getWidth() {
            return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(float width) {
            this.width = width;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(float height) {
            this.height = height;
    }

    /**
     * Finds a segment knowing its origin and its destination
     * 
     * @param origin
     * @param destination
     */
    public Segment getSegmentFromGraph(Long origin, Long destination) {
        List<Segment> segments = graph.get(origin);
        
        for (Segment segment : segments) {
            if((long)segment.getEnd().getId()==(long)destination) {
                return segment;
            }
        }
        System.out.println("pas de segment trouvé");
        return null;
    }

        /**
     * @return the graph
     */
    public Map<Long, List<Segment>> getGraph() {
        return graph;
    }
}


