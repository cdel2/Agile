package optimodlyon.agile.controller;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import optimodlyon.agile.states.CalculatedState;
import optimodlyon.agile.states.CalculatingState;
import optimodlyon.agile.states.DefaultState;
import optimodlyon.agile.states.LoadedMapState;
import optimodlyon.agile.states.LoadedDeliveriesState;
import optimodlyon.agile.states.State;

public class Controller {
	public State currentState;
	
	public Controller() {
            currentState = new DefaultState();
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

            currentState.startCalculation(nb);
            currentState = new CalculatedState();	
	}
	
	/**
	 * method used to add a Delivery and calculate the best path (and best deliverer) 
	 * to deliver this point 
	 * @param idNewNode
	 */
	public void newDelivery(Long idDelivery) throws Exception {
        currentState.addDelivery(idDelivery);
	}
	
	/**
	 * method used to remove a Delivery 
	 * @param idDelivery
	 */
	public void rmvDelivery(Long idDelivery) throws Exception {
        currentState.rmvDelivery(idDelivery);
	}
}
