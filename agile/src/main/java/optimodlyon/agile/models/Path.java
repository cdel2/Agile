package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.List;

import optimodlyon.agile.util.Time;


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
	public Path(List<Long> idIntersections, Delivery arrival) {
		listSegment = new ArrayList<Segment>();
		duration = 0;
		for(int i=0; i<idIntersections.size()-1; i++) {
			Long origin = idIntersections.get(i);
			Long destination = idIntersections.get(i+1);
			Segment currentSegment = MapManagement.getInstance().getMap().getSegmentFromGraph(origin, destination);
			listSegment.add(currentSegment);
			//System.out.println(currentSegment);
			duration+=currentSegment.getDuration();
		}
		Long idDelivery = this.findEnd().getId();
		this.arrival = arrival;
	}

	/**
	 * @return the duration
	 */
	public Time getDepartureTime() {
		Delivery delivery = MapManagement.getInstance().getDeliveryById(findStart().getId());
		Time previousDeliveryArrival = delivery.getTimeArrival();
		System.out.println(delivery);
		Time departureTime = previousDeliveryArrival.addTime(delivery.getDuration());
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


	public Intersection findEnd()
	{
		Intersection end = (listSegment.get(listSegment.size()-1)).getEnd();
		return end;
	}
	
	public String toString()
	{
		String path = "start : " + this.findStart().toString() + " end : " + this.findEnd().toString() + "\n" + listSegment.toString();
		return path;
	}
	
	public void addSegment(Segment aSegment) {
		this.listSegment.add(aSegment);
	}
	
}
