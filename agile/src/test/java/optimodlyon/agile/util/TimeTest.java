package optimodlyon.agile.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeTest {

	@Test
	public void testAddTime() {
		Time t1 = new Time(18,0,0);
		Time t2 = new Time(23,59,0);
		Time t3 = new Time(0,0,100);
		t1.addTime((float)0);
		assertEquals(t1.getHours(),18,0);assertEquals(t1.getMinutes(),0,0);assertEquals(t1.getSeconds(),0,0);
		t1.addTime((float)10);
		assertEquals(t1.getHours(),18,0);assertEquals(t1.getMinutes(),0,0);assertEquals(t1.getSeconds(),10,0);
		t1.addTime((float)100);
		assertEquals(t1.getHours(),18,0);assertEquals(t1.getMinutes(),1,0);assertEquals(t1.getSeconds(),50,0);
		t1.addTime((float)3600);
		assertEquals(t1.getHours(),19,0);assertEquals(t1.getMinutes(),1,0);assertEquals(t1.getSeconds(),50,0);
		t2.addTime((float)120);
		assertEquals(t2.getHours(),0,0);assertEquals(t2.getMinutes(),1,0);assertEquals(t2.getSeconds(),0,0);
		t3.addTime((float)1);
		assertEquals(t3.getHours(),0,0);assertEquals(t3.getMinutes(),1,0);assertEquals(t3.getSeconds(),41,0);
		t3.addTime((float)10.5);
		assertEquals(t3.getHours(),0,0);assertEquals(t3.getMinutes(),1,0);assertEquals(t3.getSeconds(),51,0);
		t3.addTime((float)36050.7);
		assertEquals(t3.getHours(),10,0);assertEquals(t3.getMinutes(),2,0);assertEquals(t3.getSeconds(),42,0);
	}

	@Test
	public void testUpdateTime() {
		Time t1 = new Time(18,0,0);
		Time t2 = new Time(23,59,0);
		Time t3 = new Time(0,0,100);
		t1.addTime((float)0);
		assertEquals(t1.getHours(),18,0);assertEquals(t1.getMinutes(),0,0);assertEquals(t1.getSeconds(),0,0);
		t1.addTime((float)10);
		assertEquals(t1.getHours(),18,0);assertEquals(t1.getMinutes(),0,0);assertEquals(t1.getSeconds(),10,0);
		t1.addTime((float)100);
		assertEquals(t1.getHours(),18,0);assertEquals(t1.getMinutes(),1,0);assertEquals(t1.getSeconds(),50,0);
		t1.addTime((float)3600);
		assertEquals(t1.getHours(),19,0);assertEquals(t1.getMinutes(),1,0);assertEquals(t1.getSeconds(),50,0);
		t2.addTime((float)120);
		assertEquals(t2.getHours(),0,0);assertEquals(t2.getMinutes(),1,0);assertEquals(t2.getSeconds(),0,0);
		t3.addTime((float)1);
		assertEquals(t3.getHours(),0,0);assertEquals(t3.getMinutes(),1,0);assertEquals(t3.getSeconds(),41,0);
		t3.addTime((float)10.5);
		assertEquals(t3.getHours(),0,0);assertEquals(t3.getMinutes(),1,0);assertEquals(t3.getSeconds(),51,0);
	}

	@Test
	public void testIsBefore() {
		Time t1 = new Time(18,0,0);
		Time t2 = new Time(23,59,0);
		Time t3 = new Time(0,0,100);
		Time t4 = new Time(0,0,0);
		Time t5 = new Time(0,0,0);
		Time t6 = new Time(18,1,0);
		Time t7 = new Time(18,1,1);
		assertEquals(t1.isBefore(t2),true);
		assertEquals(t1.isBefore(t3),false);
		assertEquals(t1.isBefore(t5),false);
		assertEquals(t1.isBefore(t6),true);
		assertEquals(t1.isBefore(t7),true);
		assertEquals(t7.isBefore(t1),false);
		assertEquals(t2.isBefore(t1),false);
		assertEquals(t2.isBefore(t3),false);
		assertEquals(t4.isBefore(t4),false);
		assertEquals(t6.isBefore(t7),true);
		assertEquals(t7.isBefore(t6),false);
		
	}

}
