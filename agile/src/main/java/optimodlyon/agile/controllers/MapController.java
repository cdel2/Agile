package optimodlyon.agile.controllers;

import optimodlyon.agile.models.CityMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import optimodlyon.agile.xml.DeserializerXML;

@RestController
public class MapController {
    
    @RequestMapping("/map")
    public CityMap getMap() {
        CityMap map = DeserializerXML.deserialize();
        return map;
    }
}