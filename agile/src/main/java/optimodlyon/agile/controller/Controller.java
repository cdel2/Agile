package optimodlyon.agile.controller;
import java.util.ArrayList;
import java.util.HashMap;

import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.xml.DeserializerXML;

public class Controller {
	public CityMap map;
	
	public void InitializeGraph() {
		map = DeserializerXML.deserializeMap();
		
	}

	public void GetDeliveries() {
		
	}

}
