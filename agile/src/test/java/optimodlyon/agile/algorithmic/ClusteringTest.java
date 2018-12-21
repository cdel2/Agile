package optimodlyon.agile.algorithmic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.text.ChangedCharSetException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.web.client.ExpectedCount;

import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.MapManagement;
import optimodlyon.agile.models.Warehouse;

public class ClusteringTest {
	Delivery delivery1;
	Delivery delivery2;
	Delivery delivery3;
	Delivery delivery4;
	Delivery delivery5;
	Delivery delivery6;
	List<Delivery> listDelivery;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MapManagement.getInstance().setWarehouse(new Warehouse((long)1,0,0,null));
		delivery1=new Delivery(null,0,(long)1,1,-1);
		delivery2=new Delivery(null,0,(long)2,2,1);
		delivery3=new Delivery(null,0,(long)4,1,10);
		delivery4=new Delivery(null,0,(long)3,1,1);
		delivery5=new Delivery(null,0,(long)5,-1,1);
		delivery6=new Delivery(null,0,(long)6,0,2);
		listDelivery = new ArrayList<Delivery>();
		listDelivery.add(delivery1);
		listDelivery.add(delivery2);
		listDelivery.add(delivery3);
		listDelivery.add(delivery4);
		listDelivery.add(delivery5);
		listDelivery.add(delivery6);
		MapManagement.getInstance().setListDelivery(listDelivery);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSortCluster() {
		Clustering clustering = new Clustering();
		List<Delivery> expecteds = new ArrayList<Delivery>();
		expecteds.add(delivery2);
		expecteds.add(delivery4);
		expecteds.add(delivery3);
		expecteds.add(delivery6);
		expecteds.add(delivery5);
		expecteds.add(delivery1);
		List<Delivery> results = clustering.sortCluster();
		assertEquals(expecteds, results);
	}

	@Test
	public final void testChangeStartingPoint() {
		Clustering clustering = new Clustering();
		List <Delivery> sorted = clustering.sortCluster();
		List<Delivery> results = clustering.changeStartingPoint(sorted, 2);
		List<Delivery> expecteds = new ArrayList<Delivery>();
		expecteds.add(delivery3);
		expecteds.add(delivery6);
		expecteds.add(delivery5);
		expecteds.add(delivery1);
		expecteds.add(delivery2);
		expecteds.add(delivery4);
		assertEquals(expecteds, results);
	}

	@Test
	public final void testChangeStart() {
		Clustering clustering = new Clustering();
		List<Delivery> results = clustering.changeStart(listDelivery, 2);
		List<Delivery> expecteds = new ArrayList<Delivery>();
		expecteds.add(listDelivery.get(2));
		expecteds.add(listDelivery.get(3));
		expecteds.add(listDelivery.get(4));
		expecteds.add(listDelivery.get(5));
		expecteds.add(listDelivery.get(0));
		expecteds.add(listDelivery.get(1));
		assertEquals(expecteds, results);
	}

	@Test
	public final void testDispatchCluster() {
		Clustering clustering = new Clustering();
		List<List<Delivery>> results = clustering.dispatchCluster(2);
		listDelivery = clustering.sortCluster();
		listDelivery = clustering.changeStartingPoint(listDelivery, 2);
		List <Delivery> del1 = new ArrayList<Delivery>();
		List <Delivery> del2 = new ArrayList<Delivery>();
		del1.add(listDelivery.get(0));
		del1.add(listDelivery.get(1));
		del1.add(listDelivery.get(2));
		del2.add(listDelivery.get(3));
		del2.add(listDelivery.get(4));
		del2.add(listDelivery.get(5));
		List<List<Delivery>> expecteds = new ArrayList<List<Delivery>>();
		expecteds.add(del1);
		expecteds.add(del2);
		assertEquals(expecteds, results);
	}

	@Test
	public final void testCreateIdArray() {
		Clustering clustering = new Clustering();
		List<Long> results = clustering.createIdArray(listDelivery);
		List<Long> expecteds = new ArrayList<Long>();
		expecteds.add((long)1);
		expecteds.add((long)2);
		expecteds.add((long)4);
		expecteds.add((long)3);
		expecteds.add((long)5);
		expecteds.add((long)6);
		assertEquals(expecteds, results);
	}
}
