package optimodlyon.agile.models;
import java.util.ArrayList;
import java.util.HashMap;

public class CityMap {
	private float height;
	private float width;
	private int nbDeliverers;
	//private int nbDeliveries;
	//private ArrayList<Segment> listSegment;
	private ArrayList<Delivery> listDelivery;
	private Warehouse warehouse;
	public HashMap<Long, ArrayList<Segment>> graph;
	
	public CityMap() {

	}
	public CityMap(HashMap<Long, ArrayList<Segment>> graph) {
		this.graph = graph;
		warehouse = null;
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
}


