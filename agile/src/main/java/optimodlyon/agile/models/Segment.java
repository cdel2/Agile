package optimodlyon.agile.models;

import optimodlyon.agile.util.Time;

public class Segment {
    private Intersection start;
    private Intersection end;
    private float duration;
    private Time passageTime;

    public Segment(Intersection start, Intersection end, float duration) {
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

    public Segment(Segment seg) {
        this.start = seg.start;
        this.end = seg.end;
        this.duration = seg.duration;
        this.passageTime=seg.passageTime;
    }

    public Intersection getStart() 
    {
        return start;
    }

    /**
     * @return the duration
     */
    public float getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Intersection getEnd()
    {
        return end;
    }

    public Time getPassageTime() {
        return passageTime;
    }

    public void setPassageTime(Time passageTime) {
        this.passageTime = passageTime;
    }

    public String toString() {
        return "start : " + start + " end : " + end + " duration : " + duration;
    }	
}
