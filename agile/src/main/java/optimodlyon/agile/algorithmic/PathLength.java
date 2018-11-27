package optimodlyon.agile.algorithmic;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.introspect.WithMember;

public class PathLength {
	ArrayList<Long> path;
	Float length;
	
	public PathLength(ArrayList<Long> aPath, Float aLength) {
		path = aPath;
		length = aLength;
	}

	public ArrayList<Long> getPath() {
		return path;
	}

	public void setPath(ArrayList<Long> aPath) {
		this.path = aPath;
	}

	public Float getLength() {
		return length;
	}

	public void setLength(Float aLength) {
		this.length = aLength;
	}
	
	public String toString() {
		String string = "(Path:" + path.toString() + ",Length:" + length + ")";
		return string;
	}
}


