
// wrapper class to hold two corresponding BinaryConstraint

public class BinaryConstraintPair {
	
	private BinaryConstraint bc1;
	private BinaryConstraint bc2;
	
	public BinaryConstraintPair(BinaryConstraint bc1, BinaryConstraint bc2) {
		this.bc1 = bc1;
		this.bc2 = bc2;
	}
	
	// getters
	public BinaryConstraint getBc1() {
		return this.bc1;
	}
	
	public BinaryConstraint getBc2() {
		return this.bc2;
	}

}
