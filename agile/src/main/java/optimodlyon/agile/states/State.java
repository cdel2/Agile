package optimodlyon.agile.states;

public interface State {
	public void loadMap(String file);
	
	public void loadDeliveries(String file) throws Exception;
	
	public void startCalculation(int nb) throws Exception;
	
	public void addDelivery(Long idDelivery, int duration) throws Exception;
	
	public void rmvDelivery(Long idDelivery) throws Exception;

    
	public void undo(int counter) throws Exception;

	public void removeDeliveryAndCalc(Long idDelivery) throws Exception;

	public void removeDeliveryWithoutCalc(Long idDelivery) throws Exception;
	
    public boolean stopCalculation() throws Exception;
    
    public void redo(int counter) throws Exception;
}
