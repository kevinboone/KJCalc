**Hexadecimal numbers in KJCalc

Numbers can be entered in hexadecimal by preceding them with "0x"; so,
for example "0x32" is the hexadcimal number 32, which is decimal 50. 
At present, only integer values are supported, although they can be
of any length. An error will be raised if a number prefixed by 0x
contains a decimal point. The symbol "E", that would indicate a
scientific notation exponent in decimal, is an ordinary digit in hexadecimal.

To display results in hexadecimal, use `disp(hex)`. If the number is
not an integer, then "default" display mode will be used instead.

Calculations based on hexadecimal numbers will be subject to the same precision
specification as those based on decimal numbers; but it might occasionally
be significant that the value of the `precision` variable is a number of
decimal, not hexadecimal significant figures.

See also: `disp`, `precision`.
