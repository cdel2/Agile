package optimodlyon.agile.endpoints;

import optimodlyon.agile.models.Round;
import optimodlyon.agile.controller.Controller;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Deliverer;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Segment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class EndPoints {
	Controller controller = new Controller();
	
	//AJOUTER LA GESTION DES ERREURS
    @GetMapping("/map/{file}")
    public CityMap  getMap(@PathVariable String file) {
        controller.InitializeGraph(file);
        return MapManagement.getInstance().getMap();
    }
    
    @GetMapping("/deliveries/{file}")
    public List<Delivery> getDeliveries(@PathVariable String file) {
        controller.GetDeliveries(file);
        return MapManagement.getInstance().getListDelivery();
    }
    
    
    @GetMapping("/calc/{nb}")
    public Map<Long,Deliverer> get(@PathVariable int nb) {
    	System.out.println("endpointDebut");
    	controller.doAlgorithm(nb);
    	System.out.println("endpointFin");
        return MapManagement.getInstance().getListDeliverer();
    }
    
//    @GetMapping("/testcalc/{nb}")
//    public List<Round> getRounds(@PathVariable int nb) {
//    	controller.InitializeGraph("petit");
//    	controller.GetDeliveries("dl-petit-6");
//    	controller.doAlgorithm(nb);
//        return CityMap.getInstance().getListRounds();
//    }
}