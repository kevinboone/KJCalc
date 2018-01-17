**Defining functions in KJCalc

KJCalc does not offer a full programming language, but it is possible to
define new functions to simplify repeated calculation. Function
definitions can, if necessary, be placed in a file and read using the
`load()` function.

A function is defined as follows:

`name:expression`

A function can take any number of arguments; these are referred to within
the function definition as `arg0`, `arg1`, etc. For example, to define
a function that calculates the length of the hypotenuse of a right triangle,
give the lengths of the other two sides, enter:

`hyp:sqrt(arg0^2+arg1^2)`

This function can then be invoked like this:

`hyp(3,4)`

See also: `load`, `names`.
