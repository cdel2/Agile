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
	
	}
	

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