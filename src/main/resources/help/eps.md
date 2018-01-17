**eps

The variable `eps` holds the precision required from operations that
attempt to find roots by iterative methods. `eps` is usually a small
number, of the order 1E-8 (the default). The use of `eps` will depend on the
algorithm chosen but, in finding roots, it typically represents how
close the function's value must be to zero. 

Smaller (more precise) values of `eps` will typically require larger
values of `iters`. 

See also: `iters`, `solve__secant`, `solve__bisection`.
