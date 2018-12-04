package optimodlyon.agile.models;

import optimodlyon.agile.xml.DeserializerXML;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import optimodlyon.agile.models.*;


public class MapManagement{
	
	private CityMap map = new CityMap();
	private List<Delivery> listDelivery = new ArrayList<Delivery>();
	private Map<Long,Deliverer> listDeliverer = new HashMap<Long,Deliverer>();
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
	
	public Map<Long,Deliverer> getListDeliverer() {
		return listDeliverer;
	}
	
	public void setListDeliverer(Map<Long,Deliverer> listDeliverer) {
		this.listDeliverer = listDeliverer;
	}
	
	public void attributeRound(List<Round> listRound)
	{
		for(Long it : listDeliverer.keySet())
		{
			Iterator<Round> round = listRound.iterator();
			listDeliverer.get(it).getListRound().add(round.next());

		}
	}
	
	public void initializeListDeliverer(int nb)
	{
		listDeliverer.clear();
		Long id = new Random().nextLong();
		Deliverer deliverer = new Deliverer(id);
		listDeliverer.put(id,deliverer);
		for(int i=1; i<nb; i++)
		{
			while(listDeliverer.containsKey(id))
			{
				id = new Random().nextLong();
				
			}
			deliverer = new Deliverer(id);
			listDeliverer.put(id,deliverer);
			
		}
	}
	

}

