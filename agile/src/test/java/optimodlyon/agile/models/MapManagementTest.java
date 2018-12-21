package optimodlyon.agile.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.assertj.core.api.Assertions;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Before;
import org.junit.Test;

import optimodlyon.agile.util.Time;

public class MapManagementTest {
	Time t, t1, t2;
	Warehouse wh;
	Round r1, r2;
	List<Round> l1;
	Deliverer del1, del2, del3;
	Delivery d1, d2;
	List<Delivery> listDelivery;
	Map<Long, Deliverer> ldel;
	
	@Before 
	public void setUp() {
		 t = new Time(8,0,0);
		t1 = new Time(12,0,0);
		t2 = new Time(18,0,0);
		wh = new Warehouse((long)0, t);
		r1 = new Round(wh, t );
		r1.setStartTime(t);
		r1.setEndTime(t1);
		r2 = new Round(wh, t);
		r2.setStartTime(t1);
		r2.setEndTime(t2);
		l1 = new ArrayList<Round>();
		l1.add(r1);
		l1.add(r2);
		
		del1 = new Deliverer((long)0);
		del2 = new Deliverer((long)1);
		del3 = new Deliverer((long)2);
		
		ldel = new HashMap<Long, Deliverer>();
		ldel.put(del1.getId(), del1);
		ldel.put(del2.getId(), del2);
		ldel.put(del3.getId(), del3);
		
		MapManagement.getInstance().setListDeliverer(ldel);
		
		d1 = new Delivery((long)1, 100);
		d2 = new Delivery((long)2, 100);
		
		listDelivery = new ArrayList<Delivery>();
		listDelivery.add(d1);
		listDelivery.add(d2);
		
		MapManagement.getInstance().setListDelivery(listDelivery);
		
		//undoList = new Stack<>();
		//redoList = new Stack<>();
	}

	@Test
	public void testAssignRounds() {		
		MapManagement.getInstance().assignRounds(l1);
		Assertions.assertThat(MapManagement.getInstance().getListDeliverer().get((long)0).getListRound()).contains(r1);
		Assertions.assertThat(MapManagement.getInstance().getListDeliverer().get((long)1).getListRound()).contains(r2);
		Assertions.assertThat(MapManagement.getInstance().getListDeliverer().get((long)2).getListRound()).isEmpty();
	}

	@Test
	public void testInitializeListDeliverer() {
		MapManagement.getInstance().initializeListDeliverer(6);
		assertEquals(MapManagement.getInstance().getListDeliverer().size(),6,0);
	}

	@Test
	public void testGetDeliveryById() {
		
	}

	@Test
	public void testAddRoundToADeliverer() {
		MapManagement.getInstance().assignRounds(l1);
		
		/*
		 * Test: add a round to a deliverer of the list
		 */
		Round r3 = new Round(wh, t1 );
		r3.setStartTime(t1);
		r3.setEndTime(t2);
		boolean b1 = MapManagement.getInstance().addRoundToADeliverer(del1, r3);
		assertEquals(b1,true);
		Assertions.assertThat(MapManagement.getInstance().getListDeliverer().get(del1.getId()).getListRound()).contains(r3);
		/*
		 * Test: add a null round
		 */
		Round r4 = null;
		boolean b2 = MapManagement.getInstance().addRoundToADeliverer(del2, r4);
		assertEquals(b2,false);
		/*
		 * Test: add to a null deliverer
		 */
		Deliverer del3 = null;
		boolean b3 = MapManagement.getInstance().addRoundToADeliverer(del3, r3);
		assertEquals(b3,false);
		/*
		 * Test: add to a deliverer that is not in the list
		 */
		Deliverer del4 = new Deliverer((long)5);
		boolean b4 = MapManagement.getInstance().addRoundToADeliverer(del4, r3);
		assertEquals(b4,false);
		//Assertions.assertThat(MapManagement.getInstance().getListDeliverer().get(del2.getId()).getListRound()).contains(r4);
	}
	
	@Test
	public void testAddDeliveryToListDelivery() {
		Delivery del = new Delivery((long)0,(float)5);
		assertEquals(MapManagement.getInstance().addDeliveryToListDelivery(del),true);
		assertEquals(MapManagement.getInstance().addDeliveryToListDelivery(del),false);
		
	}
	
	@Test
	public void testSetAndGetMap() {
		CityMap map = new CityMap();
		MapManagement.getInstance().setMap(map);
		assertEquals(MapManagement.getInstance().getMap(),map);
	}
	
	@Test
    public void testSetAndGetWarehouse() {
		Time t = new Time(8,0,0);
		Warehouse wh = new Warehouse((long)0, t);
		MapManagement.getInstance().setWarehouse(wh);
		assertEquals(MapManagement.getInstance().getWarehouse(),wh);	
    }

