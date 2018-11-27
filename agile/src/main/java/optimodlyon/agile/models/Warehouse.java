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
}
