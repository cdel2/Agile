package optimodlyon.agile.algorithmic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.models.*;


public class Clustering {
	public static void main (String[] args) throws ParseException {
		int deliverers=6;
		
		ArrayList<Integer> sortedDeliveries = new ArrayList<Integer>();
		sortedDeliveries.add(1);
		sortedDeliveries.add(2);
		sortedDeliveries.add(3);
		sortedDeliveries.add(4);
		sortedDeliveries.add(5);
		sortedDeliveries.add(6);
		
		ArrayList<ArrayList<Integer>> clusters = new ArrayList<ArrayList<Integer>>();
		int nbdeliveries=sortedDeliveries.size();
		if(deliverers==1)
		{
			ArrayList<Integer> currentCluster = new ArrayList<Integer>();
			for(int i=0;i<nbdeliveries;i++)
			{
				currentCluster.add(sortedDeliveries.get(i));
				System.out.println(currentCluster);
			}
			clusters.add(currentCluster);
			System.out.println(clusters);
		}
		if(deliverers==6)
		{
			ArrayList<Integer> currentCluster = new ArrayList<Integer>();
			for(int i=0;i<nbdeliveries;i++)
			{
				currentCluster.add(sortedDeliveries.get(i));
				clusters.add(currentCluster);
				currentCluster.clear();
			}
			System.out.println(clusters);
		}/*
		else {
		int modulo = nbdeliveries%deliverers;
		int divider=(nbdeliveries-modulo)/deliverers;
		int currentDiv=0;
		ArrayList<Integer> listIndex = new ArrayList<Integer>();
			for(int i=0;i<modulo;i++)
			{
			listIndex.add(divider+currentDiv);
			currentDiv+=divider+1;
			System.out.println(listIndex);
			}
		currentDiv--;
			for(int j=modulo;j<deliverers;j++)
			{
			listIndex.add(currentDiv+divider);
			currentDiv+=divider;
			System.out.println(listIndex);
			}
		ArrayList<Integer> currentCluster=new ArrayList<Integer>(sortedDeliveries.subList(0, listIndex.get(0)+1));
		clusters.add(currentCluster);
		System.out.println(clusters);
		for(int k=1;k<listIndex.size();k++)
			{
				currentCluster=new ArrayList<Integer>(sortedDeliveries.subList(listIndex.get(k-1)+1, listIndex.get(k)+1));

				System.out.println(listIndex.get(k-1));
				System.out.println(listIndex.get(k));
				clusters.add(currentCluster);
			}
		System.out.println(clusters);}*/
			
		/*
		for(int i=listIndex.get(modulo);i<deliverers;i++)
		{
			listIndex.add(divider+currentDiv);
			currentDiv+=divider-1;
			System.out.println(listIndex);
		}
		System.out.println(listIndex);
		ArrayList<Integer> currentCluster=new ArrayList<Integer>(sortedDeliveries.subList(0, listIndex.get(0)));
		clusters.add(currentCluster);
		System.out.println(clusters);
		for(int k=1;k<listIndex.size();k++)
		{
			currentCluster=new ArrayList<Integer>(sortedDeliveries.subList(listIndex.get(k-1), listIndex.get(k)));
			clusters.add(currentCluster);
		}
		System.out.println(clusters);*/
	}
		/*ArrayList<Segment> segments = new ArrayList<Segment>();
	    Intersection i0 = new Intersection((long)0,(float)0.0,(float)0.0);
	    
	    Intersection i1 = new Intersection((long)1,(float)0.0,(float)2.0);
	    Intersection i2 = new Intersection((long)2,(float)1.0,(float)0.0);
	    
	    Intersection i3 = new Intersection((long)3,(float)1.0,(float)2.0);
	    Intersection i4 = new Intersection((long)4,(float)2.0,(float)0.0);
	    
	    Segment s0 = new Segment(i0,i1,1);
	    Segment s1 = new Segment(i0,i2,2);
	    Segment s2 = new Segment(i0,i3,-1);
	    segments.add(s0);
	    segments.add(s1);
	    segments.add(s2);
	    /*
	    for (int i = 0; i < segments.size(); i++) {
	    	System.out.println(segments.get(i));
	    }
	    HashMap<Long, ArrayList<Segment>> graph = new HashMap<Long, ArrayList<Segment>>();
	    
	    graph.put(i0.getId(), segments);
	    
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = format.parse( "2009-12-31" );
	    Warehouse wsh = new Warehouse((long)0, (float)0.0, (float)0.0, date);
	    
		

		//System.out.println("long"+ i1.getLongitude());
		Delivery i11 = new Delivery(date, (float)1, (long)1, (float)0.0, (float)2.0);
		Delivery i31 = new Delivery(date, (float)1, (long)3, (float)1.0, (float)2.0);
		Delivery i21 = new Delivery(date, (float)1, (long)2, (float)1.0, (float)0.0);
		Delivery i41 = new Delivery(date, (float)1, (long)4, (float)2.0, (float)0.0);
		
		ArrayList<Delivery> theoricList = new ArrayList<Delivery>();
		theoricList.add(i11);
		theoricList.add(i31);
		theoricList.add(i21);
		theoricList.add(i41);
		
		ArrayList<Delivery> list = new ArrayList<Delivery>();
		list.add(i21);
		list.add(i11);
		list.add(i31);
		list.add(i41);
		/*
	    for (int i = 0; i < theoricList.size(); i++) {
	    	System.out.println("del" + theoricList.get(i));
	    }
	    
		CityMap map = new CityMap(graph, 2, wsh, list);
		
		Clustering clustering = new Clustering();
		
		
		ArrayList<Delivery>list2 = clustering.sortCluster(map);
		
	    for (int i = 0; i < list2.size(); i++) {
	    	System.out.println("new del" + list2.get(i));
	    }
	}*/
	
	
	
	

