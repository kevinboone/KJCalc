/*==========================================================================
KJCALC
FunctionDisp.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 

public class FunctionDisp extends Function implements StringArgFunction
  { 
  ValueFormatter formatter;

  public FunctionDisp (ValueFormatter formatter)
    {
    super ("disp", "set display mode -- deflt, raw, sci, eng, hex, rat", 1);
    this.formatter = formatter;
    }

  public List<String> getArgs()
    {
    Vector<String> list = new Vector<String>();
    list.add ("deflt");
    list.add ("raw");
    list.add ("sci");
    list.add ("eng");
    list.add ("hex");
    list.add ("rat");
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
      if (name.equalsIgnoreCase ("deflt"))
        {
        formatter.setMode (ValueFormatter.Mode.DEFAULT);
        return new ParseTreeNumber (BigDecimal.ZERO);
        }
      else if (name.equalsIgnoreCase ("raw"))
        {
        formatter.setMode (ValueFormatter.Mode.RAW);
        return new ParseTreeNumber (BigDecimal.ZERO);
        }
      else if (name.equalsIgnoreCase ("sci"))
        {
        formatter.setMode (ValueFormatter.Mode.SCI);
        return new ParseTreeNumber (BigDecimal.ZERO);
        }
      else if (name.equalsIgnoreCase ("eng"))
        {
        formatter.setMode (ValueFormatter.Mode.ENG);
        return new ParseTreeNumber (BigDecimal.ZERO);
        }
      else if (name.equalsIgnoreCase ("hex"))
        {
        formatter.setMode (ValueFormatter.Mode.HEX);
        return new ParseTreeNumber (BigDecimal.ZERO);
        }
      else if (name.equalsIgnoreCase ("rat"))
        {
        formatter.setMode (ValueFormatter.Mode.RAT);
        return new ParseTreeNumber (BigDecimal.ZERO);
        }
      else
        {
        throw new EvaluationException 
          ("Argument to disp() must be deflt, raw, sci, eng, hex, or rat");
        }
      }
    else
      {
      throw new EvaluationException 
        ("Wrong kind of argument to disp() -- expected a name");
      }
    }


  }


