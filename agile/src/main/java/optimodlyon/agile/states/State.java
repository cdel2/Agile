package optimodlyon.agile.states;

import optimodlyon.agile.models.Delivery;

public interface State {
	public void loadMap(String file);
	
	public void loadDeliveries(String file) throws Exception;
	
	public void startCalculation(int nb) throws Exception;
	
	public void addDelivery(Long idDelivery, int duration) throws Exception;
	
	public void rmvDelivery(Long idDelivery) throws Exception;
        
    public boolean stopCalculation();
    
	public void undo(int counter) throws Exception;
}
