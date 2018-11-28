package optimodlyon.agile.controller;
import java.util.ArrayList;

import java.util.HashMap;

import optimodlyon.agile.algorithmic.Clustering;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.xml.DeserializerXML;

public class Controller {
	public CityMap map;
	
	public void InitializeGraph(String type) {
		map = DeserializerXML.deserializeMap(type);
		
	}

	public void GetDeliveries(String file) {
		CityMap newMap = DeserializerXML.deserializeDelivery(file,map);
		map = newMap;
	}
	
	public void doAlgorithm(int nb) {
		Clustering clustering = new Clustering();
		ArrayList<ArrayList<Delivery>> rounds = clustering.dispatchCluster(map, nb);
		for(ArrayList<Delivery> round : rounds) {
			
		}
	}

}
