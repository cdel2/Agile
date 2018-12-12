package optimodlyon.agile.algorithmic;

import java.util.*;

public class IteratorSeq implements Iterator<Long>{
	private Long[] collection;
	private int nbElement;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param nonVus
	 * @param sommetCrt
	 */
	public IteratorSeq(Collection<Long> notVisited, long current){
		this.collection = new Long[notVisited.size()];
		nbElement = 0;
		for (Long s : notVisited){
			collection[nbElement++] = s;
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbElement > 0;
	}

	@Override
	public Long next() {
		return collection[--nbElement];
	}

	@Override
	public void remove() {}
	
	public String toString()
	{
		String s ="nb : "+ nbElement + "\n";
		for(int i=0; i<collection.length; i++)
		{
			s = s + " - " + collection[i];
		}
		
		return s;
	}
}
