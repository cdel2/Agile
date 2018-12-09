package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.List;

import optimodlyon.agile.util.Time;


public class Path {
    private float duration;
    private ArrayList<Segment> listSegment;
    private Delivery arrival;
    Time departureTime;


    public Path() {
        duration = 0;
        listSegment.clear();
    }

    public Path(List<Long> idIntersections, Delivery arrival, Time currentTime) {
        departureTime=new Time(currentTime);
        listSegment = new ArrayList<Segment>();
        duration = 0;
        
        for(int i=0; i<idIntersections.size()-1; i++) {
            Long origin = idIntersections.get(i);
            Long destination = idIntersections.get(i+1);
            Segment currentSegment = MapManagement.getInstance().getMap().getSegmentFromGraph(origin, destination);
            listSegment.add(new Segment(currentSegment));
            duration+=currentSegment.getDuration();
        }
        
        this.arrival = new Delivery(arrival);
        currentTime.addTime(duration);
        this.arrival.setTimeArrival(currentTime);
        currentTime.addTime(this.arrival.getDuration());
    }

    public void setSegmentsPassageTimes() {
        Time currentTime = new Time(departureTime);
        for (Segment seg : listSegment) {
            seg.setPassageTime(new Time(currentTime));
            currentTime.addTime(seg.getDuration());
        }
    }

    /**
     * @return the duration
     */
    public Time getDepartureTime() {
        return departureTime;
    }


    public Intersection findStart()
    {
        Intersection start = this.listSegment.get(0).getStart();
        return start;
    }

    /**
     * @return the duration
     */
    public float getDuration() {
        return duration;
    }

    /**
     * @return the delivery at the end of the path
     */
    public Delivery getArrival() {
        return arrival;
    }

    public ArrayList<Segment> getListSegment() {
        return this.listSegment;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }

    /**
     * @param a the delivery to set
     */
    public void setArrival(Delivery a) {
        this.arrival = a;
    }


    public Intersection findEnd()
    {
        Intersection end = (listSegment.get(listSegment.size()-1)).getEnd();
        return end;
    }

    public String toString()
    {
        String path = "start : " + this.findStart().toString() + " end : " + this.findEnd().toString() +  " arrival : " + this.arrival.toString() + "\n";// + listSegment.toString();
        return path;
    }

    public void addSegment(Segment aSegment) {
        this.listSegment.add(aSegment);
    }	
}
