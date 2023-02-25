import java.util.HashSet;
import java.util.Set;

public class Domain {
	
	private Set<String> domain;
	
	// constructor for job scheduling
	public Domain(Integer max) {
		this.domain = new HashSet<String>();
		for (int i = 0; i <= max; i++) {
			this.domain.add(Integer.toString(i));
		}
	}
	
	// constructor for map coloring
	public Domain(String[] values) {
		this.domain = new HashSet<String>();
		int valuesCount = values.length;
		for (int i = 0; i < valuesCount; i++) {
			this.domain.add(values[i]);
		}
	}
	
	// copy constructor
	public Domain(Domain other) {
		this.domain = new HashSet<String>();
		for (String element: other.domain) {
			this.domain.add(element);
		}
	}
	
	// getter
	public Set<String> getDomain() {
		return this.domain;
	}
	
	@Override
	public String toString() {
		return this.domain.toString();
	}

}
