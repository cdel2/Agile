package optimodlyon.agile.algorithmic;

import java.util.*;

public class IteratorSeq implements Iterator<Long>{
	private Long[] collection;
	private int nbElement;

	/**
	 * Creates an iterator to iterate over a collection of Deliveries' ids
	 * @param noVisited the collection of ids of the delivery that has not been visited yet
	 * @param current the id of the delivery that is being visited
	 */
	public IteratorSeq(Collection<Long> notVisited, long current){
		this.setCollection(new Long[notVisited.size()]);
		nbElement = 0;
		for (Long s : notVisited){
			getCollection()[nbElement++] = s;
		}
	}
	
	/*
	 * @return true if there are still elements to iterate over
	 */
	@Override
	public boolean hasNext() {
		return nbElement > 0;
	}
	
	
	/**
	 * @return the id of the next delivery in the collection of ids
	 */
	@Override
	public Long next() {
		return getCollection()[--nbElement];
	}

	@Override
	public void remove() {}

	public Long[] getCollection() {
		return collection;
	}

	public void setCollection(Long[] collection) {
		this.collection = collection;
	}
	

}
