package optimodlyon.agile.models;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;


@Lazy(true)
public class CityMap {
	private float height;
	private float width;
	private int nbDeliverers;
	private ArrayList<Delivery> listDelivery = new ArrayList<Delivery>();
	private Warehouse warehouse;
	private HashMap<Long, ArrayList<Segment>> graph = new HashMap<Long, ArrayList<Segment>> ();
	private List<Round> listRounds;
	
	private static CityMap instance = null;
	
	public CityMap() {}
	
	
	public void setGraph(HashMap<Long, ArrayList<Segment>> graph) {
		this.graph = graph;
	}
	
	

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @return the listDelivery
	 */
	public ArrayList<Delivery> getListDelivery() {
		return listDelivery;
	}

	/**
	 * @param listDelivery the listDelivery to set
	 */
	public void setListDelivery(ArrayList<Delivery> listDelivery) {
		this.listDelivery = listDelivery;
	}

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	/**
	 * @return the nbDeliverers
	 */
	public int getNbDeliverers() {
		return nbDeliverers;
	}
	/**
	 * @return the nbDeliveries
	 */
	/*public int getNbDeliveries() {
		nbDeliveries=listDelivery.size();
		return nbDeliveries;
	}*/

	/**
	 * @param nbDeliverers the nbDeliverers to set
	 */
	public void setNbDeliverers(int nbDeliverers) {
		this.nbDeliverers = nbDeliverers;
	}
	
	/**
	 * @param nbDeliveries the nbDeliveries to set
	 */
	/*public void setNbDeliveries(int nbDeliveries) {
		this.nbDeliveries = nbDeliveries;
	}*/
	
	public Warehouse getWarehouse() {
		return warehouse;
	}
	
	public void setWarehouse(Warehouse wh) {
		this.warehouse = wh;
	}
	
	/**
	 * Finds a segment knowing his origin and his destination
	 * 
	 * @param origin
	 * @param destination
	 */
	public Segment getSegmentFromGraph(Long origin, Long destination) {
		ArrayList<Segment> segments = graph.get(origin);
		Segment finalSegment;
		for (Segment segment : segments) {
			if((long)segment.getEnd().getId()==(long)destination) {
				//System.out.println(segment);
				return segment;
			}
		}
		System.out.println("pas de segment trouvé");
		return null;
	}

	public HashMap<Long, ArrayList<Segment>> getGraph() {
		return graph;
	}

	public void setListRounds(List<Round> finalRound) {
		listRounds = finalRound;		
	}

	public List<Round> getListRounds() {
		return listRounds;
	}
}


