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
	}

}
