package optimodlyon.agile.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import optimodlyon.agile.util.Time;

public class DelivererTest {

	@Test
	public void testGetListRound() {
		Time t = new Time(8,0,0);
		Time t1 = new Time(12,0,0);
		Time t2 = new Time(18,0,0);
		Warehouse wh = new Warehouse((long)0, t);
		Round r1 = new Round(wh );
		Round r2 = new Round(wh);
		Round r3 = new Round(wh);
		/*
		 * Test: empty Round list
		 */
		Deliverer del = new Deliverer((long)1);
		Assertions.assertThat(del.getListRound()).isEmpty();
		/*
		 * Test: 2 rounds list
		 */
		List<Round> l1 = new ArrayList<Round>();
		l1.add(r1);
		l1.add(r2);
		del.setListRound(l1);
		Assertions.assertThat(del.getListRound()).containsAll(l1);
	}

	@Test
	public void testSetListRound() {
		Time t = new Time(8,0,0);
		Time t1 = new Time(12,0,0);
		Time t2 = new Time(18,0,0);
		Warehouse wh = new Warehouse((long)0, t);
		Round r1 = new Round(wh);
		Round r2 = new Round(wh);
		Round r3 = new Round(wh);
		/*
		 * Test: empty Round list
		 */
		Deliverer del = new Deliverer((long)1);
		List<Round> l1 = new ArrayList<Round>();
		del.setListRound(l1);
		Assertions.assertThat(del.getListRound()).isEmpty();
		/*
		 * Test: 2 rounds list
		 */
		l1.add(r1);
		l1.add(r2);
		del.setListRound(l1);
		Assertions.assertThat(del.getListRound()).containsAll(l1);
	}

	@Test
	public void testGetId() {
		/*
		 * Test: long id
		 */
		Deliverer del = new Deliverer((long)1);
		assertEquals(del.getId(),(long)1,0);
		long lmax = Long.MAX_VALUE;
		long lmin = Long.MIN_VALUE;
		/*
		 * Test: max long ID
		 */
		Deliverer delmax = new Deliverer(lmax);
		assertEquals(delmax.getId(),Long.MAX_VALUE,0);
		/*
		 * Test: min long ID
		 */
		Deliverer delmin = new Deliverer(lmin);
		assertEquals(delmin.getId(),Long.MIN_VALUE,0);
		
	}

	@Test
	public void testAddRoundToList() {
		Time t = new Time(8,0,0);
		Time t1 = new Time(12,0,0);
		Time t2 = new Time(18,0,0);
		Warehouse wh = new Warehouse((long)0, t);
		Round r1 = new Round(wh );
		r1.setStartTime(t);
		r1.setEndTime(t1);
		Round r2 = new Round(wh);
		r2.setStartTime(t1);
		r2.setEndTime(t2);
		
		/*
		 * Test: add a first round (the deliverer has no round yet)
		 */
		Deliverer del = new Deliverer((long)1);
		boolean b1 = del.addRoundToList(r1);
		List<Round> l1 = new ArrayList<Round>();
		l1.add(r1);
		Assertions.assertThat(del.getListRound()).contains(r1);
		assertEquals(b1,true);
		/*
		 * Test: add a new Round to the list, the startTime of the new round
		 * is after the end time of the previous round
		 */
		boolean b2 = del.addRoundToList(r2);
		Assertions.assertThat(del.getListRound()).contains(r1);
		Assertions.assertThat(del.getListRound()).contains(r2);
		assertEquals(b2,true);
		/*
		 * Test: add a new round to the list, the startTime of the new Round
		 * is before the end time of the previous round
		 */
		boolean b3 = del.addRoundToList(r1);
		assertEquals(b3,false);
		/*
		 * Test: add a null round
		 */
		boolean b4 = del.addRoundToList(null);
		assertEquals(b4,false);
	}
	
	@Test
	public void testUpdateRounds() {
		fail("Not Implemented yet");
	}
	
	@Test
	public void TestToString() {
		Deliverer del = new Deliverer((long)1);
		assertEquals("id : 1 rounds : []",del.toString());
	}
	
	@Test
	public void TestremoveLastRound() {
		Time t = new Time(8,0,0);
		Time t1 = new Time(12,0,0);
		Time t2 = new Time(18,0,0);
		Warehouse wh = new Warehouse((long)0, t);
		Round r1 = new Round(wh);
		Round r2 = new Round(wh);
		Round r3 = new Round(wh);
		
		Deliverer del = new Deliverer((long)1);
		Assertions.assertThat(del.getListRound()).isEmpty();
		del.removeLastRound();
		Assertions.assertThat(del.getListRound()).isEmpty();
		List<Round> l1 = new ArrayList<Round>();
		l1.add(r1);
		l1.add(r2);
		del.setListRound(l1);
		Assertions.assertThat(del.getListRound()).containsAll(l1);
		del.removeLastRound();
		List<Round> lTest = new ArrayList<Round>();
		lTest.add(r1);
		Assertions.assertThat(del.getListRound()).containsAll(lTest);	
		}
}
