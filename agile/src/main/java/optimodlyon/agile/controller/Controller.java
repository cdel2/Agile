package optimodlyon.agile.controller;

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
	
	public void doAlgorithm(int nb) throws Exception{		
			currentState.startCalculation(nb);
			
	}
	
	/**
	 * method used to add a Delivery and calculate the best path (and best deliverer) 
	 * to deliver this point 
	 * @param idNewNode
	 */
	public void newDelivery(Long idDelivery) throws Exception {
		currentState.addDelivery(idDelivery);
	}
}
