package optimodlyon.agile.controller;

import java.util.*;

import optimodlyon.agile.algorithmic.Clustering;
import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.models.Path;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.algorithmic.TSP;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.states.DefaultState;
import optimodlyon.agile.states.LoadedMapState;
import optimodlyon.agile.states.LoadedDeliveriesState;
import optimodlyon.agile.states.State;
import optimodlyon.agile.xml.DeserializerXML;

public class Controller {
	public State currentState;
	
	public Controller() {
		currentState = new DefaultState();
	}
	
	public void InitializeGraph(String file) {
		try {
			currentState.loadMap(file);
			System.out.println("load");
			currentState = new LoadedMapState();
		} catch(Exception e) {
			System.out.println("Erreur lors de InitializeGraph : " + e);
		}
				
	}

	public void GetDeliveries(String file) {
		try {
			currentState.loadDeliveries(file);
			currentState = new LoadedDeliveriesState();
		} catch(Exception e) {
			System.out.println("Erreur lors de GetDeliveries : " + e);
		}		
	}
	
	public void doAlgorithm(int nb) {
		try {
			currentState.startCalculation(nb);
			//currentState = new CalculatedSate();
		} catch(Exception e) {
			System.out.println("Erreur lors de DoAlgorithm : " + e);
		}	
	}
	
	/**
	 * method used to add a Delivery and calculate the best path (and best deliverer) 
	 * to deliver this point 
	 * @param idNewNode
	 */
	public void NewDelivery(Long idNewNode) {
		try {
			
		} catch(Exception e) {
			System.out.println("Error adding a delivery : " + e);
		}
	}
}
