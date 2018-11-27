package optimodlyon.agile.models;

import java.util.ArrayList;


public class Intersection {
	private Long id;
	private float latitude;
	private float longitude;

	public Intersection(Long id, float latitude, float longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	ArrayList<Intersection> findSuccessors(){
		ArrayList<Intersection> listSuccessors = new ArrayList();
		return listSuccessors;
	}

	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	

	
	public static ArrayList<Intersection> FindSuccessorSegments(Intersection start, CityMap map)
	{
		ArrayList<Delivery> deliveries = map.getListDelivery();
		ArrayList<Segment> seg = map.graph.get(start.getId());
		//System.out.println(map.graph.get(start.getId()));
		ArrayList <Intersection> endIntersection = new ArrayList<Intersection>();
		for(Segment s : seg) {
			Intersection e=s.getEnd();
			endIntersection.add(e);
        }
		for(Intersection i : endIntersection) {
        	System.out.println(i);
        }
		return endIntersection;
	}
	
	public String toString() {
		return "Intersection id : " + id + " lat : "+latitude +  " long : "+longitude;
	}
}

