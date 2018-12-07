package optimodlyon.agile.states;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import optimodlyon.agile.models.Deliverer;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Round;
import optimodlyon.agile.models.Warehouse;
import optimodlyon.agile.util.Time;

public class CalculatedStateTest {

	@Test
	public void testAddDelivery() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindBestDeliverer() {
		CalculatedState cs = new CalculatedState();
		/*
		 * Test: find Best deliverer with a null deliverer map
		 */
		Map<Long, Deliverer> m = new HashMap<Long,Deliverer>();
		Deliverer d1 = cs.findBestDeliverer(m);
		assertEquals(d1, null);
		/*
		 * Test: find best deliverer with no deliverers
		 */
		Deliverer d2 = cs.findBestDeliverer(MapManagement.getInstance().getListDeliverer());
		assertEquals(d2, null);
		
		
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
		
		/*
		 * Test: find best deliverer with 2 different deliverers with different round Endtime
		 */
		Deliverer d3 = cs.findBestDeliverer(MapManagement.getInstance().getListDeliverer());
		assertEquals(d3,del1);
	}

	@Test
	public void testCalculateRoundForOneNode() {
		fail("Not yet implemented");
	}

}
