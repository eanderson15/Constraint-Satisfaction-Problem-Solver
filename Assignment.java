import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Assignment {
	
	// stores assignment of name of variable to element of domain
	private HashMap<String, String> assignment;
	// stores mapping from name of variable to variable for quick access
	private HashMap<String, Variable> variables;
	
	// constructor
	public Assignment(Integer initialCapacity, Variable[] variables) {
		this.assignment = new HashMap<String, String>(initialCapacity);
		this.variables = new HashMap<String, Variable>(initialCapacity);
		for (int i = 0; i < variables.length; i++) {
			this.assignment.put(variables[i].getName(), null);
			this.variables.put(variables[i].getName(), variables[i]);
		}
	}
	
	// copy constructor
	public Assignment(Assignment other) {
		this.assignment = new HashMap<String, String>(other.assignment.size());
		this.variables = new HashMap<String, Variable>(other.assignment.size());
		for (Map.Entry<String, Variable> entry: other.variables.entrySet()) {
			this.variables.put(entry.getKey(), new Variable(entry.getValue()));
		}
		for (Map.Entry<String, String> entry: other.assignment.entrySet()) {
			this.assignment.put(entry.getKey(), entry.getValue());
		}
	}
	
	// edits assignment
	public void add(String key, String value) {
		this.assignment.put(key, value);
	}
	
	// edits assignment
	public void add(Variable key, String value) {
		this.assignment.put(key.getName(), value);
	}
	
	// returns variable's assigned value
	public String getValue(String key) {
		return this.assignment.get(key);
	}
	
	// returns variable's asigned value
	public String getValue(Variable key) {
		return this.assignment.get(key.getName());
	}
	
	// returns variable from variable name
	public Variable getVariable(String key) {
		return this.variables.get(key);
	}
	
	// returns true if assignment has the variable
	public Boolean contains(String key) {
		return this.assignment.containsKey(key);
	}
	
	// returns true if all variables have an assigned value
	public Boolean isComplete() {
		return !this.assignment.containsValue(null);
	}
	
	// returns first variable that is assigned to null
	public Variable selectUnassignedVariable() {
		String key = null;
		for (Map.Entry<String, String> entry: this.assignment.entrySet()) {
			if (entry.getValue() == null) {
				key = entry.getKey();
			}
		}
		if (key == null) {
			System.out.println("Improper use of Assignment.unassignedVariable()");
			return null;
		} else {
			return this.variables.get(key);
		}
	}
	
	// iterates through constraints and checks them
	public Boolean isConsistent(LinkedList<UnaryConstraint> ucs, LinkedList<BinaryConstraintPair> bcps) {
		Iterator<UnaryConstraint> ucsIterator = ucs.iterator();
		while(ucsIterator.hasNext()) {
			if (!ucsIterator.next().evaluate(this)) {
				return false;
			}
		}
		Iterator<BinaryConstraintPair> bcpsIterator = bcps.iterator();
		while(bcpsIterator.hasNext()) {
			BinaryConstraintPair bcp = bcpsIterator.next();
			if (!bcp.getBc1().evaluate(this)) {
				return false;
			}
			if (!bcp.getBc2().evaluate(this)) {
				return false;
			}
		}
		return true;
	}
	
	// getters
	public HashMap<String, String> getAssignment() {
		return this.assignment;
	}
	
	public HashMap<String, Variable> getVariables() {
		return this.variables;
	}
	
	// method to print in professor requested format
	public void printAssignment() {
		for (Map.Entry<String, String> entry: this.assignment.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
	
	@Override
	public String toString() {
		String retString = this.assignment.toString() + "\n";
		for (Variable variable: this.variables.values()) {
			retString = retString + variable.toString() + "\n";
		}
		return retString;
	}

}
