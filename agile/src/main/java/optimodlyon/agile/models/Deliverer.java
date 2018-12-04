package optimodlyon.agile.models;

import java.util.List;
import java.util.ArrayList;

public class Deliverer {
	private Long id;
	private List<Round> listRound;
	
	public Deliverer(Long id)
	{
		this.id = id;
		this.listRound = new ArrayList<Round>();
	}
	
	public List<Round> getListRound() {
		return listRound;
	}
	public void setListRound(List<Round> listRound) {
		this.listRound = listRound;
	}
}
