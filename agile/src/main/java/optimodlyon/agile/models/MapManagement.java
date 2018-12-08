package optimodlyon.agile.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import optimodlyon.agile.models.*;


public class MapManagement{
    private CityMap map = new CityMap();
    private List<Delivery> listDelivery = new ArrayList<Delivery>();
    private Map<Long,Deliverer> listDeliverer = new HashMap<Long,Deliverer>();
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
     */
    public void assignRounds(List<Round> listRound)
    {
        int i =0;
        for(Long it : listDeliverer.keySet())
        {
            if(i < listRound.size()) {
                listDeliverer.get(it).getListRound().add(listRound.get(i));
                i++;
            }
        }
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
                            res = this.listDeliverer.get(deliv.getId()).addRoundToList(roundToAdd);
                    }
            }
            return res;
    }

    public boolean addDeliveryToListDelivery(Delivery newDelivery) {
            boolean res = false;
            if(!this.listDelivery.contains(newDelivery)) {
                    this.listDelivery.add(newDelivery);
                    res=true;
            }
            return res;
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

