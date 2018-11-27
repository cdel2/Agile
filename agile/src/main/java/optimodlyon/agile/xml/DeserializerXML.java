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
import java.util.HashMap;

import optimodlyon.agile.models.CityMap;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.Segment;

public class DeserializerXML {

  public static CityMap deserializeMap() {
	
    try {

        File fXmlFile = new File("src/main/java/optimodlyon/agile/xml/petitPlan.xml");

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
  
}

