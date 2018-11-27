package optimodlyon.agile.models;

import java.util.Date;

public class Delivery extends Intersection{
	private Date timeArrival;
	private float duration; 

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
	public void setDuration(float duration) {
		this.duration = duration;
	}

	/**
	 * @param timeArrival the timeArrival to set
	 */
	public void setTimeArrival(Date timeArrival) {
		this.timeArrival = timeArrival;
	}

}
