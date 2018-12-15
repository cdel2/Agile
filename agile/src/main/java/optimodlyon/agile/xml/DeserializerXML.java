package optimodlyon.agile.xml;
import optimodlyon.agile.models.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import optimodlyon.agile.util.Time;
import optimodlyon.agile.models.Intersection;
import optimodlyon.agile.models.Segment;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class DeserializerXML {
    
	public static boolean validateSchema(File  fXmlFile, File fXsdFile) {
	        try {
	            SchemaFactory factory = 
	                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	            Schema schema = factory.newSchema(fXsdFile);
	            Validator validator = schema.newValidator();
                    
                    final List<SAXParseException> exceptions = new LinkedList<>();
                    validator.setErrorHandler(new ErrorHandler()
                    {
                      @Override
                      public void warning(SAXParseException exception) throws SAXException
                      {
                        exceptions.add(exception);
                      }

                      @Override
                      public void fatalError(SAXParseException exception) throws SAXException
                      {
                        exceptions.add(exception);
                      }

                      @Override
                      public void error(SAXParseException exception) throws SAXException
                      {
                        exceptions.add(exception);
                      }
                    });
	            validator.validate(new StreamSource(fXmlFile));
                    
	        } catch (IOException | SAXException e) {
	            System.out.println("Exception: "+e.getMessage());
	            return false;
	        } 
	        return true;
	}

  public static Map<Long, List<Segment>> deserializeMap(String type) throws Exception {
	
    try {

        File fXmlFile = new File("src/main/java/optimodlyon/agile/files/" + type + "Plan.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        
        File fXsdFile = new File("src/main/java/optimodlyon/agile/files/validator/planValidator.xsd");
        
        if(validateSchema(fXmlFile, fXsdFile)) {
		    NodeList nList = doc.getElementsByTagName("noeud"); 
		    
		    HashMap<Long, Intersection> intersections = new HashMap<>(); 
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
		    
		    Map<Long, List<Segment>> graph = new HashMap<>();
		    
		    for (int i = 0; i<nList.getLength(); i++) {
		    	final Element node = (Element) nList.item(i);
		    	idOrigin = Long.valueOf(node.getAttribute("origine"));
		        idDestination = Long.valueOf(node.getAttribute("destination"));
		        float speed = 150/36;
		        duration = (Float.parseFloat(node.getAttribute("longueur")))/(speed);
		        
		        originInter = intersections.get(idOrigin);
		        destInter = intersections.get(idDestination);
		        
		        if(originInter != null && destInter != null) {
		        	seg = new Segment(originInter,destInter,duration);
		        	if (graph.get(originInter.getId()) != null) {
		        		graph.get(originInter.getId()).add(seg);
		        	}
		        	else {
		        		ArrayList <Segment> segments = new ArrayList<>();
		        		segments.add(seg);
		        		graph.put(originInter.getId(), segments);
		        	}
		        } else {
		        	throw new Exception();
		        }
		    }  
		    return graph;      
        }
        else {
            
        }
	    
    } catch (FileNotFoundException e) {
        throw e;
    }
            return null;
}
  
  public static ArrayList<Delivery> deserializeDeliveries(String file) throws Exception {
		try {

	        File fXmlFile = new File("src/main/java/optimodlyon/agile/files/"+file+".xml");

	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(fXmlFile);
	        doc.getDocumentElement().normalize();
	        NodeList dList = doc.getElementsByTagName("livraison");   
	        
	        File fXsdFile = new File("src/main/java/optimodlyon/agile/files/validator/dlValidator.xsd");	       
	        
	        if(validateSchema(fXmlFile, fXsdFile)) {
		        
		        ArrayList<Delivery> listDelivery = new ArrayList<>();
		        Long id;
		        float duration; 
		        Delivery delivery;
	
		        
		        for (int i = 0; i<dList.getLength(); i++) {
		            final Element nodeD = (Element) dList.item(i);
		            id = Long.parseLong(nodeD.getAttribute("adresse"));
		            duration = Float.valueOf(nodeD.getAttribute("duree"));
		            delivery = new Delivery(id, duration);
                            
                            //we check if current delivery's latitude and longitude exist in graph
		            if(delivery.findLatitudeLongitude(MapManagement.getInstance().getMap().getGraph()))
		            {
		  	            listDelivery.add(delivery);
		            } 
		        }
		        
		        return listDelivery;
	        }
	        
	        return null; 
	        } catch (FileNotFoundException e) {
                    throw e;
	        }
		
		
	}

  	
  	public static Warehouse deserializeWarehouse(String file) throws Exception {
  		try {

  	        File fXmlFile = new File("src/main/java/optimodlyon/agile/files/"+file+".xml");

  	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
  	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
  	        Document doc = dBuilder.parse(fXmlFile);
  	        doc.getDocumentElement().normalize();
  	      
        	File fXsdFile = new File("src/main/java/optimodlyon/agile/files/validator/dlValidator.xsd");	       
	        
  	        if(validateSchema(fXmlFile, fXsdFile)) { 
	  	        NodeList entrepot = doc.getElementsByTagName("entrepot"); 
	  	        Long id;
	  	        Time timeStart;
	  	        Warehouse warehouse;
	  	        
	  	        final Element nodeE = (Element) entrepot.item(0);
	  	        String timeXML = nodeE.getAttribute("heureDepart");
	  	        timeStart = new Time(timeXML);
	  	        id = Long.parseLong(nodeE.getAttribute("adresse"));
	  	        
	  	        warehouse = new Warehouse(id, timeStart);
	  	        warehouse.findLatitudeLongitude(MapManagement.getInstance().getMap().getGraph());
	  	        return warehouse;
	  	    }
	  	    
  	        
  	        } catch (FileNotFoundException e) {
  	            throw e;
  	        }
  	        return null;
  		
  		
  	}
  	
  	
  	
}

