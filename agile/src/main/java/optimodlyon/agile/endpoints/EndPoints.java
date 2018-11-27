package optimodlyon.agile.endpoints;

import optimodlyon.agile.controller.Controller;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Segment;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class EndPoints {
	Controller controller = new Controller();
	
    @GetMapping("/map")
    public CityMap  getMap() {
        controller.InitializeGraph();
        return controller.map;
    }
    
    @GetMapping("/deliveries")
    public CityMap get() {
        controller.GetDeliveries();
        return controller.map;
    }
}