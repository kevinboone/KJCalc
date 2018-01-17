**load(f)

Load, parse, and evaluate a file _f_. The file can contain function
definitions, variable settings, display settings, or anything else that
might be useful. _f_ should be an absolute pathname, or relative to the
current working directory. 

The file is processed line-by-line; expressions therefore cannot
span lines. The end-of-line marker effectively acts as an expression
separator. 

Parsing or evaluation will stop at the first
error. Evaluation of the file does not cause any calcuated result to
be displayed, but functions that produce output directly (e.g.,
`list()`) produce output as normal.
 
The final result of the function is the
result of the last evaluation that produced a result. If none of the
expressions produces a result, then the final result will be zero.

`load()` invocations can be nested; that is, one file can load another.
