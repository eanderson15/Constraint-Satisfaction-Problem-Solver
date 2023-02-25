
public class BinaryConstraint extends Constraint<Variable>{
		
	private Variable lhs;
	private String operator;
	private Variable rhs;
	// to keep track of the BinaryConstraint that reverses the direction of the constraint
	private BinaryConstraint neighbor = null;
	
	// constructor
	public BinaryConstraint(Variable lhs, String operator, Variable rhs) {
		super(lhs, operator, rhs);
		this.lhs = lhs;
		this.operator = operator;
		this.rhs = rhs;
	}
	
	// evaluates if constraint is consistent with assignment with switch statement
	@Override
	public Boolean evaluate(Assignment assignment) {
		String lhsValue, rhsValue;
		if ((lhsValue = assignment.getValue(this.lhs)) == null) {
			return true;
		} else if ((rhsValue = assignment.getValue(this.rhs)) == null) {
			return true;
		} else {
			switch (this.operator) {
			case "before":
				return ((Integer.parseInt(lhsValue) + this.lhs.getDuration().intValue()) <= Integer.parseInt(rhsValue));
			case "after":
				return (Integer.parseInt(lhsValue) >= (Integer.parseInt(rhsValue) + this.rhs.getDuration().intValue()));
			case "disjoint":
				return ((Integer.parseInt(lhsValue) + this.lhs.getDuration().intValue()) <= Integer.parseInt(rhsValue)) || ((Integer.parseInt(rhsValue) + this.rhs.getDuration().intValue()) <= Integer.parseInt(lhsValue));
			case "equals":
				return (lhsValue.equals(rhsValue));
			case "not equals":
				return (!lhsValue.equals(rhsValue));
			default:
				System.out.println("Invalid binary operator: " + this.operator);
				return false;
			}
		}
	}
	
	// evaluates if input strings are consistent with constraint with switch statement
	public Boolean evaluate(String lhsValue, String rhsValue) {
		switch (this.operator) {
		case "before":
			return ((Integer.parseInt(lhsValue) + this.lhs.getDuration().intValue()) <= Integer.parseInt(rhsValue));
		case "after":
			return (Integer.parseInt(lhsValue) >= (Integer.parseInt(rhsValue) + this.rhs.getDuration().intValue()));
		case "disjoint":
			return ((Integer.parseInt(lhsValue) + this.lhs.getDuration().intValue()) <= Integer.parseInt(rhsValue)) || ((Integer.parseInt(rhsValue) + this.rhs.getDuration().intValue()) <= Integer.parseInt(lhsValue));
		case "equals":
			return (lhsValue.equals(rhsValue));
		case "not equals":
			return (!lhsValue.equals(rhsValue));
		default:
			System.out.println("Invalid binary operator: " + this.operator);
			return false;
		}
	}
	
	// returns the operator for the other BinaryConstraint in its pair
	public String oppositeOperator() {
		switch (this.operator) {
		case "before":
			return "after";
		case "after":
			return "before";
		case "disjoint":
			return "disjoint";
		case "equals":
			return "equals";
		case "not equals":
			return "not equals";
		default:
			System.out.println("Invalid binary operator: " + this.operator);
			return null;
		}
	}
	
	@Override
	public String toString() {
		return ("Variable 1: [" + this.lhs.toString() + "] " + this.operator + " Variable 2: [" + this.rhs.toString() + "]");
	}
	
	// getters
	public BinaryConstraint getNeighbor() {
		return this.neighbor;
	}
	
	public void setNeighbor(BinaryConstraint other) {
		this.neighbor = other;
	}
	
}
