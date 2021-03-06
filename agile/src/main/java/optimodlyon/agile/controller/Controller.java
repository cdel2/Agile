package optimodlyon.agile.controller;
import org.xml.sax.SAXException;
import optimodlyon.agile.states.CalculatedState;
import optimodlyon.agile.states.CalculatingState;
import optimodlyon.agile.states.DefaultState;
import optimodlyon.agile.states.LoadedMapState;
import optimodlyon.agile.states.LoadedDeliveriesState;
import optimodlyon.agile.states.State;

public class Controller {
    private State currentState;
    
    public Controller() {
        currentState = new DefaultState();       
        
    }

    public void initializeGraph(String file) throws Exception {
            currentState.loadMap(file);
            currentState = new LoadedMapState();		
    }

    public void getDeliveries(String file) throws Exception{
        currentState.loadDeliveries(file);
        currentState = new LoadedDeliveriesState();	
    }

    public void doAlgorithm(int nb) throws Exception {
		currentState = new CalculatingState();	
		currentState.startCalculation(nb);
		currentState = new CalculatedState();
                currentState.clearHistory();
    }

    /**
     * method used to add a Delivery and calculate the best path (and best deliverer) 
     * to deliver this point 
     * @param idNewNode
     */
    public void newDelivery(Long idDelivery, int duration) throws Exception {
        currentState.addDelivery(idDelivery, duration);
    }
    
    /**
     * method used to remove a Delivery 
     * @param idDelivery
     * @param calc if the user want to calc the new best path
     */
    public void removeDelivery(Long idDelivery, boolean calc) throws Exception {
    	currentState.removeDelivery(idDelivery, calc);
    }

    public boolean stopCalculation() throws Exception {
        boolean result = currentState.stopCalculation();
        currentState = new CalculatedState();
        return result;
    }
    
    public void undo() throws Exception{
            currentState.undo();
    }
    
    public void redo() throws Exception{
        currentState.redo();
    }
    
    public boolean undoNext() throws Exception{
        return currentState.undoNext();
    }
    
    public boolean redoNext() throws Exception {
        return currentState.redoNext();
    }
}
