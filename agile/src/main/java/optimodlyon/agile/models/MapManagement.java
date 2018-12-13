package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import optimodlyon.agile.util.Pair;
import optimodlyon.agile.util.StatePair;
import optimodlyon.agile.util.Time;



public class MapManagement{
    private CityMap map = new CityMap();
    private List<Delivery> listDelivery = new ArrayList<Delivery>();
    private Map<Long,Deliverer> listDeliverer = new HashMap<Long,Deliverer>();
    private List<Pair<List<Delivery>, Map<Long,Deliverer>>> history = new ArrayList<Pair<List<Delivery>, Map<Long,Deliverer>>>() ;
    private Warehouse warehouse;
    private Time endOfDay = new Time(18,0,1);
    private volatile boolean isRunning = true;

    private static MapManagement instance = null;

    private MapManagement() {}

    public static MapManagement getInstance() {
        if (instance == null) {
            instance = new MapManagement();
        }
        return instance;
    }

    public CityMap getMap() {
        return map;
    }

    public void setMap(CityMap map) {
        this.map = map;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setListDelivery(List<Delivery> listDelivery) {
        this.listDelivery = listDelivery;
    }

    public List<Delivery> getListDelivery() {
        return listDelivery;
    }

    public Map<Long,Deliverer> getListDeliverer() {
        return listDeliverer;
    }

    public void setListDeliverer(Map<Long,Deliverer> listDeliverer) {
        this.listDeliverer = listDeliverer;
        

    }
    
    public Time getEndOfDay () {
    	return endOfDay;
    }
    
    public void setEndOfDay (Time t) {
    	this.endOfDay = t;
    }
    
    public void setIsRunning(boolean b) {
		isRunning = b;

	}
    
    public boolean getIsRunning() {
		return isRunning;
	}

    /**
     * Gives each deliverer a round
     * If we have more deliverers than the number of rounds (N),
     * the N first deliverers will be assigned a round
     * @param listRound
     * @return TODO
     */
    public Map<Long, Deliverer> assignRounds(List<Round> listRound)
    {
        int i =0;
        for(Long it : listDeliverer.keySet())
        {
            if(i < listRound.size()) {
                listDeliverer.get(it).getListRound().add(listRound.get(i));
                i++;
            }
        }
        
        return listDeliverer;
    }

    public void initializeListDeliverer(int nb)
    {
        listDeliverer.clear();
        Long id = new Random().nextLong();
        Deliverer deliverer = new Deliverer(id);
        listDeliverer.put(id,deliverer);
        for(int i=1; i<nb; i++)
        {
            while(listDeliverer.containsKey(id))
            {
                id = new Random().nextLong();
            }
            
            deliverer = new Deliverer(id);
            listDeliverer.put(id,deliverer);
        }
    }

    /*
     * Retrieves an delivery by its id
     */
    public Delivery getDeliveryById(Long id) {
        List<Delivery> listDelivery = MapManagement.getInstance().getListDelivery();
        for(Delivery delivery : listDelivery) {
            if((long)delivery.getId()==(long)id){
                return delivery;
            }
        }
        
        return MapManagement.getInstance().getWarehouse();
    }

    /*
     * Retrieves an intersection by its id
     */
    public Intersection getIntersectionById(Long id) {
        /*
         * Super annoying : we get the graph of CityMap, then we get all the segments that start from the intersection we want to retrieve, 
         *and then we get the start point of this segment which is the Intersection we want.
         */
        return MapManagement.getInstance().getMap().getGraph().get(id).get(0).getStart();
    }

    /**
     * Add a round to a deliverer if the startTime of the round to add
     * is after the endTime of the last round of the deliverer
     * @param deliv
     * @param roundToAdd
     */
    public boolean addRoundToADeliverer(Deliverer deliv, Round roundToAdd) {
        boolean res=false;
        if(deliv != null && roundToAdd != null) {
            if(this.listDeliverer.containsKey(deliv.getId())) {
    			roundToAdd.updateRoundTimes();
                res = this.listDeliverer.get(deliv.getId()).addRoundToList(roundToAdd);
            }
        }
        return res;
    }
    
    public boolean removeLastRoundFromADeliverer(Deliverer deliv) {
    	boolean isRemoved=false;;
    	if(deliv != null && listDeliverer.containsValue(deliv)) {
    		isRemoved = listDeliverer.get(deliv.getId()).removeLastRound();
    	}
    	return isRemoved;
    }

    public boolean addDeliveryToListDelivery(Delivery newDelivery) {
            boolean res = false;
            if(!this.listDelivery.contains(newDelivery)) {
                    this.listDelivery.add(newDelivery);
                    res=true;
            }
            return res;
    }

    public boolean removeDelivery(Long deliveryId) {
        Delivery toRemove = this.getDeliveryById(deliveryId);            
        if(this.listDelivery.contains(toRemove)) {
                this.listDelivery.remove(toRemove);
                return true;
        }
        return false;
    }
    
    public void pushToHistory() {
    	//create pair from new lists so that each pair in history is different
    	List<Delivery> newListDelivery = new ArrayList<Delivery>(listDelivery);
    	
    	Map<Long, Deliverer> newListDeliverer = new HashMap<Long, Deliverer>();
        Deliverer newDeliverer;
        List<Round> newListRound;	

        for (Map.Entry<Long, Deliverer> entry : listDeliverer.entrySet())
        {
                //create new deliverer with the id of current deliverer
                newDeliverer = new Deliverer(entry.getKey());

                //create new list round with current deliverer's round list
                newListRound = new ArrayList<Round>(entry.getValue().getListRound());

                //assign round list to new deliverer
                newDeliverer.setListRound(newListRound);

                newListDeliverer.put(entry.getKey(), newDeliverer);
        }

        Pair<List<Delivery>, Map<Long,Deliverer>> p = new StatePair(newListDelivery, newListDeliverer);
    	history.add(p);
    }

    public List<Pair<List<Delivery>, Map<Long,Deliverer>>> getHistory() {
    	return history;
    }
    
    public void setHistory(List<Pair<List<Delivery>, Map<Long,Deliverer>>> history) {
    	this.history = history;
    }
    
    public void clearHistory(int counter) {
        int index = history.size()-counter;
        for(int i = index; i < history.size(); i++) {
            history.remove(i);
        }
    }
}

