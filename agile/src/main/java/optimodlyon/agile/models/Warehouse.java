package optimodlyon.agile.models;

import java.util.ArrayList;
import optimodlyon.agile.util.Time;
import java.util.HashMap;

public class Warehouse extends Delivery {
	private Time timeStart;
	
	public Warehouse(Long id, float latitude, float longitude, Time ts)
	{
		super(id, 0);
		timeStart = ts;
	}
	
	public Warehouse(Long id, Time ts)
	{
		super(id, 0);
		timeStart = ts;
	}
	
	/**
	 * @return the timeStart
	 */
	public Time getTimeStart() {
		return timeStart;
	}

	/**
	 * @param timeStart the timeStart to set
	 */
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}

}
