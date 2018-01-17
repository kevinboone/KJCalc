/*==========================================================================
KJCALC
FunctionAngle.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 

public class FunctionAngle extends Function implements StringArgFunction
  { 
  public FunctionAngle ()
    {
    super ("angle", "set angle mode -- deg, rad", 1);
    }
  
  public List<String> getArgs()
    {
    Vector<String> list = new Vector<String>();
    list.add ("deg");
    list.add ("rad");
    return list;
    }
  
  public ParseTree evaluate (SymbolTable table, ParseTreeList args,
       EvaluationContext context)
     throws EvaluationException
    {
    checkArgCount (args.size());

    ParseTree pt = args.get (0);
    if (pt instanceof ParseTreeVariable)
      {
      String name = ((ParseTreeVariable)pt).getVariableName();
      if (name.equalsIgnoreCase ("deg"))
        {
        context.setAngleMode (EvaluationContext.AngleMode.DEG);
        return new ParseTreeNumber (BigDecimal.ZERO);
        }
      else if (name.equalsIgnoreCase ("rad"))
        {
        context.setAngleMode (EvaluationContext.AngleMode.RAD);
        return new ParseTreeNumber (BigDecimal.ZERO);
        }
      else
        {
        throw new EvaluationException 
          ("Argument to angle() must be deg or rad");
        }
      }
    else
      {
      throw new EvaluationException 
        ("Wrong kind of argument to angle() -- expected a name");
      }
    }


  }



