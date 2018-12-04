package optimodlyon.agile.models;

import optimodlyon.agile.xml.DeserializerXML;

import java.util.ArrayList;
import java.util.Date;

import optimodlyon.agile.models.*;


public class MapManagement{
	
	private CityMap map;
	private ArrayList<Delivery> listDelivery;
	private ArrayList<Deliverer> listDeliverer;
	
	private static MapManagement instance = null;
	
	private MapManagement() {}
	
	public static MapManagement getInstance() {
		if (instance == null) {
			instance = new MapManagement();
		}
		return instance;
	}

	public CityMap getMap() {
		return map;
	}

	public void setMap(CityMap map) {
		this.map = map;
	}
}

