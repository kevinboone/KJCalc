/*==========================================================================
KJCALC
FunctionLoad.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 

/** 
  Load and evaluate a file. This class is initialized with a
  FileLoader, which must be itself be initialized with parser, 
  evaluator, etc
*/ 
public class FunctionLoad extends Function
  { 
  FileLoader fileLoader;

  public FunctionLoad (FileLoader loader)
    {
    super ("load", "parse and execute a file of expressions", 1);
    this.fileLoader = loader;
    }
  
  public ParseTree evaluate (SymbolTable table, ParseTreeList args,
       EvaluationContext context)
     throws EvaluationException
    {
    checkArgCount (args.size());

    ParseTree pt = args.get (0);
    if (pt instanceof ParseTreeVariable)
      {
      try 
        {
        String name = ((ParseTreeVariable)pt).getVariableName();
        Value v = fileLoader.load (name); 
        return v.toParseTree (table, context);
        }
      catch (Exception e)
        {
        throw new EvaluationException 
          (e.toString());
        }
      }
    else
      {
      throw new EvaluationException 
        ("Wrong kind of argument to angle() -- expected a name");
      }
    }
  }




