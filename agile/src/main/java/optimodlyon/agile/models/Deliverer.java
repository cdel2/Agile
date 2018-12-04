package optimodlyon.agile.models;

import java.util.ArrayList;

public class Deliverer {
	private Long id;
	private ArrayList<Round> listRound;
	public ArrayList<Round> getListRound() {
		return listRound;
	}
	public void setListRound(ArrayList<Round> listRound) {
		this.listRound = listRound;
	}
}
