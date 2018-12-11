package optimodlyon.agile.models;

import static org.junit.Assert.*;

import org.junit.Test;

import optimodlyon.agile.util.Time;

public class WarehouseTest {
	@Test
	public void testWarehouseLongFloatFloatTime() {
		long id=1;
		float latitude = 1;
		float longitude = 1;
		Time t = new Time(1,1,1);		
		
		Warehouse myWHS = new Warehouse(id, latitude, longitude, t);
		assertNotNull(myWHS);
		assertSame(myWHS.getId(),id);
		assertSame(myWHS.getTimeStart(),t);
		assertSame(myWHS.getLatitude(),latitude);
		assertSame(myWHS.getLongitude(),longitude);
	}

	@Test
	public void testWarehouseLongTime() {
		long id=1;
		Time t = new Time(1,1,1);		
		
		Warehouse myWHS = new Warehouse(id, t);
		assertNotNull(myWHS);
		assertSame(myWHS.getId(),id);
		assertSame(myWHS.getTimeStart(),t);
	}

	@Test
	public void testGetTimeStart() {
		long id=1;
		float latitude=-50;
		float longitude=50;
		Intersection myInter = new Intersection(id,latitude,longitude);
		assertEquals(myInter.getLatitude(),-50,0);
	}

	@Test
	public void testSetTimeStart() {
		Time t = new Time(1,1,1);		
		Time t2 = new Time(2,2,2);		
		Warehouse myWHS = new Warehouse((long)1, (float)-50, (float)50, new Time(1,1,1));
		assertEquals(myWHS.getTimeStart(),t);
		myWHS.setTimeStart(new Time(2,2,2));
		assertEquals(myWHS.getTimeStart(),t2);
	}

}
