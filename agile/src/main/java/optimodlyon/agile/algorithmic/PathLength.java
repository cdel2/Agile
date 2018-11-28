package optimodlyon.agile.algorithmic;

import java.util.List;

import com.fasterxml.jackson.databind.introspect.WithMember;

public class PathLength {
	List<Long> path;
	Float length;
	
	public PathLength(List<Long> aPath, Float aLength) {
		path = aPath;
		length = aLength;
	}

	public List<Long> getPath() {
		return path;
	}

	public void setPath(List<Long> aPath) {
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


