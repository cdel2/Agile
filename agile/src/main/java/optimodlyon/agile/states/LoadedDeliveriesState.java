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

public class LoadedDeliveriesState extends DefaultState{
	
	@Override
	public void  startCalculation(int nb) {		
		Clustering clustering = new Clustering();
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		
		CityMap map = CityMap.getInstance();
		ArrayList<ArrayList<Delivery>> clusters = clustering.dispatchCluster(map, nb); 
		
		int i =0;
		List<Round> finalRound = new ArrayList<Round>();
		for(ArrayList<Delivery> cluster : clusters) {
			i++;
			ArrayList<Long> arrayOfIntersectionIds = Clustering.createIdArray(cluster);
			map.getWarehouse();
			arrayOfIntersectionIds.add(map.getWarehouse().getId());
			Map<Long, List<Segment>> mapGraph = clustering.reform(map.getGraph());
			Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(mapGraph, arrayOfIntersectionIds);
			Round round = tsp.brutForceTSP(graph, map, dijkstra);
			finalRound.add(round);
		}
		
		map.setListRounds(finalRound);
	}
}
