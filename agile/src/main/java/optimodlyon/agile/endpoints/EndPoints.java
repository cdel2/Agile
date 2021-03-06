package optimodlyon.agile.endpoints;

import java.io.FileNotFoundException;
import optimodlyon.agile.controller.Controller;
import optimodlyon.agile.exceptions.DijkstraException;
import optimodlyon.agile.exceptions.FunctionalException;
import optimodlyon.agile.exceptions.UndoRedoException;
import optimodlyon.agile.exceptions.UnprocessableEntityException;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Deliverer;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Warehouse;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.xml.sax.SAXParseException;


@CrossOrigin
@RestController
public class EndPoints {
    Controller controller = new Controller();
	
    @GetMapping("/map/{file}")
    public CityMap  getMap(@PathVariable String file) throws Exception {
        try {
            controller.initializeGraph(file);		
    	} catch (FileNotFoundException | SAXParseException e)
    	{
            throw e;
    	} 
        return MapManagement.getInstance().getMap();
    }
    
    @GetMapping("/deliveries/{file}")
    public List<Delivery> getDeliveries(@PathVariable String file) throws Exception {
    	try {
            controller.getDeliveries(file);    		
    	} catch (FileNotFoundException | SAXParseException e)
    	{
            throw e;
    	} catch (Exception e) {
            throw new UnprocessableEntityException("Le fichier du plan de la ville n'a pas été chargé.");
        }
        return MapManagement.getInstance().getListDelivery();
    }

    @GetMapping("/warehouse")
    public Warehouse getWarehouse() {
        if(MapManagement.getInstance().getWarehouse()!= null) {
            return MapManagement.getInstance().getWarehouse();
    	} else {
            throw new UnprocessableEntityException("Le fichier du plan de la ville n'a pas été chargé.");
    	}
    }
    
        
    @GetMapping("/calculation/start/{nb}")
    public Map<Long,Deliverer> get(@PathVariable int nb) throws Exception {
        try {
                controller.doAlgorithm(nb);
        } catch (FunctionalException e) {
                throw e;
        } catch (Exception e) {
                 throw new UnprocessableEntityException("Le fichier du plan de la ville et/ou les livraisons n'ont pas été chargés.");
        }
        return MapManagement.getInstance().getListDeliverer();
    } 
    
    @GetMapping("/calculation/stop")
    public boolean stopCalculation() throws Exception {
            try {
                    return controller.stopCalculation();
            } catch (Exception e) {
                    throw e;
            }
    } 
    
    @GetMapping("/delivery/add/{idDelivery}/{duration}")
    public Map<Long,Deliverer> addDelivery(@PathVariable Long idDelivery, @PathVariable int duration) throws Exception {
    	try {
            controller.newDelivery(idDelivery, duration);   		
    	} catch (DijkstraException | FunctionalException | UnprocessableEntityException e)
    	{
    		throw e; 
    	} 
        
    	return MapManagement.getInstance().getListDeliverer();
    }
    
    @GetMapping("/delivery/rmv/{idDelivery}/{calc}")
    public Map<Long,Deliverer> removeDelivery(@PathVariable Long idDelivery, @PathVariable boolean calc) {
    	try {
            controller.removeDelivery(idDelivery, calc);
    	} catch (Exception e)
    	{
            throw new UnprocessableEntityException("Certains fichiers n'ont pas été chargés ou le système est en train de calculer un itinéraire.");
    	}
    	return MapManagement.getInstance().getListDeliverer();
    }
    
    @GetMapping("/undo")
    public Map<Long,Deliverer> undo() throws Exception {
        try {
			controller.undo();
		} catch (UndoRedoException | UnprocessableEntityException e) {
			throw e;
                }
            
        return MapManagement.getInstance().getListDeliverer() ;
    }
    
    @GetMapping("/redo")
    public Map<Long,Deliverer> redo() throws Exception {
            try {
                    controller.redo();
            } catch (UndoRedoException | UnprocessableEntityException e) {
                    throw e;
            }
            return MapManagement.getInstance().getListDeliverer() ;
    }
    
    @GetMapping("/undo/next")
    public boolean undoNext() throws Exception {
        return controller.undoNext();
    }
    
    @GetMapping("/redo/next")
    public boolean redoNext() throws Exception {
        return controller.redoNext();
    }
}
