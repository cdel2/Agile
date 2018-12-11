package optimodlyon.agile.algorithmic;

import java.util.List;
import java.util.Map;

public interface TSPInterface {

	
	public void startTSP(int timeLimit, int nbIntersections, Map<Long, Map<Long, Float>> graph);
	
}
