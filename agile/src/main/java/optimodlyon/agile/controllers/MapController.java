package optimodlyon.agile.controllers;

import optimodlyon.agile.models.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import optimodlyon.agile.xml.DeserializerXML;

@RestController
public class MapController {
    
    @RequestMapping("/map")
    public Map getMap() {
        Map map = DeserializerXML.deserialize();
        return map;
    }
}