package optimodlyon.agile.states;

import optimodlyon.agile.models.*;

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
		MapManagement.getInstance().getMap().setListDelivery(listDelivery);
		MapManagement.getInstance().getMap().setWarehouse(whs);
	}
}
