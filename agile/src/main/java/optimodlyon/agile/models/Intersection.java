package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Intersection {
    protected Long id;
    protected float latitude;
    protected float longitude;

    public Intersection(Long id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Intersection(Long id) {
        this.id = id;
    }

    public Intersection(Intersection i) {
        id=i.id;
        latitude=i.latitude;
        longitude=i.longitude;
    }


    /**
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /*
     * retrieves the distance between 2 intersections
     */
    public static double distance(Intersection i1, Intersection i2) {
    	return Math.sqrt(Math.pow(i1.latitude-i2.latitude, 2)+Math.pow(i1.longitude-i2.longitude, 2));
    }

    public List<Intersection> findSuccessorSegments()
    {
        Map<Long, List<Segment>> myhmap = MapManagement.getInstance().getMap().getGraph();
        List<Segment> seg = MapManagement.getInstance().getMap().getGraph().get(id);
        List <Intersection> endIntersection = new ArrayList<Intersection>();
        
        for(Segment s : seg) {
            Intersection e=s.getEnd();
            endIntersection.add(e);
        }
        
        return endIntersection;
    }

    public String toString() {
        return "Intersection id : " + id + " lat : "+latitude +  " long : "+longitude;
    }
}

