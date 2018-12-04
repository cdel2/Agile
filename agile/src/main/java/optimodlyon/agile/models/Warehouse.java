package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Warehouse extends Delivery {
	private Date timeStart;
	
	public Warehouse(Long id, float latitude, float longitude, Date ts)
	{
		super(id, 600);
		timeStart = ts;
	}
	
	public Warehouse(Long id, Date ts)
	{
		super(id, 600);
		timeStart = ts;
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

}
