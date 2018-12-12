package optimodlyon.agile.controller;
import optimodlyon.agile.states.CalculatedState;
import optimodlyon.agile.states.CalculatingState;
import optimodlyon.agile.states.DefaultState;
import optimodlyon.agile.states.LoadedMapState;
import optimodlyon.agile.states.LoadedDeliveriesState;
import optimodlyon.agile.states.State;

public class Controller {
    public State currentState;
    public static int counter;
    
    public Controller() {
        currentState = new DefaultState();
        counter = 0;
    }

    public void initializeGraph(String file) {
        try {
            currentState.loadMap(file);
            currentState = new LoadedMapState();
        } catch(Exception e) {
            System.out.println("Erreur lors de InitializeGraph : " + e);
        }				
    }

    public void getDeliveries(String file) throws Exception{
        currentState.loadDeliveries(file);
        currentState = new LoadedDeliveriesState();	
    }

    public void doAlgorithm(int nb) throws Exception {
        currentState = new CalculatingState();	
        currentState.startCalculation(nb);
        currentState = new CalculatedState();	
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
        System.out.println("in controller");
        boolean result = currentState.stopCalculation();
        System.out.println("je suis"+result);
        currentState = new CalculatedState();
        return result;
    }
    
    public void undo() {
    	try {
	    	counter++;
	    	System.out.println("counter : " + counter);
			currentState.undo(counter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void redo() {
    	try {
	    	counter--;
	    	System.out.println("counter : " + counter);
			currentState.redo(counter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
