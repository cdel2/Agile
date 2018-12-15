package optimodlyon.agile.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.algorithmic.Clustering;
import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.algorithmic.TSP;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.models.Warehouse;
import optimodlyon.agile.util.Time;
import optimodlyon.agile.xml.DeserializerXML;

public class LoadedDeliveriesState extends DefaultState{
	
	@Override
	public void startCalculation(int nb) throws Exception {	
		Clustering clustering = new Clustering();
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		MapManagement.getInstance().getMap();
		MapManagement.getInstance().initializeListDeliverer(nb);
		List<List<Delivery>> clusters = clustering.dispatchCluster(MapManagement.getInstance().getMap(), nb); 
		
		List<Round> finalRound = new ArrayList<Round>();
		for(List<Delivery> cluster : clusters) {
			List<Long> arrayOfIntersectionIds = Clustering.createIdArray(cluster);
			MapManagement.getInstance().getWarehouse();
			arrayOfIntersectionIds.add(MapManagement.getInstance().getWarehouse().getId());
			Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(MapManagement.getInstance().getMap().getGraph(), arrayOfIntersectionIds);
			Time startTime=new Time("8:00:00");
			Round round = tsp.startTSPMatrix(10000, graph.size(), graph, startTime, dijkstra);
			finalRound.add(round);
		}
		
		MapManagement.getInstance().assignRounds(finalRound); 
	}
	
	
	@Override
	public void loadDeliveries(String file) throws Exception {
		List<Delivery> listDelivery = DeserializerXML.deserializeDeliveries(file);
		Warehouse whs = DeserializerXML.deserializeWarehouse(file);
		MapManagement.getInstance().setListDelivery(listDelivery);
		MapManagement.getInstance().setWarehouse(whs);
	}
}
