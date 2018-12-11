package optimodlyon.agile.models;

import java.util.List;

import optimodlyon.agile.util.Time;

import java.util.ArrayList;

public class Deliverer {
    private Long id;
    private List<Round> listRound;

    public Deliverer(Long id) {
        this.id = id;
        this.listRound = new ArrayList<Round>();
    }

    public List<Round> getListRound() {
        return listRound;
    }
    
    public void setListRound(List<Round> listRound) {
        this.listRound = listRound;
    }
    
    public void updateRounds(int i) {
    	if(i==0) {
    		listRound.get(0).setStartTime(new Time("8:00:00"));
    		listRound.get(0).updateRoundTimes();
    		i++;
    	}
    	while(i<listRound.size()) {
    		listRound.get(i).setStartTime(listRound.get(i-1).getEndTime());
    		listRound.get(i).updateRoundTimes();
            i++;
    	}
    }
    
    public void changeRound(int i, Round newRound) {
    	System.out.println("            i : " + i + listRound);
    	if(i<listRound.size()) listRound.set(i, newRound);
    	else System.out.println("problem in changeRound : the list is too small");
    }

    public Long getId() {
        return id;
    }
    /**
     * Add a round to the deliverer's list of round if the startTime of the 
     * round to add is after the endTime of the deliverer's last Round
     * @param roundToAdd
     */
    public boolean addRoundToList(Round roundToAdd) {
        if(roundToAdd != null) {
            if(listRound.size() - 1 >= 0) {
            	System.out.println("When we want to add, deliverer has a delivery");
            	//Shifted time to avoid equality
            	Time shiftedTime = new Time(0,0,1);
            	shiftedTime.addTime(roundToAdd.getEndTime());
                if(listRound.get(listRound.size() - 1).getEndTime().isBefore(shiftedTime)) {
                    System.out.println("The last round of the deliverer finishes before the startTime of the round to add");
                	this.listRound.add(roundToAdd);
                    return true;
                } else {
                	System.out.println("last round of the deliverer finishes at :" + listRound.get(listRound.size() - 1).getEndTime().toString() + " and the delivery to add : " + roundToAdd.getStartTime().toString());
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
    
    public boolean removeLastRound() {
    	boolean isRemoved = false;
    	if(this.listRound.size() > 0) {
    		listRound.remove(listRound.size() -1);
    		isRemoved = true;
    	}
    	return isRemoved;
    }
    public String toString() {
    	return listRound.toString();
    }
}
