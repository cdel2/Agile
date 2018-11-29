package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.List;


public class Path {
	private float duration;
	private ArrayList<Segment> listSegment;
	
	public Path() {
		duration = 0;
		listSegment.clear();
	}
	
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
			System.out.println(listSegment);
			System.out.println(currentSegment);
			duration+=currentSegment.getDuration();
		}
	}
	
	
	public Intersection getStart()
	{
		Intersection start = this.listSegment.get(0).getStart();
		return start;
	}

	/**
	 * @return the duration
	 */
	public float getDuration() {
		return duration;
	}
	
	public ArrayList<Segment> getPath() {
		return this.listSegment;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(float duration) {
		this.duration = duration;
	}


	public Intersection getEnd()
	{
		Intersection end = (listSegment.get(listSegment.size()-1)).getStart();
		return end;
	}
	
	public String toString()
	{
		String path = "start : " + this.getStart().toString() + " end : " + this.getEnd().toString() + "\n" + listSegment.toString();
		return path;
	}
	
	
	
}
