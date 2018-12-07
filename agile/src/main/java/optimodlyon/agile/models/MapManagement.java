package optimodlyon.agile.models;

import optimodlyon.agile.xml.DeserializerXML;

import java.util.ArrayList;
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
		int i =0;
		for(Long it : listDeliverer.keySet())
		{
			
			listDeliverer.get(it).getListRound().add(listRound.get(i));
			i++;
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
	
	public static void main(String[] args) {
		Intersection origin = new Intersection((long)1, (float)-50, (float)50);
		System.out.println(origin);
		/*Map<Long,String> listDeliverer = new HashMap<Long,String>();
		listDeliverer.put((long) 1, "a");
		listDeliverer.put((long) 2, "b");
		listDeliverer.put((long) 1, "c");
		List<String> listRound = new ArrayList<String>();
		listRound.add("aa");
		listRound.add("bb");
		listRound.add("cc");
		int i = 0;
		for(Long it : listDeliverer.keySet())
		{
			System.out.println("round : " +listRound.get(i));	
			System.out.println("deli : " +it);	
			i++;
		}*/
	}
	

}

