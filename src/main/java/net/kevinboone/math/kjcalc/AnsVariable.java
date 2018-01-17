/*==========================================================================
KJCALC
AnsVariable.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/

package net.kevinboone.math.kjcalc; 
import net.kevinboone.math.kjexpr.*;
import java.util.*;
import java.math.*;
  
/** A variable to hold the (dynamic) value of pi. The value must be
 *     determined at runtime, according to the current scale settings for
 *         arbitrary-length numbers
 *         */
public class AnsVariable extends Value
  { 
  Value ans = new NumberValue ("", "", "0");

  public AnsVariable()
    {
    super ("ans", "result of last evaluation");
    }
    
  public ParseTree toParseTree (SymbolTable table,
        EvaluationContext context)
    throws EvaluationException
      {
      return ans.toParseTree (table, context); 
      }

  public void setValue (Value value)
    {
    this.ans = value;
    }
  }
    

