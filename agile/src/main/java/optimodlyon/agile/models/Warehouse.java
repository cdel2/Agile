package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Warehouse extends Intersection {
	private Date timeArrival;
	private Date timeStart;
	
	public Warehouse(Long id, Date ts)
	{
		super(id);
		timeStart = ts;
	}
	
	/**
	 * @return the timeArrival
	 */
	public Date getTimeArrival() {
		return timeArrival;
	}

	/**
	 * @return the timeStart
	 */
	public Date getTimeStart() {
		return timeStart;
	}

	/**
	 * @param timeStart the timeStart to set
	 */
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * @param timeArrival the timeArrival to set
	 */
	public void setTimeArrival(Date timeArrival) {
		this.timeArrival = timeArrival;
	}
	
	public void findLatitudeLongitude(HashMap<Long, ArrayList<Segment>> graph) {
		Segment segment = (graph.get(this.id)).get(0);
		Intersection correspondingInter = segment.getStart();
		this.latitude = correspondingInter.latitude;
		this.longitude = correspondingInter.longitude;
		
	}
}
