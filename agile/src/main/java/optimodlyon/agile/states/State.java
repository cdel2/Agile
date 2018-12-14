package optimodlyon.agile.states;

public interface State {
    public void loadMap(String file) throws Exception;

    public void loadDeliveries(String file) throws Exception;

    public void startCalculation(int nb) throws Exception;

    public void addDelivery(Long idDelivery, int duration) throws Exception;

    public void undo() throws Exception;
    
    public void redo() throws Exception;
	
    public boolean stopCalculation() throws Exception;

    public void removeDelivery(Long idDelivery, boolean calc) throws Exception;
    
    public void clearHistory();
}