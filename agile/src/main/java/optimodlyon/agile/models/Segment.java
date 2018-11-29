package optimodlyon.agile.models;

import java.util.ArrayList;

public class Segment {
	private Intersection start;
	private Intersection end;
	private float duration;

	public Segment(Intersection start, Intersection end, float duration) {
		this.start = start;
		this.end = end;
		this.duration = duration;
	}
	public Intersection getStart() 
	{
		return start;
	}

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

	public Intersection getEnd()
	{
		return end;
	}
	
	public String toString() {
		return "start : " + start + " end : " + end + " duration : " + duration;
	}
	
}
