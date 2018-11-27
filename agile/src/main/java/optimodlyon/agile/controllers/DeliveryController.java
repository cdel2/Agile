package optimodlyon.agile.controllers;

import optimodlyon.agile.models.CityMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import optimodlyon.agile.xml.DeserializerXML;

@RestController
public class DeliveryController {
	
	 @RequestMapping("/delivery")
	 public CityMap setDelivery() {
		 CityMap map = DeserializerXML.deserializeMap();
	     return map;
	  }

}
