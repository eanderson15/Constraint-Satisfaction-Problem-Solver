
public class Variable {
	
	private String name;
	private Integer duration = null;
	private Domain domain = null;
	
	// constructors for different situations
	public Variable(String name) {
		this.name = name;
	}
	
	public Variable(String name, Integer duration) {
		this.name = name;
		this.duration = duration;
	}
	
	public Variable(String name, Domain domain) {
		this.name = name;
		this.domain = domain;
	}
	
	public Variable(String name, Integer duration, Domain domain) {
		this.name = name;
		this.duration = duration;
		this.domain = domain;
	}
	
	// copy constructor
	public Variable(Variable other) {
		this.name = other.name;
		this.duration = other.duration;
		if (other.domain != null) {
			this.domain = new Domain(other.domain);
		}
	}
	
	// getters and setter
	public String getName() {
		return this.name;
	}

	public Integer getDuration() {
		return this.duration;
	}
	
	public Domain getDomain() {
		return this.domain;
	}
	
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	
	@Override
	public String toString() {
		if (this.domain != null) {
			if (this.duration != null) {
				return ("Name: " + this.name + ", Duration: " + this.duration.toString() + ", Domain: " + this.domain.toString());
			} else {
				return ("Name: " + this.name + ", Domain: " + this.domain.toString());
			}
		} else {
			if (this.duration != null) {
				return ("Name: " + this.name + ", Duration: " + this.duration.toString());
			} else {
				return ("Name: " + this.name);
			}
		}
	}
	
}
