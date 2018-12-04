package optimodlyon.agile.models;

import optimodlyon.agile.xml.DeserializerXML;

import java.util.ArrayList;
import java.util.Date;

import optimodlyon.agile.models.*;


public class MapManagment{
    public static void main(String [] args)
    {
        System.out.println("hello");
        long id = 25321687;
        Date date = new Date();
        		
        Warehouse w = new Warehouse(id,date);
        
        //System.out.println(w.duration);
        //CityMap map = DeserializerXML.deserializeMap("petit");

        
        float latitude=(float) 45.75154;
        float longitude=(float) 4.87438;
        //Intersection start=new Intersection(id, latitude, longitude);
        //ArrayList<Intersection> endInt = Intersection.FindSuccessorSegments(start,map);
    }
}

