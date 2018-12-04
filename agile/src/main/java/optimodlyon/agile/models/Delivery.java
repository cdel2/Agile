package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Delivery extends Intersection{
	protected Date timeArrival;
	protected float duration; 

	public Delivery(Date ta, float dur, Long id, float latitude, float longitude) {
		super(id, latitude, longitude);
		timeArrival = ta;
		duration = dur;
	}
	
	public Delivery(Long id, float dur) {
		super(id);
		duration = dur;
	}
	
	public Delivery(Long id, Date ta) {
		super(id);
		timeArrival = ta;
	}
	
	/**
	 * @return the timeArrival
	 */
	public String toString() {
		return "Intersection id : " + id + " lat : "+latitude +  " long : "+longitude;
	}

	/**
	 * @return the timeArrival
	 */
	public Date getTimeArrival() {
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


	/**
	 * @param timeArrival the timeArrival to set
	 */
	public void setTimeArrival(Date timeArrival) {
		this.timeArrival = timeArrival;
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
