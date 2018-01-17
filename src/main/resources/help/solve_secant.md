**solve__secant(expr,variable,low,high)

Attempt to find a root (zero value) of the expression _expr_ in _variable_,
between _low_ and _high_, using the secant method.

For example, find a solution for _x_ of the equation 
_x^3-5*x^2+2=0_ near the range (-1,1): 

  `solve__secant(x^3-5*x^2+2,x,-1,1)`

Or, alternatively, by defining the function _f(x)_:  

  `f:arg0^3-5*arg0^2+2=0`
  `solve__secant(f(x),x,-1,1)`

Unlike bisection, the secant method does not require the root to be bracketed,
so it isn't necessary to know the approximate location of the root in
advance. The unfortunate corollary of this behaviour is that it is difficult
to find a root that is known to be in a particular range, when there are
other roots nearby. In the previous example, specifying the bounds as
(-5,5) would miss the root at x≃-0.284 altogether, and find the root at
x≃25 instead.

Another problem with the secant method is that it is prone to a 
"horizontal secant line" problem, where the _x_ values being considered
become so close together that the function values have effectively zero
gradient between them. "Effetively zero" here means that, when they are
subtracted, they yield a number that is closer to zero that the current
setting of `precision`. This problem can sometimes be resoved by
increasing `precision`, or decreasing `eps`. 

See also: `eps`, `iters`, `solve__bisection`, `precision`.
