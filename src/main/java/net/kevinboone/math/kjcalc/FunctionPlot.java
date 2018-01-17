/*==========================================================================
KJCALC
FunctionPlot.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjcalc.plotting.*;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 

/** 
  Plot an expression
*/ 
public class FunctionPlot extends Function
  { 
  public FunctionPlot ()
    {
    super ("plot", "plot the value of an expression", 4);
    }


BigDecimal evaluateWith (ParseTree ptfn, String name, BigDecimal arg,
        SymbolTable table, EvaluationContext context)
      throws EvaluationException
    {
    Value v = new NumberValue ("", "", arg);

    Hashtable<String, Value> variables = table.getVariableTable();
    Hashtable<String, Value> newVariables = 
       new Hashtable<String, Value>(variables);

    v.setName (name);
    newVariables.put (name, v);

    table.setVariableTable(newVariables);

    ParseTree ptResult = ptfn.evaluate (table, context);

    table.setVariableTable (variables);

    if (ptResult instanceof ParseTreeNumber)
      return ((ParseTreeNumber)ptResult).toBigDecimal();
    else
      throw new EvaluationException
       ("Expression does not evaluate to a real number in " + getName() + "()");
    }

  
  public ParseTree evaluate (SymbolTable table, ParseTreeList args,
       EvaluationContext context)
     throws EvaluationException
    {
    checkArgCount (args.size());
    
    ParseTree expr = args.get (0);
    ParseTree ptName = args.get (1);
    
    if (!(ptName instanceof ParseTreeVariable))
      throw new EvaluationException
         ("Expected a variable name as the second argument to "
           + getName() + "()");

    ParseTree ptLow = args.get (2).evaluate (table, context);
    ParseTree ptHigh = args.get (3).evaluate (table, context);

    Value vLow = ptLow.toValue();
    Value vHigh = ptHigh.toValue();

    if (!(vLow instanceof NumberValue))
      throw new EvaluationException
         ("Third argument to " + getName() 
           + "() must evaluate to a real number");

    if (!(vHigh instanceof NumberValue))
      throw new EvaluationException
         ("Fourth argument to " + getName() 
           + "() must evaluate to a real number");


    BigDecimal high = ((NumberValue)vHigh).toBigDecimal();
    BigDecimal low = ((NumberValue)vLow).toBigDecimal();

    String name = ((ParseTreeVariable)ptName).getVariableName();

    PlotWindow pw = PlotWindow.getInstance();

    int numPoints = 100; // TODO
    double xMin = low.doubleValue(); 
    double xMax = high.doubleValue(); 
    
    double xRange = xMax - xMin; // TODO -- check range 

    double xStep = xRange / (double) numPoints;

    PlotPoint[] points = new PlotPoint[numPoints + 1];

    double x = xMin;
    boolean oneGoodPoint = false;
    for (int i = 0; i <= numPoints; i++)
      {
      BigDecimal arg = new BigDecimal (x);
      try
        {
        BigDecimal r = evaluateWith (expr, name, arg,
          table, context);
        PlotPoint p = new PlotPoint (x, r.doubleValue());
        points[i] = p;
        oneGoodPoint = true;
        }
      catch (EvaluationException e)
        {
        PlotPoint p = new PlotPoint (x, Double.NaN);
        points[i] = p;
        }
      x += xStep;
      }

    if (!oneGoodPoint)
      {
      throw new EvaluationException ("Expression does not evaluate to " 
         + "any plottable points in the specified range");
      }

    pw.addPlot (points); 

    pw.showIt();

    return new ParseTreeNumber (BigDecimal.ZERO);
    }
  }




