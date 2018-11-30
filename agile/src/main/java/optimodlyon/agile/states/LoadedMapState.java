package optimodlyon.agile.states;

import java.util.ArrayList;

import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Warehouse;
import optimodlyon.agile.xml.DeserializerXML;

public class LoadedMapState extends DefaultState{
	CityMap map;
	
	@Override
	public void loadDeliveries(String file) {
		System.out.println("loading deliveries...");
		ArrayList<Delivery> listDelivery = DeserializerXML.deserializeDeliveries(file);
		Warehouse whs = DeserializerXML.deserializeWarehouse(file);
		CityMap.getInstance().setListDelivery(listDelivery);
		CityMap.getInstance().setWarehouse(whs);
	}
}
