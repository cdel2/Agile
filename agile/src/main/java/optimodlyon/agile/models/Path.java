package optimodlyon.agile.models;

import java.util.ArrayList;


public class Path {
	private float duration;
	private ArrayList<Segment> listSegment;
	
	/*
	Delivery getStartDelivery()
	{
		Delivery start = this.listSegment.get(0).getStart();
		return start;
	}*/

	/**
	 * @return the duration
	 */
	public float getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(float duration) {
		this.duration = duration;
	}

/*
	Delivery getEndDelivery()
	{
		Delivery end = (listSegment.get(listSegment.size()-1)).getStart();
		return end;
	}
	*/
}
