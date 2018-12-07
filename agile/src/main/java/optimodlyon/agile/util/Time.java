package optimodlyon.agile.util;

public class Time {
	int hours;
	int minutes;
	int seconds;

	
	/*
	 * constructor of time from int hours, int minutes and int seconds
	 */
	public Time(int h, int m, int s) {
		hours=h;
		minutes=m;
		seconds=s;
		updateTime();
	}
	
	public Time(Time t) {
		hours=t.hours;
		minutes=t.minutes;
		seconds=t.seconds;
	}
	
	/*
	 * constructor of time from sting with format hh:mm:ss
	 */
	public Time(String s) {
		String[] t = s.split(":");
		hours=Integer.parseInt(t[0]);
		minutes=Integer.parseInt(t[1]);
		seconds=Integer.parseInt(t[2]);
		updateTime();
	}
	
	/*
	 * Adds s seconds to a time t
	 */
	public Time addTime(float s) {
		if(s>3600) {
			hours+=(int)s/3600;
			s=s%3600;
		}
		if(s>60) {
			minutes+=(int)s%3600;
		}
		seconds+=s;
		updateTime();
		return this;
	}
	
	/*
	 * If seconds>60 or minutes>60 or hours>24, readjust the time.
	 */
	public void updateTime() {
		if(seconds>60) {
			int m = (int)seconds/60;
			seconds=seconds%60;
			minutes+=m;
		}
		if(minutes>60) {
			int h = (int)minutes/60;
			minutes=minutes%60;
			hours+=h;
		}
		hours=hours%24;
	}
	
	/*
	 * Is it better to make a constructor of time with a time as parameters ?
	 */
	
	/*
	 * WTF : Si je ne mets pas les getters et les setters, j'ai l'erreur suivante
	 * 
	 * com.fasterxml.jackson.databind.exc.InvalidDefinitionException: 
	 * No serializer found for class optimodlyon.agile.util.DayTime and 
	 * no properties discovered to create BeanSerializer 
	 * (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) 
	 * (through reference chain: optimodlyon.agile.models.Warehouse["timeStart"])
	 */
	public int getHours() {
		return hours;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	public String toString() {
		return (hours + ":" + minutes + ":" + seconds);
	}
}
