**precision

KJCalc is an _arbitrary precision calculator_. That means that arithmetical
results are calculated with a precision defined by the user, and that
precision can be much higher than 17-or-so significant
figures that most computers'
hardware provides. Calculations are performed using decimal, not 
binary, arithmetic. 

Each time a calculation is performed, some precision may be lost. In general,
multiplication, addition, and subtraction preserve precision, but division
requires that precision be limited. For example, although 1/4 can be
represented exactly in decimal, 1/3 is a recurring result that must
be specified to a particular precision. "sqrt(9)" has an exact decimal
result, but "sqrt(2)" does not. 

If operations that require
restriction of precision are applied successively to a result, then the
loss of precision accumulates. Very broadly, a digit of precision could
be lost in each operation. The default precision is set to thirty digits,
so many calculations can be performed before the loss of precision 
reaches a stage beyond which an actual physical measurement is likely to
be affected.

The variable `precision` can be assigned at any time, and this value
will be used in all subsequent calculations. The same value will also
be used to round the final result that is displayed to the user,
as there is no value in displaying digits that are known to be inaccurate.
However, there is no guarantee that every digit of the displayed result
is accurate -- precise calculation of precision is beyond the scope of
KJCalc. 

See also: `scibreak`, `disp`. 
