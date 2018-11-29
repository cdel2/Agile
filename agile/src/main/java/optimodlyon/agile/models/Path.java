package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.List;


public class Path {
	private float duration;
	private ArrayList<Segment> listSegment;
	
	/**
	 * Constructeur d'un path à partir d'une liste d'ID d'intersections et d'une map
	 * 
	 * @return the duration
	 */
	public Path(List<Long> idIntersections, CityMap map) {
		listSegment = new ArrayList<Segment>();
		duration = 0;
		for(int i=0; i<idIntersections.size()-1; i++) {
			Long origin = idIntersections.get(i);
			Long destination = idIntersections.get(i+1);
			Segment currentSegment = map.getSegmentFromGraph(origin, destination);
			listSegment.add(currentSegment);
			duration+=currentSegment.getDuration();
		}
	}
	
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
