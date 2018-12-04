package optimodlyon.agile.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.xml.DeserializerXML;

public class DefaultState implements State {

	
	public void loadMap(String file) {
		Map<Long, List<Segment>> graph = DeserializerXML.deserializeMap(file);
		CityMap.getInstance().setGraph(graph);
	}
	
	public void loadDeliveries(String file) throws Exception {
		throw new Exception("Impossible de charger des livraisons");
	}
	
	public void startCalculation(int nb) throws Exception{
		throw new Exception("Impossible de commencer le calcul");
	}
	
	public void addDelivery(Long newDelivery)throws Exception {
		throw new Exception("Impossible d'ajouter une livraison");
	}
}