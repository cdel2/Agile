package optimodlyon.agile.models;
import java.util.Date;
import java.util.ArrayList; 

public class Round {
	private Date startTime;
	private Date endTime;
	private ArrayList<Path> listPath;
	private Delivery start;

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @return the start
	 */
	public Delivery getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Delivery start) {
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
	
}
