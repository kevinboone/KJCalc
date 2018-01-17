**gcd(u,v)

Greatest common divisor of _u_ and _v_. `gcd(21,77)` is 7, for example, as
7 divides without remainder into 21 and 77.

`gcd` generalizes to non-integer numbers, but the result will also be
non-integer. If `gcd` is applied to an irrational number, then it should not
produce a result, but often it does -- the arithmetic is of arbitrary precision,
but is not of _infinite_ precision. A number whose order is similar to the
selected precision might well divide into the arguments without remainder.
Such a result will be unhelpful, of course.

See also: `precision`
