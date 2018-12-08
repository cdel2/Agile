package optimodlyon.agile.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import optimodlyon.agile.util.Time;

public class MapManagementTest {

	@Test
	public void testAssignRounds() {
		Time t = new Time(8,0,0);
		Time t1 = new Time(12,0,0);
		Time t2 = new Time(18,0,0);
		Warehouse wh = new Warehouse((long)0, t);
		Round r1 = new Round(wh, t );
		r1.setStartTime(t);
		r1.setEndTime(t1);
		Round r2 = new Round(wh, t1);
		r2.setStartTime(t1);
		r2.setEndTime(t2);
		List<Round> l1 = new ArrayList<Round>();
		l1.add(r1);
		l1.add(r2);
		
		Deliverer del1 = new Deliverer((long)0);
		Deliverer del2 = new Deliverer((long)1);
		Deliverer del3 = new Deliverer((long)2);
		
		Map<Long, Deliverer> ldel = new HashMap<Long, Deliverer>();
		ldel.put(del1.getId(), del1);
		ldel.put(del2.getId(), del2);
		ldel.put(del3.getId(), del3);
		MapManagement.getInstance().setListDeliverer(ldel);
		
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
		fail("Not yet implemented");
	}

	@Test
	public void testAddRoundToADeliverer() {
		Time t = new Time(8,0,0);
		Time t1 = new Time(12,0,0);
		Time t2 = new Time(18,0,0);
		Time t4 = new Time(20,0,0);
		Warehouse wh = new Warehouse((long)0, t);
		Round r1 = new Round(wh, t );
		r1.setStartTime(t);
		r1.setEndTime(t1);
		Round r2 = new Round(wh, t1);
		r2.setStartTime(t1);
		r2.setEndTime(t2);
		List<Round> l1 = new ArrayList<Round>();
		l1.add(r1);
		l1.add(r2);
		
		Deliverer del1 = new Deliverer((long)0);
		Deliverer del2 = new Deliverer((long)1);
		Map<Long, Deliverer> dl = new HashMap<Long,Deliverer>();
		dl.put(del1.getId(), del1);
		dl.put(del2.getId(), del2);
		
		MapManagement.getInstance().setListDeliverer(dl);
		MapManagement.getInstance().assignRounds(l1);
		
		/*
		 * Test: add a round to a deliverer of the list
		 */
		Round r3 = new Round(wh, t );
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

}
