package optimodlyon.agile.algorithmic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.models.*;


public class Clustering {
    List<Delivery> sortCluster (){
        //Values initialization
        CityMap M = MapManagement.getInstance().getMap();
        
        //Initialization of number of deliveries, warehouse and number of deliverers
        List<Delivery> listDel = MapManagement.getInstance().getListDelivery();
        int nbDeliverers = MapManagement.getInstance().getListDeliverer().size();
        int nbDeliveries = listDel.size(); 
        float xWareHouse = MapManagement.getInstance().getWarehouse().getLongitude();
        float yWareHouse = MapManagement.getInstance().getWarehouse().getLatitude();

        // Return lists X, Y, O creation
        // ResX contains longitudes, ResY contains latitudes
        List<Float> resX = new ArrayList<Float>();
        List<Float> resY = new ArrayList<Float>();
        List<Double> resO = new ArrayList<Double>();

        for (int i =0; i<nbDeliveries;i++){
            resX.add(listDel.get(i).getLongitude());
            resY.add(listDel.get(i).getLatitude());
        }

        //Warehouse location is fixed at (0,0)
        for(int a=0; a<nbDeliveries;a++){
            resX.set(a,resX.get(a)-xWareHouse);
            resY.set(a,resY.get(a)-yWareHouse);
        }

        //For each delivery let's calculate its angle formed with the warehouse
        for (int j =0; j<nbDeliveries;j++){
            if (resX.get(j)>0.0 && resY.get(j)>0.0){
                double tmp = (resX.get(j))/(resY.get(j));
                double O = java.lang.Math.atan(tmp);
                resO.add(O);
            }

            else if (resX.get(j)>0.0 && resY.get(j)<0.0){
                double tmp = (-resY.get(j))/(resX.get(j));               
                double O = java.lang.Math.atan(tmp)+ (Math.PI/2.0);
                resO.add(O);
            }

            else if (resX.get(j)<0.0 && resY.get(j)<0.0){
                double tmp = (resX.get(j))/(resY.get(j));
                double O = java.lang.Math.atan(tmp)+ (Math.PI);
                resO.add(O);
            }

            else if (resX.get(j)<0.0 && resY.get(j)>0.0){
                double tmp =(resY.get(j))/(-resX.get(j));
                double O = java.lang.Math.atan(tmp)+ (Math.PI*1.5);
                resO.add(O);
            }
            else if (resX.get(j)==0.0){
                if (resY.get(j)<0.0){
                    double O=Math.PI;
                    resO.add(O);
                }
                else if (resY.get(j)>0.0){
                    double O=0.0;
                    resO.add(O);
                }else {
                    System.out.println("Erreur point de livraison à l'entrepot");
                    break;
                }
            }
            else if (resY.get(j)==0.0){
                if (resX.get(j)<0.0){
                    double O=Math.PI*1.5;
                    resO.add(O);
                }
                else if (resX.get(j)>0.0){
                    double O=Math.PI*0.5;
                    resO.add(O);
                }else {
                    System.out.println("Erreur point de livraison à l'entrepot");
                    break;
                }
            }
        }


        // Theta sorting
        boolean end = false;

        while (!end){
            end=true;
            for (int k=0; k<nbDeliveries-1; k++){
                if (resO.get(k)>resO.get(k+1)){
                    double t= resO.get(k);
                    resO.set(k,resO.get(k+1));
                    resO.set(k+1,t);

                    float tX= resX.get(k);
                    resX.set(k,resX.get(k+1));
                    resX.set(k+1,tX);

                    float tY= resY.get(k);
                    resY.set(k,resY.get(k+1));
                    resY.set(k+1,tY);
                    end=false;

                }
            }
        }

        //Creation of list of Deliveries to return       
        List<Delivery> listTri = new ArrayList<Delivery>();
        for (int r=0; r<nbDeliveries; r++){
            boolean stop = false;
            int cmp = 0;
            while (!stop) {
                if (resX.get(r)+xWareHouse==listDel.get(cmp).getLongitude() && resY.get(r)+yWareHouse==listDel.get(cmp).getLatitude()) {
                    listTri.add(listDel.get(cmp));
                    stop=true;
                }
                cmp++;
            }
        }
        
        return listTri; 
    }

