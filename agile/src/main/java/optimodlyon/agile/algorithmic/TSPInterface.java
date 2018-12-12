package optimodlyon.agile.algorithmic;

import java.util.Map;
import java.util.TreeMap;

public interface TSPInterface {

	
	public void startTSP(int timeLimit, int nbIntersections, Map<Long, TreeMap<Long, Float>> graph);
	
}
