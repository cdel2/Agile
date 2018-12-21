package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import optimodlyon.agile.exceptions.UndoRedoException;
import optimodlyon.agile.util.Time;



public class MapManagement{
    private CityMap map = new CityMap();
    private List<Delivery> listDelivery = new ArrayList<>();
    private Map<Long,Deliverer> listDeliverer = new HashMap<>();
    private Warehouse warehouse;
    private Time endOfDay = new Time(18,0,1);
    private volatile boolean isRunning = true;    
    private Stack<Map<Long, Deliverer>> undoList = new Stack<>();
    private Stack<Map<Long, Deliverer>> redoList = new Stack<>();

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
        for(Delivery delivery : listDelivery) {
            if((long)delivery.getId()==(long)id){
                return delivery;
            }
        }
        
        return warehouse;
    }

    /*
     * Retrieves an intersection by its id
     */
    public Intersection getIntersectionById(Long id) {
        return map.getGraph().get(id).get(0).getStart();
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
                if(res) {
                	System.out.println("Round added to deliverer " + deliv.getId());
                } else {
                	System.out.println("Round couldn't be added to deliverer " + deliv.getId());
                	System.out.println("Details of the round :" + roundToAdd.toString());
            }
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
    
    
    public void addMapToHistory() {
        Map<Long, Deliverer> copyMap = new HashMap<>();
        
        Deliverer d;
        Long id;
        for (Map.Entry<Long, Deliverer> entry : listDeliverer.entrySet())
        {
            d = new Deliverer(entry.getValue());
            id = entry.getKey();
            copyMap.put(id, d);
        }
        
        undoList.push(copyMap);
        redoList.clear();
    }
    
    public void undo() throws RuntimeException {
            if(!undoList.isEmpty()){
	            redoList.push(listDeliverer);
	            Map<Long, Deliverer> map = undoList.pop();
	            setListDeliverer(map);
                
            }
            else {
                throw new UndoRedoException();
            }
    }
        
    public void redo() throws RuntimeException {
            if(!redoList.isEmpty()){
                undoList.push(listDeliverer);
                Map<Long, Deliverer> map = redoList.pop();
                setListDeliverer(map);                
            }
            else {
                throw new UndoRedoException();
            }
    }
    
    public void clearHistory() {
        undoList.clear();
        redoList.clear();
    }

    public void removeLastMapInHistory() {
        undoList.pop();
    }
    
    public boolean isUndoable() {
        return(undoList.size() > 0);
    }
    
    public boolean isRedoable() {
        return(redoList.size() > 0);
    }
    
    public Stack<Map<Long, Deliverer>> getUndoList() {
    	return undoList;
    }
    
    public Stack<Map<Long, Deliverer>> getRedoList() {
    	return redoList;
    }
}

