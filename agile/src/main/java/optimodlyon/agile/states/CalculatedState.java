package optimodlyon.agile.states;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import optimodlyon.agile.algorithmic.Dijkstra;
import optimodlyon.agile.algorithmic.TSP;
import optimodlyon.agile.exceptions.FunctionalException;
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
	public void addDelivery(Long idDelivery, int duration) throws Exception{
                MapManagement.getInstance().addMapToHistory();
		/*
		 * Create a new round between the warehouse and the new point to deliver
		 * The startTime of the round is 00:00.00 hence its EndTime is the duration
		 * Both StartTime and Endtime will be set 	 the deliverer is found
		 */
		Time t0 = new Time(0,0,0); Time endOfDay = new Time(18,0,0);
		createDelivery(idDelivery, duration);
		Round newRound = calculateRoundForOneNode(idDelivery,MapManagement.getInstance().getMap(), t0);
		Round newPossibleRound = newRound; //created if we need to create another round 
		Time roundTime = newRound.getEndTime();
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
		for (Long key : delivererMap.keySet()) {
			int listRoundSize = delivererMap.get(key).getListRound().size(); 
			/*
			 * The deliverer has at least 1 round assigned
			 */
			if(listRoundSize > 0) {
				Round round = delivererMap.get(key).getListRound().get(listRoundSize-1);
				/*
				 * The deliverer has only 1 round assigned
				 */
				if(listRoundSize<2) {
					if(round != null) {
						tmpTime = new Time(round.getEndTime().toString());
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
					// check if the deliverer is not gone doing its additionnal round yet
					//Time currentTime = getCurrentTimeUsingCalendar();
					Time currentTime = new Time(8,0,0); // we simulate a current time at 8:00.00
					if(currentTime.isBefore(delivererMap.get(key).getListRound().get(listRoundSize-1).getStartTime())) {
						/*
						 * We create a new round from the last round of the deliverer and we check if 
						 * this new round finishes before the current min Time 
						 */
						Round newPossibleRoundTmp = calculateRoundByAddingNodeToExisting(delivererMap.get(key).getListRound().get(listRoundSize-1), MapManagement.getInstance().getMap(),idDelivery);
						if(newPossibleRoundTmp.getEndTime().isBefore(minTime)) {
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
				newRound.setEndTime(tend);
			}
			if(!MapManagement.getInstance().addRoundToADeliverer(delivererMap.get(keyBestDeliv), newRound)) {
				removeDelivery(idDelivery, true);
			}
		} else {
			throw new FunctionalException("The added round exceed the end of working day time");
		}
		
    	MapManagement.getInstance().setIsRunning(true);
	}
	
	public void removeDelivery(Long idDelivery, boolean calc) throws Exception {
		MapManagement.getInstance().addMapToHistory();
		Delivery toRemove = MapManagement.getInstance().getDeliveryById(idDelivery);
        if(MapManagement.getInstance().getListDelivery().contains(toRemove)) {
	    	Iterator<Entry<Long, Deliverer>> it = MapManagement.getInstance().getListDeliverer().entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry <Long, Deliverer> pair = (Entry<Long, Deliverer>) it.next();
	            List<Round> rounds = pair.getValue().getListRound();
	            int i=0;
	            outerloop:
	            for (Round round : rounds) {
	            	for(Path path : round.getListPath()) {
	            		if((long)path.getArrival().getId()==(long)toRemove.getId()) {
	            			if(round.getListPath().size()==2) {
	            				rounds.remove(round);
	            			}
	            			else {
	            		    	if(calc) {
			            			Round newRound = calculateRoundByRemovingNodeToExisting(pair.getValue().getListRound().get(i), MapManagement.getInstance().getMap(), path.getArrival().getId());
			            			pair.getValue().changeRound(i, newRound);
	            		    	}
	            		    	else {
			            			Round newRound = MergePathsInRound(pair.getValue().getListRound().get(i), path.getArrival().getId());
			            			pair.getValue().changeRound(i, newRound);
	            		    	}
	            			}
	            			pair.getValue().updateRounds(i);
	            			break outerloop;
	            		}
	            	}
	            	i++;
	            }
	        }
            List<Delivery> lDeliveries = new ArrayList<Delivery>(MapManagement.getInstance().getListDelivery());
            lDeliveries.remove(toRemove);
            MapManagement.getInstance().setListDelivery(lDeliveries);
        }
	}
	
	public Round calculateRoundForOneNode(Long idIntersection, CityMap map, Time startTime ) throws Exception {
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		List<Long> newDel = new ArrayList<Long>();
		newDel.add(idIntersection);
		newDel.add(MapManagement.getInstance().getWarehouse().getId());
		Map<Long, Map<Long, Float>> graph;
		graph = dijkstra.doDijkstra(map.getGraph(), newDel);
		Round round = tsp.startTSPMatrix(10000, graph.size(), graph, startTime, dijkstra);
		return round;
	}
	
	public Round calculateRoundByAddingNodeToExisting(Round previousRound, CityMap map, Long idNode) throws Exception {
		List<Long> listIds = new ArrayList<Long>();
		listIds.add(idNode);
		for(Path path : previousRound.getListPath()) {
			listIds.add(path.getArrival().getId());
		}
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		Map<Long, Map<Long, Float>> graph;
		try {
			graph = dijkstra.doDijkstra(map.getGraph(), listIds);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		Round round = tsp.startTSPMatrix(10000, graph.size(), graph,  previousRound.getStartTime(), dijkstra);
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
		Round newRound = new Round(previousRound);
		newRound.setListPath(newListPath);
		return newRound;
	}
	
	
	public Round calculateRoundByRemovingNodeToExisting(Round previousRound, CityMap map, Long idNode) throws Exception{
		List<Long> listIds = new ArrayList<Long>();
		for(Path path : previousRound.getListPath()) {
			listIds.add(path.getArrival().getId());
		}
		listIds.remove(idNode);
		Dijkstra dijkstra = new Dijkstra();
		TSP tsp = new TSP();
		Map<Long, Map<Long, Float>> graph;
		graph = dijkstra.doDijkstra(map.getGraph(), listIds);
		Time startTime = MapManagement.getInstance().getWarehouse().getTimeStart();
		Round round = tsp.startTSPMatrix(10000, graph.size(), graph, startTime, dijkstra);
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

	public void undo() throws Exception {
            System.out.println("yooooooooo");
            MapManagement.getInstance().undo();
	}
        
        public void redo() {
            MapManagement.getInstance().redo();
	}
    
    @Override
    public boolean stopCalculation() {
    	MapManagement.getInstance().setIsRunning(false);
    	try {
    	    Thread.sleep(200);
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}
    	MapManagement.getInstance().setIsRunning(true);
        return true;
    }
    
    public void clearHistory() {
        MapManagement.getInstance().clearHistory();
    }
}
