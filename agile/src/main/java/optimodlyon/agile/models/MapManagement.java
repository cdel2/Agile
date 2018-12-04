package optimodlyon.agile.models;

import optimodlyon.agile.xml.DeserializerXML;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import optimodlyon.agile.models.*;


public class MapManagement{
	
	private CityMap map;
	private List<Delivery> listDelivery;
	private List<Deliverer> listDeliverer;
	private Warehouse warehouse;
	
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

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public void setListDelivery(List<Delivery> listDelivery) {
		this.listDelivery = listDelivery;
	}
	
	public List<Delivery> getListDelivery() {
		return listDelivery;
	}
	
	public List<Deliverer> getListDeliverer() {
		return listDeliverer;
	}
	
	public void setListDeliverer(List<Deliverer> listDeliverer) {
		this.listDeliverer = listDeliverer;
	}
	
	public void attributeRound(List<Round> listRound)
	{
		for(Deliverer deliverer : listDeliverer)
		{
			Iterator<Round> round = listRound.iterator();
			deliverer.getListRound().add(round.next());

		}
	}
}

