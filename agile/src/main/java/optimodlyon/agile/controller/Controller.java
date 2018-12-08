package optimodlyon.agile.controller;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import optimodlyon.agile.states.CalculatedState;
import optimodlyon.agile.states.CalculatingState;
import optimodlyon.agile.states.DefaultState;
import optimodlyon.agile.states.LoadedMapState;
import optimodlyon.agile.states.LoadedDeliveriesState;
import optimodlyon.agile.states.State;

public class Controller {
	public State currentState;
	
	public Controller() {
            currentState = new DefaultState();
	}
	
	public void initializeGraph(String file) {
            try {
                currentState.loadMap(file);
                currentState = new LoadedMapState();
            } catch(Exception e) {
                System.out.println("Erreur lors de InitializeGraph : " + e);
            }				
	}

	public void getDeliveries(String file) throws Exception{
            currentState.loadDeliveries(file);
            currentState = new LoadedDeliveriesState();	
	}
	
	public void doAlgorithm(int nb) throws Exception {
            /*
            //executorService manages an in-memory queue and schedules submitted tasks based on thread availability.
            //newCachedThreadPool Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available. 
            ExecutorService executorService = Executors.newCachedThreadPool();

            //submit a task (startCalculation)
            Future<Boolean> future = executorService.submit(() -> {
                currentState = new CalculatingState();
                System.out.println("je commence le calcul");
                currentState.startCalculation(nb);
                System.out.println("je finis le calcul");
                System.out.println("j'ai crée l'état calculating");
                //Thread.sleep(10000);
                return Boolean.TRUE;
            });


            if (future.isDone() && !future.isCancelled()) {
                try {
                        //specify a timeout for the operation. If the task takes more than this time, a TimeoutException is thrown
                    future.get(10, TimeUnit.SECONDS);
                    currentState = new CalculatedState();
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                }
            }*/
            /*
            Boolean result = future.get(10, TimeUnit.SECONDS);

            if(result) {
                System.out.println("j'ai crée l'état calculated");
                currentState = new CalculatedState();
            }
            else {
                System.out.println("calcul trop long!");
            }*/
                
            currentState.startCalculation(nb);
            currentState = new CalculatedState();	
	}
	
	/**
	 * method used to add a Delivery and calculate the best path (and best deliverer) 
	 * to deliver this point 
	 * @param idNewNode
	 */
	public void newDelivery(Long idDelivery) throws Exception {
            currentState.addDelivery(idDelivery);
	}
}
