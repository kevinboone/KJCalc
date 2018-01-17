/*==========================================================================
KJCALC/plotting
PlotGUI.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc.plotting;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

/** 
  The main plotting user interface, without any WM stuff. 
*/ 
public class PlotGUI extends JPanel
  {
  PlotData plotData;
  PlotCanvas plotCanvas;
  PlotControls plotControls;

  PlotGUI (PlotData plotData)
    {
    this.plotData = plotData;
    plotCanvas = new PlotCanvas (this, plotData); 
    plotControls = new PlotControls (plotCanvas); 
    plotControls.setVisible (true);
    plotCanvas.setPreferredSize (new Dimension (400, 400));
    setLayout (new BorderLayout());
    add (plotCanvas, BorderLayout.CENTER);
    add (plotControls, BorderLayout.SOUTH);
    }


  public void clear()
    {
    plotCanvas.repaintAll();
    plotData.clear();
    plotControls.clear();
    }


  public void repaintAll()
    {
    plotCanvas.repaintAll();
    plotControls.clearCursorPos();
    }


  public void showCursorPos (double x, double y)
    {
    plotControls.showCursorPos (x, y);
    }

  }


