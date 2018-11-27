package optimodlyon.agile.xml;
import optimodlyon.agile.models.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java. text.SimpleDateFormat;
import java.util.HashMap;

import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.Segment;

public class DeserializerXML {

  public static CityMap deserializeMap(String type) {
	
    try {

        File fXmlFile = new File("src/main/java/optimodlyon/agile/files/" + type + "Plan.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
                
        NodeList nList = doc.getElementsByTagName("noeud"); 
        
        HashMap<Long, Intersection> intersections = new HashMap<Long, Intersection>(); 
        Long id;
        float latitude;
        float longitude;
        Intersection inter;
        for (int i = 0; i<nList.getLength(); i++) {
            final Element node = (Element) nList.item(i);
            id = Long.parseLong(node.getAttribute("id"));
            latitude = Float.valueOf(node.getAttribute("latitude"));
            longitude = Float.valueOf(node.getAttribute("longitude"));
            inter = new Intersection(id, latitude, longitude);
            intersections.put(id, inter);
        }
        
        nList = doc.getElementsByTagName("troncon");            
        Segment seg;
        Intersection originInter; 
        Intersection destInter;
        Long idOrigin;
        Long idDestination;
        float duration;
        HashMap<Long, ArrayList<Segment>> graph = new HashMap<Long, ArrayList<Segment>>();
        
        for (int i = 0; i<nList.getLength(); i++) {
        	final Element node = (Element) nList.item(i);
        	idOrigin = Long.valueOf(node.getAttribute("origine"));
            idDestination = Long.valueOf(node.getAttribute("destination"));
            duration = Float.parseFloat(node.getAttribute("longueur"));
            
            originInter = intersections.get(idOrigin);
            destInter = intersections.get(idDestination);
            
            if(originInter != null && destInter != null) {
            	seg = new Segment(originInter,destInter,duration);
            	if (graph.get(originInter.getId()) != null) {
            		graph.get(originInter.getId()).add(seg);
            	}
            	else {
            		ArrayList <Segment> segments = new ArrayList<Segment>();
            		segments.add(seg);
            		graph.put(originInter.getId(), segments);
            	}
            }        	
        }
        
        CityMap map = new CityMap(graph);
        
        return map;
        
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("caca");
            return null;
        }
    }
  
  	public static CityMap deserializeDelivery(String file, CityMap map) {
  		try {

  	        File fXmlFile = new File("src/main/java/optimodlyon/agile/xml/"+ file);

  	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
  	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
  	        Document doc = dBuilder.parse(fXmlFile);
  	        doc.getDocumentElement().normalize();
  	                
  	        NodeList dList = doc.getElementsByTagName("livraison"); 
  	        NodeList entrepot = doc.getElementsByTagName("entrepot"); 
  	        
  	        ArrayList<Delivery> listDelivery = new ArrayList<Delivery>();
  	        Long id;
  	        float latitude;
  	        float longitude;
  	        Date timeArrival;
  	        Date timeStart;
  	        float duration; 
  	        Delivery delivery;
  	        Warehouse warehouse;
  	        
  	        final Element nodeE = (Element) entrepot.item(0);
  	        String dateXML = nodeE.getAttribute("heureDepart");
  	        SimpleDateFormat dateFormat = new SimpleDateFormat("H:m:s");
  	        timeStart = dateFormat.parse(dateXML);
  	        id = Long.parseLong(nodeE.getAttribute("adresse"));
  	        
  	        warehouse = new Warehouse(id, timeStart);
  	        
  	        for (int i = 0; i<dList.getLength(); i++) {
  	            final Element nodeD = (Element) dList.item(i);
  	            id = Long.parseLong(nodeD.getAttribute("adresse"));
  	            duration = Float.valueOf(nodeD.getAttribute("duree"));
  	            delivery = new Delivery(id, duration);
  	            listDelivery.add(delivery);
  	        }
  	        
  	        map.setListDelivery(listDelivery);
  	        map.setWarehouse(warehouse);
  	        
  	        return map;
  	        
  	        } catch (Exception e) {
  	            e.printStackTrace();
  	            System.out.println("caca");
  	            return null;
  	        }
  	}
  
}

