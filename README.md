# KJCalc

Version 0.0.2

## What is this?

KJCalc is a command-line calculator, implemented in Java, that uses
arbitrary-precision decimal arithmetic. It should work on any platform
that provides a Java runtime compatible with JDK 1.7 or later. 
It is based on the KJExpr math parser library, maintained by the same
author, and available from github.

KJCalc has a substantial built-in function library, including rudimentary
equation-solving facilities. It supports plotting of functions
(of one variable) on computers with a graphical user interface. However,
it won't complain if you try to use it on a non-graphical system, for
ordinary text operations.

KJCalc uses the JAnsi library for interactive line editing and history
management, on systems that support this. 

## Building and running KJCalc

Binary versions of KJCalc are available from the author's website,
and other places. These typically take the form of an executable
Java JAR file, and a script to run it. The JAR file contains the
Java code, function definitions, documentation, etc, so no particular
method of installation needs to be followed.

To build from source, you will need Maven. This Maven operation:

```
$ mvn package
```

will build the JAR file in the ``target/`` directory. Just
copy this to any convenient location.

Please note that KJCalc depends on KJExpr, which must also be installed
using Maven. KJExpr should be available from the same place you got
KJCalc from.

To run the calculator:

```
$ java -jar /path/to/kjcalc-0.0.1-jar-with-dependencies.jar
```

Of course, you'll need to adjust the path to match the location of the
JAR. No specific installation is needed beyond this.

Within the program, you can enter ``help()`` for more information.
Please note that the built-in documentation is reasonably extensive,
and there is (so far) no documentation other than this.


## Initialization file

On startup, KJCalc reads a file of expressions called ``.kjcalc.rc`` from the
user's home directory. This file is a good place to set default values for
``precision``, ``scibreak``, etc. The home directory is usually pretty clear in
Linux/Unix systems, but is less obvious on Windows. Worse, the location on
Windows varies between Java runtime versions. If the file is not present, that
will not be considered a  problem.

## Author

KJCalc/KJExpr is mainted by Kevin Boone (kevin at railwayterrace dot com). 
It is distributed under the terms of the GNU Public License, version 3.0,
in the hope that it will be useful. There is no warranty of any kind.

