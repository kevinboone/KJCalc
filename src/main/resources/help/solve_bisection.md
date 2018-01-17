**solve__bisection(expr,variable,low,high)

Attempt to find a root (zero value) of the expression _expr_ in _variable_,
between _low_ and _high_, using the bisection method.

For example, find a solution for _x_ of the equation 
_x^3-2*x^2+3=0_ in the range (-1,1): 

  `solve__bisection(x^3-2*x^2+3,x,-1,1)`

Or, alternatively, by defining the function _f(x)_:  

  `f:arg0^3-2*arg0^2+3`
  `solve__bisection(f(x),x,-1,1)`

The bisection method is robust, and will always be able to find a root
if it is bracketed correctly, and the precision chosen appropriately.
It does, however, require that the approximate position of the root
be known in advance. If the bracketed region contains multiple roots, 
only one will be found, and there is no way to predict what others
there might be. Note, for instance, that the example given does not
find the root at _x=1_, even though that value was given as a bound.
The bisection is prone to mis-identifying a vertical point of inflection
as a root.


See also: `eps`, `iters`, `solve__secant`.
