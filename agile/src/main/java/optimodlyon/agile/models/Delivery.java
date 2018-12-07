package optimodlyon.agile.models;
import optimodlyon.agile.util.Time;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	public Delivery(Long id, Time ta) {
		super(id);
		timeArrival = ta;
	}
	
	
	/**
	 * @return the timeArrival
	 */
	public String toString() {
		return "Intersection id : " + id + " lat : "+latitude +  " long : "+longitude + " timeArrival : " + timeArrival;
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
		Segment segment = (graph.get(this.id)).get(0);
		if(segment != null)
		{
			Intersection correspondingInter = segment.getStart();
			this.latitude = correspondingInter.latitude;
			this.longitude = correspondingInter.longitude;
			return true;
		} else {
			return false;
		}
		
	}

}
