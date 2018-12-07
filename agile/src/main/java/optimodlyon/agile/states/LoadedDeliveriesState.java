package optimodlyon.agile.states;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.algorithmic.Clustering;
import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.algorithmic.TSP;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Deliverer;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.models.Warehouse;
import optimodlyon.agile.util.Time;
import optimodlyon.agile.xml.DeserializerXML;

public class LoadedDeliveriesState extends DefaultState{
	
	@Override
	public void startCalculation(int nb) {	
		System.out.println("calculating...");
		Clustering clustering = new Clustering();
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		
		MapManagement.getInstance().getMap();
		MapManagement.getInstance().initializeListDeliverer(nb);
		List<List<Delivery>> clusters = clustering.dispatchCluster(MapManagement.getInstance().getMap(), nb); 
		
		int i =0;
		List<Round> finalRound = new ArrayList<Round>();
		for(List<Delivery> cluster : clusters) {
			i++;
			List<Long> arrayOfIntersectionIds = Clustering.createIdArray(cluster);
			MapManagement.getInstance().getWarehouse();
			arrayOfIntersectionIds.add(MapManagement.getInstance().getWarehouse().getId());
			//Map<Long, List<Segment>> mapGraph = clustering.reform(map.getGraph());
			Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(MapManagement.getInstance().getMap().getGraph(), arrayOfIntersectionIds);
			Round round = tsp.brutForceTSP(graph, MapManagement.getInstance().getMap(), dijkstra);
			finalRound.add(round);
		}
		
		MapManagement.getInstance().attributeRound(finalRound); //A TESTER SI CA MARCHE!
	}
	

	@Override
	/**
	 * Checks if a deliverer has finished his round
	 * Calculates the shortest path from warehouse to the new point
	 * Chooses the best deliverer depending on its finishing time 
	 */
	public void addDelivery (Long idDelivery) {
		/*
		 * Calculate the shortest path from warehouse to newPoint
		 */
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		List<Long> newDel = new ArrayList<Long>();
		newDel.add(idDelivery);
		CityMap map = MapManagement.getInstance().getMap();
		newDel.add(MapManagement.getInstance().getWarehouse().getId());
		Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(map.getGraph(), newDel);
		Round round = tsp.brutForceTSP(graph, MapManagement.getInstance().getMap(), dijkstra);
		/*
		 *Find the best deliverer 
		 */
		Map<Long,Deliverer> delivererMap = MapManagement.getInstance().getListDeliverer();
		Time minTime = new Time(99,99,99); Time tmpTime; Long keyBestDeliv = (long)-1;
		for (Long key : delivererMap.keySet()) {
			tmpTime = delivererMap.get(key).getListRound().get(0).getEndTime();
			if(tmpTime.isBefore(minTime)) {
				minTime = tmpTime;
				keyBestDeliv = key;
			}
		}
		/*
		 * Add the new round to a deliverer's round list
		 */
		if(keyBestDeliv != -1) {
			
			//round.setStartTime(minTime); round.setEndTime();
			List<Round> newRoundList = delivererMap.get(keyBestDeliv).getListRound();
			newRoundList.add(round);
			delivererMap.get(keyBestDeliv).setListRound(newRoundList);
			MapManagement.getInstance().setListDeliverer(delivererMap);
		}
	}
	
	@Override
	public void loadDeliveries(String file) {
		System.out.println("loading deliveries...");
		List<Delivery> listDelivery = DeserializerXML.deserializeDeliveries(file);
		Warehouse whs = DeserializerXML.deserializeWarehouse(file);
		MapManagement.getInstance().setListDelivery(listDelivery);
		MapManagement.getInstance().setWarehouse(whs);
	}
}
