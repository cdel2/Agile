package optimodlyon.agile.models;
import java.util.Date;
import java.util.ArrayList; 

public class Round {
	private Date startTime;
	private Date endTime;
	private float duration;
	private ArrayList<Path> listPath;
	private Warehouse start;
	
	public Round() {
		listPath = new ArrayList<Path>();
		duration=0;
	}

	/**
	 * @return the startTime
	 */
	public Round(ArrayList<Path> listPath, Warehouse start)
	{
		this.listPath = listPath;
		this.start = start;
	}
	
	public Date getStartTime() {
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
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public void addPath(Path aPath) {
		this.listPath.add(aPath);
		this.duration+=aPath.getDuration();
		this.duration+=aPath.getArrival().getDuration();
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
	
	public float getDuration() {
		return this.duration;
	}
	
	public void setDuration(float d) {
		duration = d;
	}
	
}
