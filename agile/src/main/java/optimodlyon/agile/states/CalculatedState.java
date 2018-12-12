package optimodlyon.agile.states;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.web.filter.OncePerRequestFilter;

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
import optimodlyon.agile.util.Pair;
import optimodlyon.agile.util.StatePair;
import optimodlyon.agile.util.Time;

public class CalculatedState extends LoadedDeliveriesState{
	@Override
	public void addDelivery(Long idDelivery, int duration) {
		/*
		 * Create a new round between the warehouse and the new point to deliver
		 * The startTime of the round is 00:00.00 hence its EndTime is the duration
		 * Both StartTime and Endtime will be set 	 the deliverer is found
		 */
		Time t0 = new Time(0,0,0); Time endOfDay = new Time(18,0,0);
		System.out.println("Calculating new round from warehouse (single point)");
		createDelivery(idDelivery, duration);
		Round newRound = calculateRoundForOneNode(idDelivery,MapManagement.getInstance().getMap(), t0);
		Round newPossibleRound = newRound; //created if we need to create another round 
		Time roundTime = newRound.getEndTime();
		System.out.println("Time of the new round : " + roundTime.toString());
		Map<Long,Deliverer> delivererMap = MapManagement.getInstance().getListDeliverer();
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
		System.out.println("Looking for the best deliverer ....");
		for (Long key : delivererMap.keySet()) {
			System.out.println("Deliverer n° "+ key);
			int listRoundSize = delivererMap.get(key).getListRound().size(); 
			System.out.println("Its listROund size : " + listRoundSize);
			/*
			 * The deliverer has at least 1 round assigned
			 */
			if(listRoundSize > 0) {
				System.out.println("Hence we analyse its rounds...");
				Round round = delivererMap.get(key).getListRound().get(listRoundSize-1);
				/*
				 * The deliverer has only 1 round assigned
				 */
				if(listRoundSize<2) {
					System.out.print("He has only one round...");
					if(round != null) {
						tmpTime = new Time(round.getEndTime().toString());
						System.out.println("Which finishes at : "+tmpTime.toString());
						tmpTime.addTime(roundTime);
						System.out.println("His new finishing time is then : "+tmpTime.toString());
						if(tmpTime.isBefore(minTime)) {
							minTime = tmpTime;
							keyBestDeliv = key;
							System.out.println("The minTime is : " + minTime.toString());
						}
					}
				}
				/*
				 * The deliverer has more than 1 round assigned
				 */
				else {
					System.out.println("He has more than 1 round...");
					// check if the deliverer is not gone doing its additionnal round yet
					//Time currentTime = getCurrentTimeUsingCalendar();
					Time currentTime = new Time(8,0,0); // we simulate a current time at 8:00.00
					if(currentTime.isBefore(delivererMap.get(key).getListRound().get(listRoundSize-1).getStartTime())) {
						System.out.println("And he's not gone to do his last round yet");
						/*
						 * We create a new round from the last round of the deliverer and we check if 
						 * this new round finishes before the current min Time 
						 */
						Round newPossibleRoundTmp = calculateRoundByAddingNodeToExisting(delivererMap.get(key).getListRound().get(listRoundSize-1), MapManagement.getInstance().getMap(),idDelivery);
						System.out.println("The modification of his last round finished at : " + newPossibleRoundTmp.getEndTime().toString());
						if(newPossibleRoundTmp.getEndTime().isBefore(minTime)) {
							System.out.println("Hence we modify the minTime");
							newPossibleRound = new Round(newPossibleRoundTmp);
							minTime = newPossibleRound.getEndTime();
							keyBestDeliv = key;
						}
					}
				}
			} else {
				/*
				 * The deliverer has no rounds yet
				 */
				System.out.println("The deliverer has no round yet");
				minTime = new Time(8,0,0);
				minTime.addTime(roundTime);
				keyBestDeliv = key;
			}
		}
		/*
		 * If we have found a corresponding deliverer and adding a new delivery doesn't make 
		 * the deliverer finish after the end of the working day
		 */
		if(keyBestDeliv != -1 && minTime.isBefore(endOfDay)) {
			Deliverer bestDeliv = MapManagement.getInstance().getListDeliverer().get(keyBestDeliv);
			System.out.println("We found a deliverer and we finish before 18h");
			/*
			 * If we had to create a new Round, we remove the last round of the chosen deliverer 
			 * and we add the new round to its list
			 * We also create a delivery Point and add it to the MapManagement instance
			 */
			if(minTime == newPossibleRound.getEndTime()) {
				newRound = newPossibleRound;
				MapManagement.getInstance().removeLastRoundFromADeliverer(delivererMap.get(keyBestDeliv));
			} else {
				Time tstart = new Time(8,0,0);
				if(bestDeliv.getListRound().size()>0) {
					tstart = bestDeliv.getListRound().get(bestDeliv.getListRound().size()-1).getEndTime();
				}
				Time tend = new Time(minTime.toString());
				newRound.setStartTime(tstart);
				System.out.println("   " + tstart.toString() + " // " + tend.toString());
				newRound.setEndTime(tend);
			}
			if(!MapManagement.getInstance().addRoundToADeliverer(delivererMap.get(keyBestDeliv), newRound)) {
				rmvDelivery(idDelivery);
			}
			System.out.println("Delivery " + idDelivery + " added to deliverer " + keyBestDeliv );
		} else {
			System.out.println("We didn't find a deliverer or we don't finish before 18h");
		}
        MapManagement.getInstance().pushToHistory();
	}
	
