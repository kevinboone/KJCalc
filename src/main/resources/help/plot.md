**plot(expr,variable,low,high)

Plot the value of the expression _expr_ in _variable_,
between _low_ and _high_, as a line on a graph. 

For example, plot the value of _sin(x)/x_ in the range (0,720): 

  `plot(sin(x)/x,x,0,720)`

Or, alternatively, by defining the function _f(x)_:  

  `f:sin(arg0)/arg0`
  `plot(f(x),x,0,720)`

The plotting functionality is only available on platforms with graphics
support, and the appearance of the plot user interface will vary from
platform to platform. The plot will automatically be scaled so that the
_y_ range fills the available height. Multiple expressions can be plotted
on the same axes by running the `plot` operation several times. The plots
are distinguished by colour, alternating red, green, blue for each new
plot.

Moving the mouse cursor over the plot will show approximate _x_ and _y_ 
values of the point -- the accuracy of the reported values will depend
on the screen hardware, as well as the way in which the expression value
varies between data points. It should only be used as a guide.

Values that cannot be plotted -- for example, square roots of negative
numbers -- are ommitted. If no plottable points remain, then an error
is raised, and nothing will be drawn. 

Although the calculation of the expression is carried out using 
arbitrary precision math, as most things are in KJCalc, the result
must be capable of being converted to a double-precision floating-point
number, in order to be displayed.
