/*==========================================================================
KJCALC/plotting
PlotRange.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc.plotting;
import net.kevinboone.math.kjexpr.*;


public class PlotRange 
  {
  public double xMin;
  public double xMax;
  public double yMin;
  public double yMax;


  public PlotRange ()
    {
    }


  public PlotRange (double xMin, double xMax, double yMin, double yMax)
    {
    this.xMin = xMin;
    this.xMax = xMax;
    this.yMin = yMin;
    this.yMax = yMax;
    }
  }

