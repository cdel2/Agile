package optimodlyon.agile.algorithmic;

public class Pair {
	Long idPredecessor;
	Float distFromSource;
	
	public Pair(Long anId, Float aDist) {
		idPredecessor = anId;
		distFromSource = aDist;
	}
	
	public Pair() {
		idPredecessor = (long)0;
		distFromSource = Float.MAX_VALUE;
	}

	public Long getIdPredecessor() {
		return idPredecessor;
	}

	public void setIdPredecessor(Long idPredecessor) {
		this.idPredecessor = idPredecessor;
	}

	public Float getDistFromSource() {
		return distFromSource;
	}

	public void setDistFromSource(Float distFromSource) {
		this.distFromSource = distFromSource;
	}
}

