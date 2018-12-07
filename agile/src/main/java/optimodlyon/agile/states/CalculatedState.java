package optimodlyon.agile.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.algorithmic.TSP;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Deliverer;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.util.Time;

public class CalculatedState extends DefaultState{
	
	
	@Override
	/**
	 * Checks if a deliverer has finished his round
	 * Calculates the shortest path from warehouse to the new point
	 * Chooses the best deliverer depending on its finishing time 
	 */
	public void addDelivery (Long idDelivery) {
		Deliverer bestDeliverer = findBestDeliverer(MapManagement.getInstance().getListDeliverer());
		/*
		 * Calculate the shortest path from warehouse to newPoint
		 */
		Round newRound = calculateRoundForOneNode(idDelivery,MapManagement.getInstance().getMap(), bestDeliverer.getListRound().get(0).getEndTime());
		Time endOfDay = new Time(18,00,00);
		if(newRound != null && bestDeliverer != null) {
			if(newRound.getEndTime().isBefore(endOfDay)) {
				MapManagement.getInstance().addRoundToADeliverer(bestDeliverer, newRound);
			}
		}
	}
	
	/**
	 * Returns the deliverer that ends its first round the first
	 * @param delivererMap
	 * @return
	 */
	public Deliverer findBestDeliverer(Map<Long,Deliverer> delivererMap) {
		/*
		 * Initialize the minimum finishing Time to 99:99.99 and the idDeliverer to -1
		 */
		Time minTime = new Time(99,99,99); Time tmpTime; Long keyBestDeliv = (long)-1;
		/*
		 * We look at the first round of each deliverer and compare its finishing time 
		 * to the current minimum finishing time
		 */
		for (Long key : delivererMap.keySet()) {
			tmpTime = delivererMap.get(key).getListRound().get(0).getEndTime();
			if(tmpTime.isBefore(minTime)) {
				minTime = tmpTime;
				keyBestDeliv = key;
			}
		}
		/*
		 * If we have found a corresponding deliverer, return the deliverer Object
		 */
		if(keyBestDeliv != -1) {
			return delivererMap.get(keyBestDeliv);
		} else {
			return null;
		}
	}
	
	public Round calculateRoundForOneNode(Long idIntersection, CityMap map, Time startTime ) {
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		List<Long> newDel = new ArrayList<Long>();
		newDel.add(idIntersection);
		newDel.add(MapManagement.getInstance().getWarehouse().getId());
		Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(map.getGraph(), newDel);
		Round round = tsp.brutForceTSP(graph, dijkstra, startTime);
		return round;
	}
}
