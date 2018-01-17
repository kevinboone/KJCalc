**Variables in KJCalc

KJCalc does not distinguish between constants and variables -- both are
simply names that are assigned a value. A variable is defined using
the equals operator:

  `foo=42`

  `bar=sin(foo)`

Apart from the built-in variables, variables have no default values: it
is an error to use a variable that has not been defined. 

Variables (or, more likely, constants) can be defined in a file, and
read using the `load()` function.

Variables can be reassigned at any time.

See also: `functions`, `names`, `load`.

