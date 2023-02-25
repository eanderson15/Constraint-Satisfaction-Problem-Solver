import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class CSP {
	
	public static Scanner scanner;
	
	private LinkedList<UnaryConstraint> unaryConstraints;
	private LinkedList<BinaryConstraintPair> binaryConstraintPairs;
	private Assignment assignment;
	private int stateCount = 0;
	
	// constructor
	public CSP() {
		this.unaryConstraints = new LinkedList<UnaryConstraint>();
		this.binaryConstraintPairs = new LinkedList<BinaryConstraintPair>();
	}
	
	// copy constructor
	public CSP(CSP other) {
		this.assignment = new Assignment(other.assignment);
		this.unaryConstraints = new LinkedList<UnaryConstraint>();
		this.binaryConstraintPairs = new LinkedList<BinaryConstraintPair>();
		Iterator<UnaryConstraint> uci = other.unaryConstraints.iterator();
		while (uci.hasNext()) {
			UnaryConstraint uc = uci.next();
			this.unaryConstraints.add(new UnaryConstraint(this.assignment.getVariable(uc.getLhs().getName()), uc.getOperator(), uc.getRhs()));
		}
		Iterator<BinaryConstraintPair> bcpi = other.binaryConstraintPairs.iterator();
		while (bcpi.hasNext()) {
			BinaryConstraintPair bcp = bcpi.next();
			BinaryConstraint bc1 = bcp.getBc1();
			BinaryConstraint bc2 = bcp.getBc2();
			BinaryConstraint bc3 = new BinaryConstraint(this.assignment.getVariable(bc1.getLhs().getName()), bc1.getOperator(), this.assignment.getVariable(bc1.getRhs().getName()));
			BinaryConstraint bc4 = new BinaryConstraint(this.assignment.getVariable(bc2.getLhs().getName()), bc2.getOperator(), this.assignment.getVariable(bc2.getRhs().getName()));
			bc3.setNeighbor(bc4);
			bc4.setNeighbor(bc3);
			this.binaryConstraintPairs.add(new BinaryConstraintPair(bc3, bc4));
		}
		this.stateCount = other.stateCount;
	}
	
	// uses lines of file through domain
	public void createAssignment() {
		// ignore line
		scanner.nextLine();
		// count variables
		int variableCount = scanner.nextLine().split(" ").length;
		Variable[] variables = new Variable[variableCount];
		String[] variableLines = new String[variableCount];
		// iterate through lines corresponding to variables
		for (int i = 0; i < variableCount; i++) {
			String line = scanner.nextLine();
			String[] lineParse = line.split(" ");
			if (lineParse.length > 1) {
				try {
					// variables for job scheduling
					variables[i] = new Variable(lineParse[0], Integer.parseInt(lineParse[1]));
					if (lineParse.length > 2) {
						System.out.println("Too many numeric arguments in file.");
						return;
					}
				// variables for map coloring
				} catch (NumberFormatException e) {
					variables[i] = new Variable(lineParse[0]);
					variableLines[i] = line;
				}
			// no extra info encoded in variable
			} else {
				variables[i] = new Variable(lineParse[0]);
			}
		}
		// create domain
		Domain domain;
		String[] domainLine = scanner.nextLine().split(" ");
		if (domainLine.length == 1) {
			try {
				domain = new Domain(Integer.parseInt(domainLine[0]));
			} catch (NumberFormatException e) {
				domain = new Domain(domainLine);
			}
		} else {
			domain = new Domain(domainLine);
		}
		for (int i = 0; i < variableCount; i++) {
			variables[i].setDomain(domain);
		}
		this.assignment = new Assignment(variableCount, variables);
		// map coloring constraints set by adjacencies
		for (int i = 0; i < variableCount; i++) {
			if (variableLines[i] != null) {
				String[] lineParse = variableLines[i].split(" ");
				for (int j = 1; j < lineParse.length; j++) {
					BinaryConstraint bc1 = new BinaryConstraint(variables[i], "not equals", this.assignment.getVariable(lineParse[j]));
					BinaryConstraint bc2 = new BinaryConstraint(bc1.getRhs(), bc1.getOperator(), bc1.getLhs());
					bc1.setNeighbor(bc2);
					bc2.setNeighbor(bc1);
					this.binaryConstraintPairs.add(new BinaryConstraintPair(bc1, bc2));
				}
			}
		}
	}
	
	// creates constraints from bottom of file
	public void createConstraints() {
		while (scanner.hasNextLine()) {
			String constraint = scanner.nextLine();
			String[] constraintParse = constraint.split(" ");
			if (this.assignment.contains(constraintParse[2])) {
				BinaryConstraint bc1 = new BinaryConstraint(this.assignment.getVariable(constraintParse[0]), constraintParse[1], this.assignment.getVariable(constraintParse[2]));
				BinaryConstraint bc2 = new BinaryConstraint(bc1.getRhs(), bc1.oppositeOperator(), bc1.getLhs());
				bc1.setNeighbor(bc2);
				bc2.setNeighbor(bc1);
				this.binaryConstraintPairs.add(new BinaryConstraintPair(bc1, bc2));
			} else {
				this.unaryConstraints.add(new UnaryConstraint(this.assignment.getVariable(constraintParse[0]), constraintParse[1], constraintParse[2]));
			}
		}
	}
	
	public Boolean backtrackingSearch() {
		return backtrack();
	}
	
	public Boolean backtrack() {
		this.stateCount = this.stateCount + 1;
		if (this.assignment.isComplete()) {
			return true;
		}
		Variable variable = this.assignment.selectUnassignedVariable();
		for (String value: variable.getDomain().getDomain()) {
			this.assignment.add(variable, value);
			if (this.assignment.isConsistent(this.unaryConstraints, this.binaryConstraintPairs)) {
				if (this.backtrack()) {
					return true;
				}
			}
			this.assignment.add(variable, null);
		}
		return false;
	}
	
	public CSP backtrackingSearch_AC3() {
		Iterator<UnaryConstraint> li = this.unaryConstraints.iterator();
		while (li.hasNext()) {
			UnaryConstraint uc = li.next();
			if (this.revise(uc)) {
				if (uc.getLhs().getDomain().getDomain().size() == 0) {
					return this;
				}
			}
		}
		return this.backtrack_AC3();
	}
	
	public CSP backtrack_AC3() {
		this.stateCount = this.stateCount + 1;
		if (this.assignment.isComplete()) {
			return this;
		}
		Variable variable = this.assignment.selectUnassignedVariable();
		for (String value: variable.getDomain().getDomain()) {
			CSP clone = new CSP(this);
			clone.assignment.add(variable, value);
			clone.assignment.getVariable(variable.getName()).setDomain(new Domain(new String[]{value}));
			if (clone.assignment.isConsistent(this.unaryConstraints, this.binaryConstraintPairs)) {
				if (clone.AC3()) {
					clone = clone.backtrack_AC3();
					if (clone.assignment.isComplete()) {
						return clone;
					}
				}
			}
		}
		return this;
	}
	
	public Boolean AC3() {
		LinkedList<BinaryConstraint> queue = new LinkedList<BinaryConstraint>();
		Iterator<BinaryConstraintPair> li = this.binaryConstraintPairs.iterator();
		while (li.hasNext()) {
			BinaryConstraintPair bcp = li.next();
			queue.add(bcp.getBc1());
			queue.add(bcp.getBc2());
		}
		while (!queue.isEmpty()) {
			BinaryConstraint bc = queue.pop();
			if (this.revise(bc)) {
				if (bc.getLhs().getDomain().getDomain().size() == 0) {
					return false;
				}
				queue.add(bc.getNeighbor());
			}
		}
		return true;
	}
	
	public Boolean revise(BinaryConstraint bc) {
		boolean revised = false;
		Domain domainX = new Domain(bc.getLhs().getDomain());
		for (String elementX: bc.getLhs().getDomain().getDomain()) {
			boolean deleteX = true;
			for (String elementY: bc.getRhs().getDomain().getDomain()) {
				if (bc.evaluate(elementX, elementY)) {
					deleteX = false;
					break;
				}
			}
			if (deleteX) {
				domainX.getDomain().remove(elementX);
				revised = true;
			}
		}
		bc.getLhs().setDomain(domainX);
		return revised;
	}
	
	public Boolean revise(UnaryConstraint uc) {
		boolean revised = false;
		Domain domainX = new Domain(uc.getLhs().getDomain());
		for (String elementX: uc.getLhs().getDomain().getDomain()) {
			boolean deleteX = true;
			if (uc.evaluate(elementX)) {
				deleteX = false;
			}
			if (deleteX) {
				domainX.getDomain().remove(elementX);
				revised = true;
			}
		}
		uc.getLhs().setDomain(domainX);
		return revised;
	}
	
	public void printConstraints() {
		Iterator<UnaryConstraint> li2 = this.unaryConstraints.iterator();
		while (li2.hasNext()) {
			UnaryConstraint uc = li2.next();
			System.out.println(uc.toString());
			System.out.println(uc.evaluate(this.assignment));
		}
		Iterator<BinaryConstraintPair> li = this.binaryConstraintPairs.iterator();
		while (li.hasNext()) {
			BinaryConstraintPair bcp = li.next();
			System.out.println(bcp.getBc1().toString());
			System.out.println(bcp.getBc1().evaluate(this.assignment));
			System.out.println(bcp.getBc2().toString());
			System.out.println(bcp.getBc2().evaluate(this.assignment));
		}
	}

	public static void main(String[] args) {
		int useAC3 = 0;
		boolean useNormalBool = true;
		boolean useAC3Bool = false;
		File input = null;
		if (args.length == 0) {
			System.out.println("No arguments were given.");
			return;
		} else if (args.length > 2) {
			System.out.println("Too many arguments were given.");
			return;
		}
		try {
			input = new File(args[0]);
			scanner = new Scanner(input);
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			return;
		}
		if (args.length == 2) {
			try {
				useAC3 = Integer.parseInt(args[1]);
				if (useAC3 > 2 || useAC3 < 0) {
					throw new NumberFormatException();
				}
				if (useAC3 == 1) {
					useAC3Bool = true;
				} else if (useAC3 == 2) {
					useAC3Bool = true;
					useNormalBool = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("Incorrect second argument.");
				return;
			}
		}
		CSP csp = new CSP();
		csp.createAssignment();
		csp.createConstraints();
		CSP clone = new CSP(csp);
		if (useNormalBool) {
			long startNormal = System.currentTimeMillis();
			System.out.println("Backtracking Search Start Time: " + (startNormal / 100.0) + " secs.\n");
			csp.backtrackingSearch();
			if (!csp.assignment.isComplete()) {
				System.out.println("FAILED");
			}
			csp.assignment.printAssignment();
			System.out.println();
			long endNormal = System.currentTimeMillis();
			System.out.println("Backtracking Search Elapsed time: " + ((endNormal - startNormal) / 1000.0) + " secs.");
			System.out.println("Number of states explored by Backtracking Search: " + csp.stateCount + "\n");
		}
		if (useAC3Bool) {
			long startAC3 = System.currentTimeMillis();
			System.out.println("Backtracking Search with AC3 Start Time: " + (startAC3 / 100.0) + " secs.\n");
			clone = clone.backtrackingSearch_AC3();
			if (!clone.assignment.isComplete()) {
				System.out.println("FAILED");
			}
			clone.assignment.printAssignment();
			System.out.println();
			long endAC3 = System.currentTimeMillis();
			System.out.println("Backtracking Search with AC3 Elapsed time: " + ((endAC3 - startAC3) / 1000.0) + " secs.");
			System.out.println("Number of states explored by Backtracking Search with AC3: " + clone.stateCount + "\n");
		}
	}

}
