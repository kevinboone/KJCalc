**disp(d)

Set the current display mode to _d_. This setting does not in any way affect
the way that results are calculated, only the way they are displayed.

`disp(deflt)` Select default display mode. Values whose order of magnitude is no
greater than `scibreak` will be displayed as plain digits, otherwise
scientific notation will be used. Numerical results will be rounded
to the current setting of `precision`, although complex calcualtions
may have suffered some loss of precision -- this is difficult to
predict.

`disp(raw)` Display values exactly as they are produced by the expression evaluator.
For numerical values, these results are exactly as produced by Java's
`BigDecimal` class. Scientific notation may be used, depending on the
order of the number and the way the calculation was performed. 

`disp(sci)` Always use scientific notation for numerical results. Results are
rounded to the current setting of `precision`.

`disp(eng)` Use engineering scientific notation for numerical results. Orders
are adjusted to powers of three and, if the exponent is in the range
+/- 12, common suffices (M, k, m) will be appended instead of
using the "ENN" format.

`disp(hex)` Display the result in hexadecimal, if it is a whole number. If it
is not a whole number, use the default formatting instead.

`disp(rat)` Display the result as a whole number plus a ratio of two whole 
numbers, if that is practicable. For example, in ratio mode, "1/2*2/3" 
is "1/3", rather than 0.3333... To be displayed as a ratio, the division
of the numerator by the denominator must be equal to the calculated value,
within the limit set by the `precision` variable. Results will be 
haphazard when the result to be displayed requires all of the 
allowed precision -- for example, it is a recurring value. The algorithm
won't attempt to find ratios that require more than about three digits in
the numerator or denominator.
