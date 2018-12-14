package optimodlyon.agile.models;
import optimodlyon.agile.util.Time;
import java.util.List;
import java.util.Map;

public class Delivery extends Intersection{
    protected Time timeArrival;
    protected float duration;

    public Delivery(Time ta, float dur, Long id, float latitude, float longitude) {
        super(id, latitude, longitude);
        timeArrival = ta;
        duration = dur;
    }

    public Delivery(Long id, float dur) {
        super(id);
        duration = dur;
    }

    public Delivery(Delivery d) {
        super(d);
        timeArrival=d.timeArrival;
        duration=d.duration;
    }

    /*
     * Creates a delivery from an intersection and a duration.
     */
    public Delivery(Intersection i, float duration) {
        super(i);
        this.duration=duration;
    }

    public Delivery(Long id, Time ta) {
        super(id);
        timeArrival = ta;
    }


    /**
     * @return the timeArrival
     */
    public String toString() {
        return "Delivery id : " + id + " lat : "+latitude +  " long : "+longitude + " timeArrival : " + timeArrival;
    }

    /**
     * @return the timeArrival
     */
    public Time getTimeArrival() {
        return timeArrival;
    }

    /**
     * @return the durationDelivery
     */
    public float getDuration() {
        return duration;
    }

    /**
     * @param durationDelivery the durationDelivery to set
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * @param timeArrival the timeArrival to set
     */
    public void setTimeArrival(Time timeArrival) {
        this.timeArrival = new Time(timeArrival);
    }

    public boolean findLatitudeLongitude(Map<Long, List<Segment>> graph) {
        List<Segment> segmentID = graph.get(this.id);
    	System.out.println(segmentID);
        if(segmentID != null)
        {
        Segment segment = segmentID.get(0);
        	
        	Intersection correspondingInter = segment.getStart();
            this.latitude = correspondingInter.latitude;
            this.longitude = correspondingInter.longitude;
            return true;
        } else {
            return false;
        }
    }

}
