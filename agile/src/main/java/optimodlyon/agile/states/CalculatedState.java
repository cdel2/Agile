package optimodlyon.agile.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.algorithmic.TSP;
import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Deliverer;
import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Path;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.util.Time;

public class CalculatedState extends LoadedDeliveriesState{
	@Override

	public void addDelivery(Long idDelivery) {
		/*
		 * Create a new round between the warehouse and the new point to deliver
		 * The startTime of the round is 00:00.00 hence its EndTime is the duration
		 * Both StartTime and Endtime will be set once the deliverer is found
		 */
		Time t0 = new Time(0,0,0);
		Round newRound = calculateRoundForOneNode(idDelivery,MapManagement.getInstance().getMap(), t0);
		Round newPossibleRound = newRound; //created if we need to create another round 
		Time roundTime = newRound.getEndTime();
		Map<Long,Deliverer> delivererMap = MapManagement.getInstance().getListDeliverer();
		/*
		 * Initialize the minimum finishing Time to 23:59.59 and the idDeliverer to -1
		 */
		Time minTime = new Time(23,59,59); Time tmpTime = new Time(24,00,00); Long keyBestDeliv = (long)-1;
		/*
		 * We look at each deliverer and :
		 * if he has 1 and only 1 round assigned, if its endTime is < minTime, then minTime = endTime
		 * 		and we keep the id of the deliverer
		 * if he has 0 round, minTime = 00:00.00 and we keep the id of the deliverer
		 * if he has more than 1 round, we check if he's started his last round yet, and if not
		 * 		we re-calculate its last round in order to add the new point,
		 * 		then we check if the endTime of this newly created round is < minTime then 
		 * 		minTime = endTime and we keep the id of the deliverer
		 */
		for (Long key : delivererMap.keySet()) {
			int listRoundSize = delivererMap.get(key).getListRound().size(); 
			/*
			 * The deliverer has at least 1 round assigned
			 */
			if(listRoundSize > 0) {
				for(Round round : delivererMap.get(key).getListRound()) {
					/*
					 * The deliverer has only 1 round assigned
					 */
					if(listRoundSize<2) {
						if(round != null) {
							tmpTime = round.getEndTime();
							tmpTime.addTime(roundTime);
							if(tmpTime.isBefore(minTime)) {
								minTime = tmpTime;
								keyBestDeliv = key;
							}
						}
					}
					/*
					 * The deliverer has more than 1 round assigned
					 */
					else {
						// TODO checker si le livreur n'est pas encore parti
						/*
						 * We create a new round from the last round of the deliverer and we check if 
						 * this new round finishes before the current min Time 
						 */
						Round newPossibleRoundTmp = calculateRoundByAddingNodeToExisting(delivererMap.get(key).getListRound().get(listRoundSize-1), MapManagement.getInstance().getMap(),idDelivery);
						if(newPossibleRound.getEndTime().isBefore(minTime)) {
							//TODO créer constructeur de copie de round
							//newPossibleRound = new Round(newPossibleRoundTmp);
							minTime = newPossibleRound.getEndTime();
							keyBestDeliv = key;
						}
					}
				}
			} else {
				/*
				 * If the deliverer has no rounds yet
				 */
				minTime = new Time(0,0,0);
				keyBestDeliv = key;
			}
		}
		Time endOfDay = new Time(18,0,0);
		/*
		 * If we have found a corresponding deliverer and adding a new delivery doesn't make 
		 * the deliverer finish after the end of the working day
		 */
		if(keyBestDeliv != -1 && minTime.isBefore(endOfDay)) {
			/*
			 * If we had to create a new Round, we remove the last round of the chosen deliverer 
			 * and we add the new round to its list
			 * We also create a delivery Point and add it to the MapManagement instance
			 */
			if(minTime == newPossibleRound.getEndTime()) {
				//TODO créer a fonction pour retirer un round d'un livreur
				//MapManagement.getInstance().removeLastRoundFromADeliverer(delivererMap.get(keyBestDeliv));
				if(MapManagement.getInstance().addRoundToADeliverer(delivererMap.get(keyBestDeliv), newPossibleRound)) {
					Intersection i = MapManagement.getInstance().getIntersectionById(idDelivery);
					Delivery newDelivery = new Delivery(i,(float)0);
					MapManagement.getInstance().addDeliveryToListDelivery(newDelivery);
				}
				/*
				 * If we didn't have to create a new round, we simply add the round to the deliverer's list
				 * and we reate a new delivery point that we add to the mapmanagement instance
				 */
			} else {
				if(MapManagement.getInstance().addRoundToADeliverer(delivererMap.get(keyBestDeliv), newRound)) {
					Intersection i = MapManagement.getInstance().getIntersectionById(idDelivery);
					Delivery newDelivery = new Delivery(i,(float)0);
					MapManagement.getInstance().addDeliveryToListDelivery(newDelivery);
				}
			}
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
//		for(Path path : round.getListPath()) {
//			System.out.println("Departure : " + path.getDepartureTime() + " Duration : " + path.getDuration() + 
//					", " + path.getArrival().getDuration() + " Arrival : " + path.getArrival().getTimeArrival());
//		}
		return round;
	}
	
	public Round calculateRoundByAddingNodeToExisting(Round previousRound, CityMap map, Long idNode) {
		List<Long> listIds = new ArrayList<Long>();
		listIds.add(idNode);
		for(Path path : previousRound.getListPath()) {
			for(Segment segment : path.getPath()) {
				if(!listIds.contains(segment.getStart().getId())) {
					listIds.add(segment.getStart().getId());
				}
			}
		}
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(map.getGraph(), listIds);
		Round round = tsp.brutForceTSP(graph, dijkstra, previousRound.getStartTime());
		return round;
	}
}
