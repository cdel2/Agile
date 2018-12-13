package optimodlyon.agile.states;

public interface State {
	public void loadMap(String file);
	
	public void loadDeliveries(String file) throws Exception;
	
	public void startCalculation(int nb) throws Exception;
	
	public void addDelivery(Long idDelivery, int duration, int counter) throws Exception;

	public void undo(int counter) throws Exception;
    
    public void redo(int counter) throws Exception;
	
    public boolean stopCalculation() throws Exception;

	public void removeDelivery(Long idDelivery, boolean calc) throws Exception;
}