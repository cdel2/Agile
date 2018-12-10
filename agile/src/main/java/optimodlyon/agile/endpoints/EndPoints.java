package optimodlyon.agile.endpoints;

import optimodlyon.agile.controller.Controller;
import optimodlyon.agile.exceptions.UnprocessableEntityException;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Deliverer;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Warehouse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class EndPoints {
    Controller controller = new Controller();
	
    @GetMapping("/map/{file}")
    public CityMap  getMap(@PathVariable String file) {
        controller.initializeGraph(file);
        return MapManagement.getInstance().getMap();
    }
    
    @GetMapping("/deliveries/{file}")
    public List<Delivery> getDeliveries(@PathVariable String file) {
    	try {
            controller.getDeliveries(file);    		
    	} catch (Exception e)
    	{
            throw new UnprocessableEntityException("Le fichier du plan de la ville n'a pas été chargé.");
    	}
        return MapManagement.getInstance().getListDelivery();
    }
    
    @GetMapping("/warehouse")
    public Warehouse getWarehouse() {
        return MapManagement.getInstance().getWarehouse();
    }
    
    @GetMapping("/add/delivery/{idDelivery}")
    public Map<Long,Deliverer> addDelivery(@PathVariable Long idDelivery) {
    	try {
            controller.newDelivery(idDelivery);   		
    	} catch (Exception e)
    	{
            throw new UnprocessableEntityException("Certains fichiers n'ont pas été chargés ou le système est en train de calculer un itinéraire.");
    	}
    	return MapManagement.getInstance().getListDeliverer();
    }
    
    @GetMapping("/rmv/delivery/{idDelivery}")
    public Map<Long,Deliverer> rmvDelivery(@PathVariable Long idDelivery) {
    	try {
            controller.rmvDelivery(idDelivery);   		
    	} catch (Exception e)
    	{
            //throw new UnprocessableEntityException("Certains fichiers n'ont pas été chargés ou le système est en train de calculer un itinéraire.");
    	}
    	System.out.println(MapManagement.getInstance().getListDeliverer());
    	return MapManagement.getInstance().getListDeliverer();
    }
    
    @GetMapping("/deliveries")
    public List<Delivery> getDeliveries() {
    	System.out.println("service getDeliveries");
    	return MapManagement.getInstance().getListDelivery();
    }
    
    @GetMapping("/deliverers/deliveries")
    public Map<Long, Deliverer> getDeliverersDeliveries() {
    	System.out.println("service getDeliverersDeliveries");
    	return MapManagement.getInstance().getListDeliverer();
    }
    
    @GetMapping("/calc/{nb}")
    public Map<Long,Deliverer> get(@PathVariable int nb) {
    	try {
            System.out.println("endpointDebut");
            controller.doAlgorithm(nb);
            System.out.println("endpointFin");
    	} catch (Exception e) {
            throw new UnprocessableEntityException("Le fichier du plan de la ville et/ou les livraisons n'ont pas été chargés.");
    	}
        return MapManagement.getInstance().getListDeliverer();
    } 
}