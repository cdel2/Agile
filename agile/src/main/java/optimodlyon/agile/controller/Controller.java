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
	
	
	/*
	public List<List<Intersection>> doAlgorithmWithoutClustering(int nb) {
		Clustering clustering = new Clustering();
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		
		ArrayList<Delivery> listDel = map.getListDelivery();
		ArrayList<Long> roundID = new ArrayList<Long>();
		
		//récupération des id de delivery et stockage dans une liste
		for (int i = 0; i < listDel.size(); i++) {
			roundID.add(listDel.get(i).getId());
		}
		
		map.getWarehouse();
		roundID.add(map.getWarehouse().getId());
		Map<Long, List<Segment>> mapGraph = clustering.reform(map.graph);
		
		Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(mapGraph, roundID);
		
		ArrayList<PathLength> finalRounds = new ArrayList<PathLength>();
	
		System.out.println(">>>>>>>" + graph);
		PathLength finalRound = tsp.doTSP(graph, map.getWarehouse().getId());
		
		finalRounds.add(finalRound);
		
		List<List<Intersection>> ret = tsp.makeRounds(finalRounds, map);
		return ret;
	}
*/
}
