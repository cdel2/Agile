package optimodlyon.agile.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.algorithmic.Clustering;
import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.algorithmic.TSP;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.models.Warehouse;
import optimodlyon.agile.xml.DeserializerXML;

public class LoadedDeliveriesState extends DefaultState{
	
	@Override
	public void  startCalculation(int nb) {		
		Clustering clustering = new Clustering();
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		
		CityMap map = CityMap.getInstance();
		List<List<Delivery>> clusters = clustering.dispatchCluster(map, nb); 
		
		int i =0;
		List<Round> finalRound = new ArrayList<Round>();
		for(List<Delivery> cluster : clusters) {
			i++;
			List<Long> arrayOfIntersectionIds = Clustering.createIdArray(cluster);
			map.getWarehouse();
			arrayOfIntersectionIds.add(map.getWarehouse().getId());
			//Map<Long, List<Segment>> mapGraph = clustering.reform(map.getGraph());
			Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(map.getGraph(), arrayOfIntersectionIds);
			Round round = tsp.brutForceTSP(graph, map, dijkstra);
			finalRound.add(round);
		}
		
		map.setListRounds(finalRound);
	}
	

	@Override
	/**
	 * Checks if a deliverer has finished his round
	 * Calculates the shortest path from warehouse to the new point
	 * Chooses the best deliverer depending on its finishing time 
	 */
	public void addDelivery (Long newDelivery) {
		/*
		 * Calculate the shortest path from warehouse to newPoint
		 */
		Dijkstra dijkstra = new Dijkstra();
		List<Long> newDel = new ArrayList<Long>();
		newDel.add(newDelivery);
		CityMap map = CityMap.getInstance();
		newDel.add(map.getWarehouse().getId());
		
	}
	
	@Override
	public void loadDeliveries(String file) {
		System.out.println("loading deliveries...");
		List<Delivery> listDelivery = DeserializerXML.deserializeDeliveries(file);
		Warehouse whs = DeserializerXML.deserializeWarehouse(file);
		CityMap.getInstance().setListDelivery(listDelivery);
		CityMap.getInstance().setWarehouse(whs);
	}
}
