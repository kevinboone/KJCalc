**scibreak

The variable `scibreak` holds the order below which scientific notiation
will not be used, when the display mode is "default" (`disp(deflt)`).
For example, if `scibreak=5` then 1E5 is rendered as "10000". If 
`scibreak=4`, then 1E5 is rendered as "1E5". Negative exponents are
handled in comparable way. The value of
`scibreak` affects on the display of results, not their calculation. Moreover,
it does not affect the precision used for display, only whether scientific
notation is used.

See also: `precision`, `disp`. 
