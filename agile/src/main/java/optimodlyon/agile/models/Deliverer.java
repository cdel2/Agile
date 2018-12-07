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

	public Long getId() {
		return id;
	}
	/**
	 * Add a round to the list of round f the deliverer if the startTime of the 
	 * round to add is after the endTime of the last Round of the deliverer
	 * @param roundToAdd
	 */
	public boolean addRoundToList(Round roundToAdd) {
		if(roundToAdd != null) {
			if(listRound.size() - 1 >= 0) {
				if(listRound.get(listRound.size() - 1).getEndTime().isBefore(roundToAdd.getEndTime())) {
					this.listRound.add(roundToAdd);
					return true;
				} else {
					return false;
				}
			} else {
				this.listRound.add(roundToAdd);
				return true;
			}
			
		} else {
			return false;
		}
	}
}