	ArrayList<Delivery> sortCluster (CityMap M){

		//Initialisation des valeurs 

        //Initialisation entrepot et nbLivraisons et nbVlivreurs
		ArrayList<Delivery> listDel = M.getListDelivery();
        int nbLivreurs = M.getNbDeliverers();
        int nbLivraisons = listDel.size(); 
        //System.out.println(nbLivraisons);
        float xWareHouse = M.getWarehouse().getLongitude();
        float yWareHouse = M.getWarehouse().getLatitude();
        
        // Création des listes retours X, Y, O
        // ResX contient des longitudes, ResY des latitudes
        ArrayList<Float> resX = new ArrayList<Float>();
        ArrayList<Float> resY = new ArrayList<Float>();
        ArrayList<Double> resO = new ArrayList<Double>();
        
        
        
        for (int i =0; i<nbLivraisons;i++){
            //System.out.println(listDel.get(i));
         }
        
        for (int i =0; i<nbLivraisons;i++){
            resX.add(listDel.get(i).getLongitude());
            //System.out.println(listDel.get(i).getLongitude());
            resY.add(listDel.get(i).getLatitude());
            //System.out.println(listDel.get(i).getLatitude());
         }
        
        
        //On place l'entrepot aux coordonées (0,0)
        
        
        for(int a=0; a<nbLivraisons;a++){
            resX.set(a,resX.get(a)-xWareHouse);
            resY.set(a,resY.get(a)-yWareHouse);
        }
        
        //Calcul de l'angle de chaque point
        

        for (int j =0; j<nbLivraisons;j++){
        
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
        
        
        // Tri par teta
        boolean end = false;

        while (!end){
            end=true;
            for (int k=0; k<nbLivraisons-1; k++){
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

        //Création de la liste de delivery retour        
        ArrayList<Delivery> listTri = new ArrayList<Delivery>();
        for (int r=0; r<nbLivraisons; r++){
        	boolean stop = false;
        	int cmp = 0;
        	while (!stop) {
        		//System.out.println(cmp);
        		if (resX.get(r)+xWareHouse==listDel.get(cmp).getLongitude() && resY.get(r)+yWareHouse==listDel.get(cmp).getLatitude()) {
        			listTri.add(listDel.get(cmp));
        			stop=true;
        		}
        		cmp++;
        	}
        }
        
        return listTri; 
	}
	
	public ArrayList<ArrayList<Delivery>> pushFrontDelivery(ArrayList<ArrayList<Delivery>> goalDeliveries,ArrayList<ArrayList<Delivery>> sortedDeliveries){
		ArrayList<Delivery> currentCluster = new ArrayList<Delivery>();
		currentCluster= goalDeliveries.get(goalDeliveries.size()-1);
		
		return sortedDeliveries;
	}
	
	public ArrayList<ArrayList<Delivery>> dispatchCluster(CityMap map, int deliverers){
		ArrayList<Delivery> sortedDeliveries = sortCluster(map);
		ArrayList<ArrayList<Delivery>> clusters = new ArrayList<ArrayList<Delivery>>();
		
		int nbdeliveries=sortedDeliveries.size();
		if(deliverers==1)
		{
			ArrayList<Delivery> currentCluster = new ArrayList<Delivery>();
			for(int i=0;i<nbdeliveries;i++)
			{
				currentCluster.add(sortedDeliveries.get(i));
			}
			clusters.add(currentCluster);
		}
		else {
		int modulo = nbdeliveries%deliverers;
		int divider=(nbdeliveries-modulo)/deliverers;
		int currentDiv=0;
		ArrayList<Integer> listIndex = new ArrayList<Integer>();
			for(int i=0;i<modulo;i++)
			{
			listIndex.add(divider+currentDiv);
			currentDiv+=divider+1;
			}
		currentDiv--;
			for(int j=modulo;j<deliverers;j++)
			{
			listIndex.add(currentDiv+divider);
			currentDiv+=divider;
			}
		ArrayList<Delivery> currentCluster=new ArrayList<Delivery>(sortedDeliveries.subList(0, listIndex.get(0)+1));
		clusters.add(currentCluster);
		for(int k=1;k<listIndex.size();k++)
			{
				currentCluster=new ArrayList<Delivery>(sortedDeliveries.subList(listIndex.get(k-1)+1, listIndex.get(k)+1));

				clusters.add(currentCluster);
			}
		}
		return clusters;
	}
		/*
		
		
		
		ArrayList<Delivery> sortedDeliveries = sortCluster(map);
		ArrayList<ArrayList<Delivery>> clusters = new ArrayList<ArrayList<Delivery>>();
		int nbdeliveries=sortedDeliveries.size();
		int modulo = nbdeliveries%deliverers;
		int divider=(nbdeliveries-modulo)/2;
		int currentDiv=0;
		ArrayList<Integer> listIndex = new ArrayList<Integer>();
		for(int i=0;i<divider-1;i++)
		{
			for(int j=0;j<modulo;j++)
			{
			listIndex.add(divider+1+currentDiv);
			currentDiv=divider+1;
			}
			listIndex.add(divider+currentDiv);
			currentDiv=divider;
		}
		
		ArrayList<Delivery> currentCluster=new ArrayList<Delivery>(sortedDeliveries.subList(0, listIndex.get(0)));
		clusters.add(currentCluster);
		for(int k=1;k<listIndex.size();k++)
		{
			currentCluster=new ArrayList<Delivery>(sortedDeliveries.subList(listIndex.get(k-1)+1, listIndex.get(k)));
			clusters.add(currentCluster);
		}
		return clusters;
	}*/
	
	//Creates an Array of IDs for Dijkstra arguments
	public static ArrayList<Long> createIdArray(ArrayList<Delivery> deliveryArray){
		ArrayList<Long> ids = new ArrayList<Long>();
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