	@Test
    public void testSetAndGetListDeliverer() {
		Deliverer del1 = new Deliverer((long)0);
		
		Map<Long, Deliverer> ldel = new HashMap<Long, Deliverer>();		
		ldel.put(del1.getId(), del1);
		
		MapManagement.getInstance().setListDeliverer(ldel);
		
		assertEquals(MapManagement.getInstance().getListDeliverer(), ldel);
		
    }

	@Test
    public void testSetAndGetListDelivery() {
		Delivery del = new Delivery((long)0,(float)5);
		
		List<Delivery> listDel = new ArrayList<>();
		listDel.add(del);
		
		MapManagement.getInstance().setListDelivery(listDel);
		
		assertEquals(MapManagement.getInstance().getListDelivery(), listDel);

    }

	
	@Test
    public void testSetAndGetEndOfDay() {
	    Time endOfDay = new Time(18,0,1);
	    MapManagement.getInstance().setEndOfDay(endOfDay);
	    assertEquals(MapManagement.getInstance().getEndOfDay(), endOfDay);
	    
    }

	@Test
    public void testSetAndGetsetIsRunning() {
		MapManagement.getInstance().setIsRunning(true);
		
		boolean b = MapManagement.getInstance().getIsRunning();
		assertEquals(b, true);
    }

	@Test
    public void testGetIntersectionById() {
		CityMap map = new CityMap();
		
		long id = 1;
		Intersection origin = new Intersection(id, (float)-50, (float)50);
		Intersection destination = new Intersection((long)2, (float)50, (float)50);
		Segment mySeg1 = new Segment(origin,destination,100);
		
		List<Segment>  listSegments = new ArrayList<>();
		listSegments.add(mySeg1);
		
		Map<Long, List<Segment>>  graph = new HashMap<>();
		graph.put(id, listSegments);
		
		map.setGraph(graph);
		
		MapManagement.getInstance().setMap(map);
		
		assertEquals(MapManagement.getInstance().getIntersectionById(id), origin);
    }
	
	@Test
	public void testRemoveLastRoundFromADeliverer() {
		MapManagement.getInstance().assignRounds(l1);
		boolean b1 = MapManagement.getInstance().removeLastRoundFromADeliverer(del1);
		assertEquals(b1, true);
		
		boolean b2 = MapManagement.getInstance().removeLastRoundFromADeliverer(del1);
		assertEquals(b2, false);
		
	}
	
	@Test
	public void testRemoveDelivery() {
		long id = 1;
		boolean b1 = MapManagement.getInstance().removeDelivery(id);
		assertEquals(b1, true);
		
		id = 3;
		boolean b2 = MapManagement.getInstance().removeDelivery(id);
		assertEquals(b2, false);
	}
	
	@Test
	public void testAddMapToHistory() {
		MapManagement.getInstance().addMapToHistory();
		Map<Long, Deliverer> undoListMap = MapManagement.getInstance().getUndoList().get(0);
		Stack<Map<Long, Deliverer>> redoList = MapManagement.getInstance().getUndoList();
		
		Assertions.assertThat(undoListMap.containsKey((long)0));
		Assertions.assertThat(undoListMap.containsValue(del1));
		Assertions.assertThat(redoList.isEmpty());
	}
	
	@Test
	public void testUndo() {
		/*
		  try {
				MapManagement.getInstance().undo();
			    //fail("Should throw exception");
			  }catch(RuntimeException rExp){
			    assert(rExp.getMessage().contains("You cannot redo or undo."));
			  }
		*/
		
		MapManagement.getInstance().addMapToHistory();
		
		MapManagement.getInstance().undo();
		
	}
	
	@Test
	public void testRedo() {
		MapManagement.getInstance().addMapToHistory();
		
		MapManagement.getInstance().undo();
		MapManagement.getInstance().redo();
	}
	
	@Test
	public void isUndoableTest() {
		MapManagement.getInstance().clearHistory();
		
		boolean b = MapManagement.getInstance().isUndoable();
		assertEquals(b, false);
		
		MapManagement.getInstance().addMapToHistory();
		b = MapManagement.getInstance().isUndoable();
		assertEquals(b, true);
	}
	
	@Test
	public void isRedoableTest() {
		MapManagement.getInstance().clearHistory();
		
		MapManagement.getInstance().addMapToHistory();
		
		boolean b = MapManagement.getInstance().isRedoable();
		assertEquals(b, false);

		MapManagement.getInstance().undo();
		
		b = MapManagement.getInstance().isRedoable();
		assertEquals(b, true);
	}

	@Test
	public void testRemoveLastMapInHistory() {
		MapManagement.getInstance().clearHistory();
		
		MapManagement.getInstance().addMapToHistory();
		
		MapManagement.getInstance().removeLastMapInHistory();
		Assertions.assertThat(MapManagement.getInstance().getUndoList().isEmpty());
	}

}
