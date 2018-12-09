package optimodlyon.agile.controller;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import optimodlyon.agile.models.Segment;
import optimodlyon.agile.states.CalculatedState;
import optimodlyon.agile.states.DefaultState;
import optimodlyon.agile.states.LoadedDeliveriesState;
import optimodlyon.agile.states.LoadedMapState;
import optimodlyon.agile.states.State;
import optimodlyon.agile.xml.DeserializerXML;

public class ControllerTest {
	@Autowired
	public State currentState;
	
	@Before 
	public void setCurrentState() {
		currentState = new DefaultState();
	}
	
	@Test
	public void testController() {
		DefaultState ds = new DefaultState();
		assertNotNull(ds);
		assertEquals(ds, currentState);
		
	}

	@Test
	public void testInitializeGraph() {
		//DefaultState ds = new DefaultState();
		//currentState.loadMap("src/main/java/optimodlyon/agile/files/petitPlan.xml");
		//ds.loadMap("src/main/java/optimodlyon/agile/files/petitPlan.xml");
		
		State ds = new LoadedMapState();
		currentState = new LoadedMapState();
		assertNotNull(ds);
		assertEquals(ds, currentState);
		
	}

	@Test
	public void testGetDeliveries() {
		State ds = new LoadedDeliveriesState();
		currentState = new LoadedDeliveriesState();
		assertNotNull(ds);
		assertEquals(ds, currentState);
	}

	@Test
	public void testDoAlgorithm() {
		State ds = new CalculatedState();
		currentState = new CalculatedState();
		assertNotNull(ds);
		assertEquals(ds, currentState);
	}

	@Test
	public void testNewDelivery() {
		fail("Not yet implemented");
	}

}
