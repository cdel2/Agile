package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.List;


public class Path {
	private float duration;
	private ArrayList<Segment> listSegment;
	private Delivery arrival;
	
	public Path() {
		duration = 0;
		listSegment.clear();
	}
	
	/**
	 * 
	 */
	public Path(List<Long> idIntersections, CityMap map, Delivery arrival) {
		listSegment = new ArrayList<Segment>();
		duration = 0;
		for(int i=0; i<idIntersections.size()-1; i++) {
			Long origin = idIntersections.get(i);
			Long destination = idIntersections.get(i+1);
			Segment currentSegment = map.getSegmentFromGraph(origin, destination);
			listSegment.add(currentSegment);
			//System.out.println(currentSegment);
			duration+=currentSegment.getDuration();
		}
		Long idDelivery = this.getEnd().getId();
		this.arrival = arrival;
	}
	
	
	public Intersection getStart()
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
	
	public ArrayList<Segment> getPath() {
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


	public Intersection getEnd()
	{
		Intersection end = (listSegment.get(listSegment.size()-1)).getEnd();
		return end;
	}
	
	public String toString()
	{
		String path = "start : " + this.getStart().toString() + " end : " + this.getEnd().toString() + "\n" + listSegment.toString();
		return path;
	}
	
	public void addSegment(Segment aSegment) {
		this.listSegment.add(aSegment);
	}
	
	
	
}
