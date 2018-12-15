package optimodlyon.agile.models;

import optimodlyon.agile.util.Time;
import java.util.ArrayList;

public class Round {
    private Time startTime;
    private Time endTime;
    private ArrayList<Path> listPath;
    private Warehouse start;

    public Round(Warehouse w, Time st) {
        listPath = new ArrayList<Path>();
        startTime = new Time(st);
        endTime = new Time(st);
        start = w;
    }

    public Round(Round r) {
    	if(r==null) {
        	throw new IllegalArgumentException("null value");
    	}
        listPath = r.listPath;
        startTime = r.startTime;
        endTime = r.endTime;
        start =r.start;
    }

    public Round(ArrayList<Path> listPath, Warehouse start, Time st)
    {
        this.listPath = listPath;
        this.start = start;
        startTime= st;
        float totalDuration = 0;
        
        for (Path path : listPath) {
            totalDuration+=path.getDuration();
            totalDuration+=path.getArrival().getDuration();
        }
        
        endTime = new Time(startTime);
        endTime.addTime(totalDuration);
    }
    
    public Round() {
		// TODO Auto-generated constructor stub
	}

	public void remove(Path path) {
    	listPath.remove(path);
    	updateRoundTimes();
    }
    
    public void updateRoundTimes() {
    	Time currentTime = new Time(startTime.toString());
    	for(Path path : listPath) {
			path.setDepartureTime(currentTime);
			path.setSegmentsPassageTimes();
			currentTime.addTime(path.getDuration());
			currentTime.addTime(path.getArrival().getDuration());
			path.getArrival().setTimeArrival(currentTime);
		}
    	endTime=currentTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    /**
     * @return the start
     */
    public Warehouse getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Warehouse start) {
        this.start = start;
    }

    /**
     * @return the listPath
     */
    public ArrayList<Path> getListPath() {
        return listPath;
    }

    /**
     * @param listPath the listPath to set
     */
    public void setListPath(ArrayList<Path> listPath) {
        this.listPath = listPath;
    }

    /**
     * @return the endTime
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void addPath(Path aPath) {
        this.listPath.add(aPath);
        endTime.addTime(aPath.getDuration());
        endTime.addTime(aPath.getArrival().getDuration());
    }

    public float getTotalDuration() {
        float duration = 0;
        for(int i = 0; i < listPath.size(); i++)
        {
            duration = listPath.get(i).getDuration() + duration;
        }
        return duration;
    }

    public String toString() {
        return "duration : " + this.getTotalDuration() +"\n"+ this.listPath.toString();
    }
	
}
