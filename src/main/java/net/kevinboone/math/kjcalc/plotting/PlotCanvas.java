/*==========================================================================
KJCALC/plotting
PlotCanvas.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc.plotting;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 
import javax.swing.*; 
import java.awt.*; 

/** 
  A Swing element for displaying plots
*/ 
public class PlotCanvas extends JPanel
  { 
  PlotCanvasPlots plots;
  PlotLeftBorder plotLeftBorder;
  PlotBottomBorder plotBottomBorder;
  PlotData plotData;
  PlotGUI plotGUI;

  public PlotCanvas (PlotGUI plotGUI, PlotData plotData)
    {
    this.plotData = plotData;
    this.plotGUI = plotGUI;
    plotLeftBorder = new PlotLeftBorder (plotData);
    plotBottomBorder = new PlotBottomBorder (plotData);
    plots = new PlotCanvasPlots (this, plotData);
    plots.setPreferredSize (new Dimension (400, 400));
    plots.setVisible (true);
    plotLeftBorder.setVisible (true);
    plotBottomBorder.setVisible (true);
    setLayout (new BorderLayout());
    add (plots, BorderLayout.CENTER); 
    add (plotLeftBorder, BorderLayout.WEST);
    add (plotBottomBorder, BorderLayout.SOUTH);
    }


  protected void repaintAll()
    {
    plots.repaintAll ();
    plotLeftBorder.repaintAll ();
    plotBottomBorder.repaintAll ();
    }


  public void clear ()
    {
    plotGUI.clear();
    }


  public void showCursorPos (double x, double y)
    {
    plotGUI.showCursorPos (x, y); 
    }
  }



