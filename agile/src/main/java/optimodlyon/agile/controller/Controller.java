package optimodlyon.agile.controller;
import java.nio.file.Path;

import java.util.*;

import optimodlyon.agile.algorithmic.Clustering;
import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.algorithmic.PathLength;
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
	
	public ArrayList<PathLength> doAlgorithm(int nb) {
		Clustering clustering = new Clustering();
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		ArrayList<ArrayList<Delivery>> rounds = clustering.dispatchCluster(map, nb); 
		int i =0;
		ArrayList<PathLength> finalRounds = new ArrayList<PathLength>();
		for(ArrayList<Delivery> round : rounds) {
			i++;
			ArrayList<Long> roundID = Clustering.createIdArray(round);
			map.getWarehouse();
			roundID.add(map.getWarehouse().getId());
			Map<Long, List<Segment>> mapGraph = clustering.reform(map.graph);
			Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(mapGraph, roundID);
			PathLength finalRound = tsp.doTSP(graph, (long)1);
			System.out.println("round" + i + finalRound);
			finalRounds.add(finalRound);
		}
		return finalRounds;
	}

}
