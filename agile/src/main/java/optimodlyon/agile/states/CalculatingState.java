package optimodlyon.agile.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.algorithmic.Clustering;
import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.algorithmic.TSP;
import optimodlyon.agile.models.Deliverer;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.models.Warehouse;
import optimodlyon.agile.states.State;
import optimodlyon.agile.util.Pair;
import optimodlyon.agile.util.StatePair;
import optimodlyon.agile.util.Time;
import optimodlyon.agile.xml.DeserializerXML;

public class CalculatingState extends DefaultState {

    @Override
    public void loadDeliveries(String file) {
        System.out.println("loading deliveries...");
        List<Delivery> listDelivery = DeserializerXML.deserializeDeliveries(file);
        Warehouse whs = DeserializerXML.deserializeWarehouse(file);
        MapManagement.getInstance().setListDelivery(listDelivery);
        MapManagement.getInstance().setWarehouse(whs);
    }

    @Override
    public void startCalculation(int nb) throws Exception {	
        System.out.println("calculating...");
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
            //Map<Long, List<Segment>> mapGraph = clustering.reform(map.getGraph());
            Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(MapManagement.getInstance().getMap().getGraph(), arrayOfIntersectionIds);
			Time startTime = MapManagement.getInstance().getWarehouse().getTimeStart();

            Round round = tsp.startTSPMatrix(10000, graph.size(), graph, startTime, dijkstra);
            //Round round = tsp.startTSPMinDistance(10000, graph.size(), graph, startTime, dijkstra);
			System.out.println(round);
            finalRound.add(round);
        }

        MapManagement.getInstance().assignRounds(finalRound); 
        
        System.out.println("before push");	
        List<Pair<List<Delivery>, Map<Long,Deliverer>>> history = MapManagement.getInstance().getHistory();
        history.clear();
        MapManagement.getInstance().pushToHistory();
        System.out.println("after push");
    }
    
    @Override
    public boolean stopCalculation() {
        boolean result;
        System.out.println("in state");
        TSP tsp = new TSP();
        tsp.stopAlgorithm();
        result = true;
        return result;
    }
}
