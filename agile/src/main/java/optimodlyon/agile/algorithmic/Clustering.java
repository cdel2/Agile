package optimodlyon.agile.algorithmic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.Segment;
import optimodlyon.agile.models.CityMap;

public class Clustering {
	
	ArrayList<Delivery> sortCluster (CityMap M){
		
		
		//Initialisation des valeurs 

        //Initialisation entrepot et nbLivraisons et nbVlivreurs
		ArrayList<Delivery> listDel = M.getListDelivery();
        int nbLivreurs = M.getNbDeliverers();
        int nbLivraisons = listDel.size(); 
        
        float xWareHouse = M.getWarehouse().getLongitude();
        float yWareHouse = M.getWarehouse().getLatitude();
        
        // Création des listes retours X, Y, O
        // ResX contient des longitudes, ResY des latitudes
        ArrayList<Float> resX = new ArrayList<Float>();
        ArrayList<Float> resY = new ArrayList<Float>();
        ArrayList<Double> resO = new ArrayList<Double>();
        
        
        
        
        for (int i =0; i<nbLivraisons;i++){
            resX.add(listDel.get(i).getLongitude());
            resY.add(listDel.get(i).getLatitude());
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
        
        /*
        ArrayList<Delivery> listTri = new ArrayList<Delivery>();
        for (int r=0; r<nbLivraisons-1; r++){
        	boolean stop = false;
        	int cmp = 0;
        	while (!stop) {
        		System.out.println(cmp);
        		if (resX.get(r)+xWareHouse==listDel.get(cmp).getLongitude() && resY.get(r)+yWareHouse==listDel.get(cmp).getLatitude()) {
        			listTri.add(listDel.get(cmp));
        		}
        		cmp++;
        	}
        }
        */
        return listDel; 
	}
	
	public ArrayList<ArrayList<Delivery>> dispatchCluster(CityMap map, int deliverers){
		ArrayList<Delivery> sortedDeliveries = sortCluster(map);
		ArrayList<ArrayList<Delivery>> clusters = new ArrayList<ArrayList<Delivery>>();
		int j = 0;
		ArrayList<Delivery> currentCluster = new ArrayList<Delivery>();
		int clusterN = (int) Math.ceil(sortedDeliveries.size()/deliverers);
		for(int i=0;i<sortedDeliveries.size();i++) {
			currentCluster.add(sortedDeliveries.get(i));
			if(0==((i-1)%clusterN) && i!=0) {
				ArrayList<Delivery> finalCluster = new ArrayList<Delivery>(currentCluster);
				clusters.add(finalCluster);
				currentCluster.clear();
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