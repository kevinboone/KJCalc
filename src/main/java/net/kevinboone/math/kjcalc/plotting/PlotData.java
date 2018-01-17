/*==========================================================================
KJCALC/plotting
PlotData.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc.plotting;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 
import javax.swing.*; 

public class PlotData
  {
  PlotRange cachedRange = null; 

  Vector<PlotPoint[]> lines = new Vector<PlotPoint[]>();

  java.util.List<PlotPoint[]> getLines() { return lines; }

  public boolean hasData() { return lines.size() != 0; }

  public void clear()
    {
    lines = new Vector<PlotPoint[]>();
    cachedRange = null;
    }

  public void addPlot (PlotPoint[] points)
    {
    cachedRange = null;
    lines.add (points);
    }

  
  public PlotRange getRange()
    {
    if (cachedRange != null) return cachedRange;

    PlotRange r = new PlotRange();

    boolean gotXmin = false;
    boolean gotXmax = false;
    boolean gotYmin = false;
    boolean gotYmax = false;

    for (PlotPoint[] l : lines)
      {
      int ll = l.length;
      for (int j = 0; j < ll; j++)
        {
        double x = l[j].x;
        double y = l[j].y;
        if (!Double.isNaN(y))
          {
          if (!gotXmin || x < r.xMin) { r.xMin = x; gotXmin = true; }
          if (!gotXmax || x > r.xMax) { r.xMax = x; gotXmax = true; }
          if (!gotYmin || y < r.yMin) { r.yMin = y; gotYmin = true; }
          if (!gotYmax || y > r.yMax) { r.yMax = y; gotYmax = true; }
          }
        }
      }

    cachedRange = r;
    return r;
    }


  }


