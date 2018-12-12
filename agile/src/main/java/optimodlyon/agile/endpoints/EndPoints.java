package optimodlyon.agile.endpoints;

import optimodlyon.agile.controller.Controller;
import optimodlyon.agile.exceptions.UnprocessableEntityException;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Deliverer;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
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
    
    @GetMapping("/deliveries")
    public List<Delivery> getLoadedDeliveries() {
    	try {
            return MapManagement.getInstance().getListDelivery();
    	} catch (Exception e)
    	{
            throw new UnprocessableEntityException("Le fichier du plan de la ville n'a pas été chargé.");
    	}
    }
    
    @GetMapping("/warehouse")
    public Warehouse getWarehouse() {
        return MapManagement.getInstance().getWarehouse();
    }
    
    @GetMapping("/delivery/add/{idDelivery}/{duration}")
    public Map<Long,Deliverer> addDelivery(@PathVariable Long idDelivery, @PathVariable int duration) {
    	try {
    		System.out.println(duration);
            controller.newDelivery(idDelivery, duration);   		
    	} catch (Exception e)
    	{
            //throw new UnprocessableEntityException("Certains fichiers n'ont pas été chargés ou le système est en train de calculer un itinéraire.");
    	}
    	return MapManagement.getInstance().getListDeliverer();
    }
    
    @GetMapping("/delivery/rmv/{idDelivery}")
    public Map<Long,Deliverer> rmvDelivery(@PathVariable Long idDelivery) {
    	try {
            controller.rmvDelivery(idDelivery);
    	} catch (Exception e)
    	{
            //throw new UnprocessableEntityException("Certains fichiers n'ont pas été chargés ou le système est en train de calculer un itinéraire.");
    	}
    	//System.out.println(MapManagement.getInstance().getListDeliverer());
    	return MapManagement.getInstance().getListDeliverer();
    }
    
    @GetMapping("/delivery/rmv/{idDelivery}/{calc}")
    public Map<Long,Deliverer> removeDelivery(@PathVariable Long idDelivery, @PathVariable boolean calc) {
    	try {
            controller.removeDelivery(idDelivery, calc);
    	} catch (Exception e)
    	{
            //throw new UnprocessableEntityException("Certains fichiers n'ont pas été chargés ou le système est en train de calculer un itinéraire.");
    	}
    	//System.out.println(MapManagement.getInstance().getListDeliverer());
    	return MapManagement.getInstance().getListDeliverer();
    }
    
    @GetMapping("/calculation/start/{nb}")
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
    
    @GetMapping("/calculation/stop")
    public boolean stopCalculation() {

            System.out.println("trying to stop calculation");
            try {
				return controller.stopCalculation();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
    } 
    
    
    @GetMapping("/undo")
    public Map<Long,Deliverer> undo() {

            System.out.println("trying to undo");
            controller.undo();
            //Map<Long,Deliverer> result = new HashMap<Long,Deliverer>(MapManagement.getInstance().getListDeliverer());
            //LA MAP RENVOYEE NE CHANGE PAS :'( POURTANT CA MACHE AVEC LIST DELIVERY
            return MapManagement.getInstance().getListDeliverer() ;
    }
    /*
    @GetMapping("/undo")
    public List<Delivery> undo() {

            System.out.println("trying to undo");
            controller.undo();
            //Map<Long,Deliverer> result = new HashMap<Long,Deliverer>(MapManagement.getInstance().getListDeliverer());
            //LA MAP RENVOYEE NE CHANGE PAS :'( POURTANT CA MACHE AVEC LIST DELIVERY
            return MapManagement.getInstance().getListDelivery() ;
    } */
    
    
}
