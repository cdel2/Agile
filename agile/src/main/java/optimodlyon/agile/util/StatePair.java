package optimodlyon.agile.util;

import java.util.List;
import java.util.Map;

import optimodlyon.agile.models.Delivery;
import optimodlyon.agile.models.Deliverer;

public class StatePair implements Pair<List<Delivery>, Map<Long,Deliverer>> {
	List<Delivery> list;
	Map<Long,Deliverer> map;
	
	public StatePair(List<Delivery> list, Map<Long,Deliverer> map) {
		this.list = list;
		this.map = map;
	}
	
	@Override
	public List<Delivery> getKey() {
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public Map<Long,Deliverer> getValue() {
		// TODO Auto-generated method stub
		return map;
	}

	@Override
	public void setKey(List<Delivery> list) {
		// TODO Auto-generated method stub
		this.list = list;
	}

	@Override
	public void setValue(Map<Long,Deliverer> map) {
		// TODO Auto-generated method stub
		this.map = map;
	}
	
	public String toString() {
		return "Pair []";
	}
}
