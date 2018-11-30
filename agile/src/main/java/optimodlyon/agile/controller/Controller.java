package optimodlyon.agile.controller;

import java.util.*;

import optimodlyon.agile.algorithmic.Clustering;
import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.models.Path;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.algorithmic.TSP;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.xml.DeserializerXML;

public class Controller {
	public CityMap map;
	
	public void InitializeGraph(String type) {
		map = DeserializerXML.deserializeMap(type);
		
	}

	public void GetDeliveries(String file) {
		CityMap newMap = DeserializerXML.deserializeDelivery(file,map);
		map = newMap;
	}
	
	public List<Round> doAlgorithm(int nb) {
		Clustering clustering = new Clustering();
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		ArrayList<ArrayList<Delivery>> clusters = clustering.dispatchCluster(map, nb); 
		int i =0;
		List<Round> finalRound = new ArrayList<Round>();
		for(ArrayList<Delivery> cluster : clusters) {
			i++;
			ArrayList<Long> arrayOfIntersectionIds = Clustering.createIdArray(cluster);
			map.getWarehouse();
			arrayOfIntersectionIds.add(map.getWarehouse().getId());
			Map<Long, List<Segment>> mapGraph = clustering.reform(map.graph);
			Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(mapGraph, arrayOfIntersectionIds);
			Round round = tsp.brutForceTSP(graph, map, dijkstra);
			finalRound.add(round);
		}
		//System.out.println(finalRound);
		return finalRound;
	}
	
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

}
