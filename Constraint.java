
// interface for constraints

public abstract class Constraint<R> {
	
	private Variable lhs;
	private String operator;
	private R rhs;
	
	// constructor
	public Constraint(Variable lhs, String operator, R rhs) {
		this.lhs = lhs;
		this.operator = operator;
		this.rhs = rhs;
	}
	
	public abstract Boolean evaluate(Assignment assignment);
	
	// getters
	public Variable getLhs() {
		return this.lhs;
	}
	
	public String getOperator() {
		return this.operator;
	}
	
	public R getRhs() {
		return this.rhs;
	}
}
