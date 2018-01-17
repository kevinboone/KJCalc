**KJCalc overview

KJCalc is a command-line calculator/expression evaluator that is designed to
fill the gap between simple four-function calculators, and serious math
platforms like Matlab and Octave. It is written entirely in Java, and
should run on any platform that has a Java 7 runtime (or later) available.

KJCalc is designed to behave like a sophisticated pocket calcuator, not
a programming language. So, for example, trignonetric calculations are
performed using angles in degrees, not radians, by default (but this
can be changed.) All results are calculated to an arbitrary level of
precision, using decimal, not binary arithmetic. This means that results
that have exact representations in decimal should appear as exact 
numbers, not approximations. So, for example "1.01-1.001" is "0.009",
and not "0.00900000000000012" as most calculators that use binary floating-pointmath will suggest.

KJCalc supports the usual trigonometric, logarithmic, etc., functions,
to arbitrary precision. It can display results in a number of different
ways. There is support for limited programming -- new functions can
be defined, and these can stored in a file for later use. 

On platforms that support this, KJCalc provides support for editing
the command line, along with recall of earlier expressions. Alternatively,
an expression can be supplied on the command line. On platforms with
graphics support KJCalc offers basic graph plotting capabilities.

KJCalc is essentially a front-end to the KJExpr expression parsing
library. Both are maintained by Kevin Boone (kevin at railwayterrace dot
com) and released under the terms of the GNU Public Licence. 
