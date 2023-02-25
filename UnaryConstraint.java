
public class UnaryConstraint extends Constraint<String> {
	
	private Variable lhs;
	private String operator;
	private String rhs;
	
	// constructor
	public UnaryConstraint(Variable lhs, String operator, String rhs) {
		super(lhs, operator, rhs);
		this.lhs = lhs;
		this.operator = operator;
		this.rhs = rhs;
	}
	
	// checks if assignment is consistent with constraint with switch statement
	@Override
	public Boolean evaluate(Assignment assignment) {
		String lhsValue;
		if ((lhsValue = assignment.getValue(this.lhs)) != null) {
			switch (this.operator) {
			case "=":
				return (lhsValue.equals(this.rhs));
			case "!=":
				return (!lhsValue.equals(this.rhs));
			default:
				System.out.println("Invalid unary operator: " + this.operator);
				return false;
			}
		}
		return true;
	}
	
	// checks if input is consistent with constraint with switch statement
	public Boolean evaluate(String lhsValue) {
		switch (this.operator) {
		case "=":
			return (lhsValue.equals(this.rhs));
		case "!=":
			return (!lhsValue.equals(this.rhs));
		default:
			System.out.println("Invalid unary operator: " + this.operator);
			return false;
		}
	}

	@Override
	public String toString() {
		return ("Variable 1: [" + this.lhs.toString() + "] " + this.operator + " " + this.rhs);
	}
	
}
