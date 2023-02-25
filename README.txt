Eric Anderson
eander29@u.rochester.edu
CSC 242
Project 2
Worked alone

Build instructions:

	- javac *.java

Run instructions:
	Input is filename and optional 0, 1, or 2. 0 runs without AC3. 1 runs both.
	2 runs only AC3. If optional argument is not input, default is 0.

	- java CSP [filename] [(optional) {0, 1}]

	- java CSP AustraliaMapColoring.txt 1
	- java CSP AustraliaMapColoring.txt 2
	- java CSP JobShopScheduling.txt

Output format closely mimics the example output given to us.
Turn in zip contains src files, CSP files, submission form, and the README.

Runs for CSP map coloring and job scheduling problems of file input described
in project description. NOTE: map coloring files cannot have numeric labels
for region labels.

Normal backtracking search is severely constrained by d (the size of the domain)
and n (the number of variables). On the given map coloring problem, because
it is so small, it runs in less than a second. On the given job scheduling 
problem, it takes from about 200-400 seconds to terminate normally. When 
changing Tmax to 10, so as to constrict the domain and leaving no possible 
valid assignment causing many states to be explored, it terminates in about 
90 seconds. Changing Tmax to 20 (still no solution) would cause the program 
to run for an unreasonable amount of time.

Backtracking search with AC3 runs with no problems, taking a negligible amount
of time for the given map coloring problem and around 0.008 seconds for the
given job scheduling problem.

Files:
	- Assignment.java - holds a mapping between variable and assigned values
	- AustraliaMapColoring.txt
	- BinaryConstraint.java - describes the relationship between two variables
	- BinaryConstraintPair.java - holds the two binary constraints that are equivalent but opposite directions
	- Constraint.java - interface for constraints
	- CSP.java 
	- Domain.java - holds the set of possible values
	- JobShopScheduling.txt
	- README.txt
	- submission_form.pdf
	- UnaryConstraint.java - describes the relationship between a variable and a domain value
	- Variable.java - holds information describing the variable

I think I did a pretty good job of implementing this with good object
oriented design and encapsulation. It was originally implemented without
BinaryConstraintPair but I added this to accommodate for AC3 which
necessitates considering constraints with opposite arc directions.

I'm concerned with the run time of my basic backtracking search, although it
makes sense considering the amount of states it must consider. There are some
basic alterations that could make it run faster like constricting the domains
of variables based on some of the given information, but as is, it is
implemented in the way shown in the textbook.

EXAMPLE OUTPUTS:
A.
java CSP JobShopScheduling.txt
Backtracking Search Start Time: 1.645992540009E10 secs.

WheelLF: 20
NutsLF: 21
AxelF: 10
WheelLB: 10
AxelB: 0
Inspect: 25
CapLF: 23
CapLB: 24
NutsRB: 11
WheelRF: 20
NutsRF: 21
CapRF: 23
NutsLB: 22
WheelRB: 10
CapRB: 22

Backtracking Search Elapsed time: 209.258 secs.
Number of states explored by Backtracking Search: 38907231




B.
java CSP JobShopScheduling.txt 1
Backtracking Search Start Time: 1.645989406285E10 secs.

WheelLF: 20
NutsLF: 21
AxelF: 10
WheelLB: 10
AxelB: 0
Inspect: 25
CapLF: 23
CapLB: 24
NutsRB: 11
WheelRF: 20
NutsRF: 21
CapRF: 23
NutsLB: 22
WheelRB: 10
CapRB: 22

Backtracking Search Elapsed time: 209.9 secs.
Number of states explored by Backtracking Search: 38907231

Backtracking Search with AC3 Start Time: 1.645989616189E10 secs.

WheelLF: 20
NutsLF: 21
AxelF: 10
WheelLB: 11
AxelB: 0
Inspect: 25
CapLF: 23
CapLB: 24
NutsRB: 12
WheelRF: 20
NutsRF: 21
CapRF: 23
NutsLB: 22
WheelRB: 11
CapRB: 22

Backtracking Search with AC3 Elapsed time: 0.008 secs.
Number of states explored by Backtracking Search with AC3: 16




C.
java CSP JobShopScheduling.txt 2
Backtracking Search with AC3 Start Time: 1.645992511151E10 secs.

WheelLF: 20
NutsLF: 21
AxelF: 10
WheelLB: 11
AxelB: 0
Inspect: 25
CapLF: 23
CapLB: 24
NutsRB: 12
WheelRF: 20
NutsRF: 21
CapRF: 23
NutsLB: 22
WheelRB: 11
CapRB: 22

Backtracking Search with AC3 Elapsed time: 0.04 secs.
Number of states explored by Backtracking Search with AC3: 16




D.
java CSP AustraliaMapColoring.txt 1
Backtracking Search Start Time: 1.645993017255E10 secs.

Q: r
NSW: g
T: r
NT: g
V: r
WA: r
SA: b

Backtracking Search Elapsed time: 0.027 secs.
Number of states explored by Backtracking Search: 9

Backtracking Search with AC3 Start Time: 1.645993017287E10 secs.

Q: r
NSW: g
T: r
NT: g
V: r
WA: r
SA: b

Backtracking Search with AC3 Elapsed time: 0.002 secs.
Number of states explored by Backtracking Search with AC3: 8