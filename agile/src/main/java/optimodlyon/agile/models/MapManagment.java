package optimodlyon.agile.models;

import optimodlyon.agile.xml.DeserializerXML;

import java.util.ArrayList;

import optimodlyon.agile.models.*;


public class MapManagment{
    public static void main(String [] args)
    {
        System.out.println("hello");
        CityMap map = DeserializerXML.deserialize();
        long id = 25321687;
        float latitude=(float) 45.75154;
        float longitude=(float) 4.87438;
        Intersection start=new Intersection(id, latitude, longitude);
        ArrayList<Intersection> endInt = Intersection.FindSuccessorSegments(start,map);
    }
}