    public List<List<Delivery>> pushFrontDelivery(List<List<Delivery>> goalDeliveries,List<List<Delivery>> sortedDeliveries){
        List<Delivery> currentCluster = new ArrayList<Delivery>();
        currentCluster= goalDeliveries.get(goalDeliveries.size()-1);

        return sortedDeliveries;
    }

//    public List<List<Delivery>> dispatchCluster(CityMap map, int deliverers){
//        List<Delivery> sortedDeliveries = sortCluster();
//        List<List<Delivery>> clusters = new ArrayList<List<Delivery>>();
//
//        int nbdeliveries=sortedDeliveries.size();
//        if(deliverers==1)
//        {
//            List<Delivery> currentCluster = new ArrayList<Delivery>();
//            for(int i=0;i<nbdeliveries;i++)
//            {
//                currentCluster.add(sortedDeliveries.get(i));
//            }
//            clusters.add(currentCluster);
//        }
//
//        else {
//            if(deliverers>nbdeliveries) {
//                deliverers=nbdeliveries;
//            }
//            
//            int modulo = nbdeliveries%deliverers;
//            int divider=(nbdeliveries-modulo)/deliverers;
//            int currentDiv=0;
//            
//            ArrayList<Integer> listIndex = new ArrayList<Integer>();
//            
//            for(int i=0;i<modulo;i++) {
//                listIndex.add(divider+currentDiv);
//                currentDiv+=divider+1;
//            }
//            
//            currentDiv--;
//            
//            for(int j=modulo;j<deliverers;j++)
//            {
//                listIndex.add(currentDiv+divider);
//                currentDiv+=divider;
//            }
//            
//            List<Delivery> currentCluster=new ArrayList<Delivery>(sortedDeliveries.subList(0, listIndex.get(0)+1));
//            clusters.add(currentCluster);
//        
//            for(int k=1;k<listIndex.size();k++){
//                currentCluster = new ArrayList<Delivery>(sortedDeliveries.subList(listIndex.get(k-1)+1, listIndex.get(k)+1));
//                clusters.add(currentCluster);
//            }
//        }
//        return clusters;
//    }
    
    public List<Delivery> changeStartingPoint(List<Delivery> sortedDeliveries, int deliverers){
    	int nbDel=sortedDeliveries.size();
		int clusterSize = (int) Math.floor(nbDel/deliverers);
    	int rest=nbDel%deliverers;
    	List<Integer> clusters= new ArrayList<Integer>();
    	for(int i=0; i<deliverers; i++) {
    		if(rest>0) {
    			int currentClusterSize=clusterSize+1;
    			if(i>0) currentClusterSize+=clusters.get(i-1);
    			clusters.add(currentClusterSize);
    			rest--;
    		}
    		else {
    			int currentClusterSize=clusterSize;
    			if(i>0) currentClusterSize+=clusters.get(i-1);
    			clusters.add(currentClusterSize);
    		}
    	}
    	List<Double> angles = new ArrayList<Double>();
    	angles.add(Intersection.angle(MapManagement.getInstance().getWarehouse(), sortedDeliveries.get(nbDel-1), sortedDeliveries.get(0)));
    	for(int i=0; i<nbDel-1; i++) {
    		angles.add(Intersection.angle(MapManagement.getInstance().getWarehouse(), sortedDeliveries.get(i), sortedDeliveries.get(i+1)));
        }
    	int bestIndex=0;
    	double bestValue=0;
    	int currentIndex=0;
    	while(currentIndex<nbDel) {
        	double currentValue=0;
    		for(int h=0; h<deliverers; h++) {
    			currentValue+=(angles.get((currentIndex+clusters.get(h))%nbDel));
    		}
    		if(currentValue>bestValue) {
    			bestIndex=currentIndex;
    			bestValue=currentValue;
    		}
    		currentIndex++;
    	}
    	return changeStart(sortedDeliveries, bestIndex);
    }
    
    public List<Delivery> changeStart(List<Delivery> deliveries, int newStart){
    	List<Delivery> sortedDeliveries = new ArrayList<Delivery>();
    	for(int i=newStart; i<deliveries.size()+newStart; i++) {
    		sortedDeliveries.add(deliveries.get(i%deliveries.size()));
    	}
    	return sortedDeliveries;
    }
    
	public List<List<Delivery>> dispatchCluster(CityMap map, int deliverers){
		List<Delivery> sortedDeliveries = sortCluster();
		sortedDeliveries = changeStartingPoint(sortedDeliveries, deliverers);
		List<List<Delivery>> clusters = new ArrayList<List<Delivery>>();
		int clusterSize = (int) Math.floor(sortedDeliveries.size()/deliverers);
		int rest = sortedDeliveries.size()%deliverers;
		int index = 0;
		for(int i=1; i<=deliverers;i++) {
			List<Delivery> currentCluster = new ArrayList<Delivery>();
			for(int j=0; j<clusterSize;j++) {
				currentCluster.add(sortedDeliveries.get(index));
				index++;
			}
			if(rest!=0) {
				currentCluster.add(sortedDeliveries.get(index));
				index++;
				rest--;
			}
			clusters.add(currentCluster);
		}
		return clusters;
	}

    //Creates an Array of IDs for Dijkstra arguments
    public static List<Long> createIdArray(List<Delivery> deliveryArray){
        List<Long> ids = new ArrayList<Long>();
        
        for(Delivery delivery : deliveryArray) {
            ids.add(delivery.getId());
        }
        
        return ids;
    } 


    public Map<Long, List<Segment>> reform(HashMap<Long, ArrayList<Segment>> map){
        Map<Long, ArrayList<Segment>> newMap = new HashMap<Long, ArrayList<Segment>>(map);
        Iterator it = newMap.entrySet().iterator();
        Map<Long, List<Segment>> finalMap = new HashMap<Long, List<Segment>>();
        
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            finalMap.put((long)pair.getKey(), (List<Segment>) pair.getValue());
        }
        
        return finalMap;
    }
}