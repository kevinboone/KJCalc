/*==========================================================================
KJCALC/plotting
PlotWindow.java
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
  A Swing element for displaying plots
*/ 
public class PlotWindow extends JFrame 
  { 
  static PlotWindow self = null;
  PlotData plotData;
  PlotGUI plotGUI;

  public static PlotWindow getInstance()
    {
    if (self == null)
      self = new PlotWindow();
    return self;
    }


  public PlotWindow()
    {
    super ("KJCalc plot");
    addWindowListener (new WindowAdapter()
      {
      @Override
      public void windowClosed (WindowEvent e)
        {
        self = null;
        }
      });
    setDefaultCloseOperation (DISPOSE_ON_CLOSE);
    setLocation (20, 20);

    plotData = new PlotData();
    plotGUI = new PlotGUI (plotData);
    plotGUI.setPreferredSize (new Dimension (400, 400));
    getContentPane().add (plotGUI, BorderLayout.CENTER);
    pack();
    }


  public void clear ()
    {
    plotGUI.clear();
    }

  public void addPlot (PlotPoint[] p) 
    { 
    plotData.addPlot (p);
    plotGUI.repaintAll();
    }


  public void showIt()
    {
    setVisible (true);
    }


  }


