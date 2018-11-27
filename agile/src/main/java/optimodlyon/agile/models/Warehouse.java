package optimodlyon.agile.models;

import java.util.Date;

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
}