	/*
	 * From here we start all methods to remove a delivery from our deliveries
	 */
	public void rmvDelivery(Long idDelivery) {
		Delivery toRemove = MapManagement.getInstance().getDeliveryById(idDelivery);
		System.out.println(toRemove);
        if(MapManagement.getInstance().getListDelivery().contains(toRemove)) {
	    	Iterator it = MapManagement.getInstance().getListDeliverer().entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry <Long, Deliverer> pair = (Map.Entry) it.next();
	            List<Round> rounds = pair.getValue().getListRound();
	            int i=0;
	            outerloop:
	            for (Round round : rounds) {
	            	List<Path> newListPath = new ArrayList();
	            	for(Path path : round.getListPath()) {
	            		if((long)path.getArrival().getId()==(long)toRemove.getId()) {
	            			if(round.getListPath().size()==2) {
	            				System.out.println("Je remove la boloss");
	            				rounds.remove(round);
	            			}
	            			else {
	            				System.out.println("wola c'est la merde0");
	            				System.out.println(i);
		            			Round newRound = calculateRoundByRemovingNodeToExisting(pair.getValue().getListRound().get(i), MapManagement.getInstance().getMap(), path.getArrival().getId());
	            				System.out.println("wola c'est la merde1");
	            				System.out.println(newRound);
		            			pair.getValue().changeRound(i, newRound);
	            				System.out.println("wola c'est la merde2");
	            			}
	            			pair.getValue().updateRounds(i);
	            			//TODO : gerer currentTime, si le temps actuel>temps de fin de livraison du dernier round.
	            			break outerloop;
	            		}
	            	}
	            	i++;
	            }
	            //it.remove(); // avoids a ConcurrentModificationException
	        }
            MapManagement.getInstance().getListDelivery().remove(toRemove);
            MapManagement.getInstance().pushToHistory();
        }
	}
	
	public void removeDeliveryAndCalc(Long idDelivery) {
		Delivery toRemove = MapManagement.getInstance().getDeliveryById(idDelivery);
        if(MapManagement.getInstance().getListDelivery().contains(toRemove)) {
	    	Iterator it = MapManagement.getInstance().getListDeliverer().entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry <Long, Deliverer> pair = (Map.Entry) it.next();
	            List<Round> rounds = pair.getValue().getListRound();
	            int i=0;
	            outerloop:
	            for (Round round : rounds) {
	            	List<Path> newListPath = new ArrayList();
	            	for(Path path : round.getListPath()) {
	            		if((long)path.getArrival().getId()==(long)toRemove.getId()) {
	            			if(round.getListPath().size()==2) {
	            				rounds.remove(round);
	            			}
	            			else {
		            			Round newRound = calculateRoundByRemovingNodeToExisting(pair.getValue().getListRound().get(i), MapManagement.getInstance().getMap(), path.getArrival().getId());
		            			pair.getValue().changeRound(i, newRound);
	            			}
	            			pair.getValue().updateRounds(i);
	            			//TODO : gerer currentTime, si le temps actuel>temps de fin de livraison du dernier round.
	            			break outerloop;
	            		}
	            	}
	            	i++;
	            }
	            //it.remove(); // avoids a ConcurrentModificationException
	        }
            MapManagement.getInstance().getListDelivery().remove(toRemove);
            MapManagement.getInstance().pushToHistory();
        }
	}
	
	public void removeDeliveryWithoutCalc(Long idDelivery) {
		Delivery toRemove = MapManagement.getInstance().getDeliveryById(idDelivery);
        if(MapManagement.getInstance().getListDelivery().contains(toRemove)) {
	    	Iterator it = MapManagement.getInstance().getListDeliverer().entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry <Long, Deliverer> pair = (Map.Entry) it.next();
	            List<Round> rounds = pair.getValue().getListRound();
	            int i=0;
	            outerloop:
	            for (Round round : rounds) {
	            	List<Path> newListPath = new ArrayList();
	            	for(Path path : round.getListPath()) {
	            		if((long)path.getArrival().getId()==(long)toRemove.getId()) {
	            			if(round.getListPath().size()==2) {
	            				rounds.remove(round);
	            			}
	            			else {
		            			Round newRound = MergePathsInRound(pair.getValue().getListRound().get(i), path.getArrival().getId());
		            			pair.getValue().changeRound(i, newRound);
	            			}
	            			pair.getValue().updateRounds(i);
	            			//TODO : gerer currentTime, si le temps actuel>temps de fin de livraison du dernier round.
	            			break outerloop;
	            		}
	            	}
	            	i++;
	            }
	            //it.remove(); // avoids a ConcurrentModificationException
	        }
            MapManagement.getInstance().getListDelivery().remove(toRemove);
            MapManagement.getInstance().pushToHistory();
        }
	}
	
	public Round calculateRoundForOneNode(Long idIntersection, CityMap map, Time startTime ) {
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		List<Long> newDel = new ArrayList<Long>();
		System.out.println("We add "+idIntersection+" and "+MapManagement.getInstance().getWarehouse().getId()+" to the list" );
		newDel.add(idIntersection);
		newDel.add(MapManagement.getInstance().getWarehouse().getId());
		System.out.println("Seemed to work, now we calculate dijkstra");
		Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(map.getGraph(), newDel);
		System.out.println("Dijkstra seemed to work ! Now TSP");
		Round round = tsp.brutForceTSP(graph, dijkstra, startTime);
		System.out.println("TSP seemed to work !");
		return round;
	}
	
	public Round calculateRoundByAddingNodeToExisting(Round previousRound, CityMap map, Long idNode) {
		System.out.println("calculateRoundByAddingNodeToExisting");
		List<Long> listIds = new ArrayList<Long>();
		System.out.println("We add "+ idNode + "to the list");
		listIds.add(idNode);
		for(Path path : previousRound.getListPath()) {
			System.out.println("	We add "+ path.getArrival().getId() + "to the list");
			listIds.add(path.getArrival().getId());
		}
		System.out.println("Warehouse is : " + MapManagement.getInstance().getWarehouse().getId());
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(map.getGraph(), listIds);
		System.out.println("Dijkstra calcultaed");
		Round round = tsp.brutForceTSP(graph, dijkstra, previousRound.getStartTime());
		System.out.println("TSP calcuated");
		return round;
	}
	
	public Round MergePathsInRound(Round previousRound, Long idNode) {
		ArrayList<Path> newListPath = new ArrayList<Path>();
		ArrayList<Segment> newListSeg = new ArrayList<Segment>();
		float newDuration = 0;
		Time newDepartureTime = new Time(0, 0, 0);
		boolean lastPathWasModified = false;
		for(Path path : previousRound.getListPath()) {
			if(path.getArrival().getId()==idNode) {
				newListSeg=path.getListSegment();
				newDuration+=path.getDuration();
				newDuration+=path.getArrival().getDuration();
				newDepartureTime = path.getDepartureTime();
				lastPathWasModified = true;
			}
			else if(lastPathWasModified) {
				newListSeg.addAll(path.getListSegment());
				newDuration+=path.getDuration();
				newDuration+=path.getArrival().getDuration();
				Path newPath = new Path(newListSeg, path.getArrival(), newDuration, newDepartureTime);
				newListPath.add(newPath);
				lastPathWasModified = false;
			}
			else newListPath.add(path);
		}
		previousRound.setListPath(newListPath);
		return previousRound;
	}
	
	
	public Round calculateRoundByRemovingNodeToExisting(Round previousRound, CityMap map, Long idNode) {
		System.out.println("calculateRoundByRemovingNodeToExisting");
		List<Long> listIds = new ArrayList<Long>();
		for(Path path : previousRound.getListPath()) {
			System.out.println("	We add "+ path.getArrival().getId() + "to the list");
			listIds.add(path.getArrival().getId());
		}
		listIds.remove(idNode);
		System.out.println("Warehouse is : " + MapManagement.getInstance().getWarehouse().getId());
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		Map<Long, Map<Long, Float>> graph = dijkstra.doDijkstra(map.getGraph(), listIds);
		System.out.println("Dijkstra calcultaed");
		Round round = tsp.brutForceTSP(graph, dijkstra, previousRound.getStartTime());
		System.out.println("TSP calcuated");
		return round;
	}
	
	public Time getCurrentTimeUsingCalendar() {
	    Calendar cal = Calendar.getInstance();
	    Date date=cal.getTime();
	    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    String formattedDate=dateFormat.format(date);
	    Time currentTime = new Time(formattedDate);
	    return currentTime;
	}
	
	public void createDelivery(Long idDelivery, int duration) {
		Intersection i = MapManagement.getInstance().getIntersectionById(idDelivery);
		Delivery newDelivery = new Delivery(i,(float)duration);
		MapManagement.getInstance().addDeliveryToListDelivery(newDelivery);
	}
	
	/**
	 * Undo an action
	 * Get MapManagement's history list and set new delivery and deliverer list to MapManagement
	 */
	public void undo(int counter) {
		List<Pair<List<Delivery>, Map<Long,Deliverer>>>  history = MapManagement.getInstance().getHistory();
		int j = history.size()-1-counter;
		System.out.println("index : " + history.size() + " - 1 " + " - " + counter + " = " + j);
		
		if (j >= 0 ) {
			Pair<List<Delivery>, Map<Long,Deliverer>> pair = history.get(history.size()-1-counter);
			MapManagement.getInstance().setListDelivery(pair.getKey());
			MapManagement.getInstance().setListDeliverer(pair.getValue());
		}		
	}
	
	/**
	 * Redo an action
	 */
	public void redo(int counter) {
		List<Pair<List<Delivery>, Map<Long,Deliverer>>>  history = MapManagement.getInstance().getHistory();
		int j = history.size()-1-counter;
		System.out.println("index : " + history.size() + " - 1 " + " - " + counter + " = " + j);
		
		if (j < history.size() ) {
			Pair<List<Delivery>, Map<Long,Deliverer>> pair = history.get(history.size()-1-counter);
			MapManagement.getInstance().setListDelivery(pair.getKey());
			MapManagement.getInstance().setListDeliverer(pair.getValue());
		}		
	}
	
}
