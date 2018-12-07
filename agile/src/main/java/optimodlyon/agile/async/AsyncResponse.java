package optimodlyon.agile.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncResponse<V> implements Future<V> {
	private V value;
	private Exception executionException;
	private boolean isCompleteExceptionnaly;
	private boolean isCancelled;
	private boolean isDone;
	private long checkCompletedInterval = 5000;

	@Override
	public boolean cancel(boolean arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V get(long arg0, TimeUnit arg1) throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean complete(V val) {
		this.value = val;
		this.isDone = true;
		
		return true;
	}
	
	public boolean completeExceptionnally(Throwable ex) {
		this.value = null;
		this.executionException = new ExecutionException(ex);
		this.isCompleteExceptionnaly = true;
		this.isDone = true;
		
		return true;
	}
	
	public void setCheckedCompletedInterval(long millis) {
		this.checkCompletedInterval = millis;
	}
	
	private void block(long timeout) throws InterruptedException {
		long start = System.currentTimeMillis();
		
		while(!isCancelled && !isDone) {
			if(timeout > 0) {
				
				
			}
		}
		
	}

}
