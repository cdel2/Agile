package optimodlyon.agile.models;
import java.util.ArrayList;
import java.util.HashMap;

public class Map {
	private float height;
	private float width;
	//private ArrayList<Segment> listSegment;
	private ArrayList<Delivery> listDelivery;
	public HashMap<Long, ArrayList<Segment>> graph;
	
	public Map() {

	}
	public Map(HashMap<Long, ArrayList<Segment>> graph) {
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
	
}


