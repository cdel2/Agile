package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.util.Iterator;

import optimodlyon.agile.models.*;
import optimodlyon.agile.util.Pair;
import optimodlyon.agile.util.StatePair;
import optimodlyon.agile.util.Time;


public class MapManagement{
    private CityMap map = new CityMap();
    private List<Delivery> listDelivery = new ArrayList<Delivery>();
    private Map<Long,Deliverer> listDeliverer = new HashMap<Long,Deliverer>();
	private List<Pair<List<Delivery>, Map<Long,Deliverer>>> history = new ArrayList<Pair<List<Delivery>, Map<Long,Deliverer>>>() ;
    private Warehouse warehouse;

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
            System.out.println("addDeliveryToListDelivery appelé ");
            
            if(!this.listDelivery.contains(newDelivery)) {
            		System.out.println("j'ajoute une delivery car celle-ci n'hexiste pas déjà");
                    this.listDelivery.add(newDelivery);
                    res=true;
            }
            return res;
    }

    public boolean removeDelivery(Long deliveryId) {
        System.out.println("removeDelivery appelé ");
        Delivery toRemove = this.getDeliveryById(deliveryId);            
        if(this.listDelivery.contains(toRemove)) {
        		System.out.println("La delivery n'existe pas ???");
                this.listDelivery.remove(toRemove);
                return true;
        }
        return false;
    }
    
    public void pushToHistory() {
    	//create pair from new lists so that each pair in history is different
    	List<Delivery> newListDelivery = new ArrayList<Delivery>(listDelivery);
    	Map<Long, Deliverer> newListDeliverer = new HashMap<Long, Deliverer>(listDeliverer);
    	
        Pair<List<Delivery>, Map<Long,Deliverer>> p = new StatePair(newListDelivery, newListDeliverer);
    	history.add(p);
    	
    	System.out.println("pushToHistory");
		System.out.println("ééééééééééé history");
		for (int k = 0; k < history.size(); k++) {
			System.out.println(k);
			for (int i = 0; i < history.get(k).getKey().size(); i++) {
				System.out.println(history.get(k).getKey().get(i));
			}
		}

		System.out.println("éééééééé pair ");
		for (int i = 0; i < p.getKey().size(); i++) {
			System.out.println(p.getKey().get(i));
		}

    	System.out.println("\n");
    }

    public List<Pair<List<Delivery>, Map<Long,Deliverer>>> getHistory() {
    	return history;
    }
    public static void main(String[] args) {
            Intersection origin = new Intersection((long)1, (float)-50, (float)50);
            System.out.println(origin);
            /*Map<Long,String> listDeliverer = new HashMap<Long,String>();
            listDeliverer.put((long) 1, "a");
            listDeliverer.put((long) 2, "b");
            listDeliverer.put((long) 1, "c");
            List<String> listRound = new ArrayList<String>();
            listRound.add("aa");
            listRound.add("bb");
            listRound.add("cc");
            int i = 0;
            for(Long it : listDeliverer.keySet())
            {
                    System.out.println("round : " +listRound.get(i));	
                    System.out.println("deli : " +it);	
                    i++;
            }*/
    }
}

