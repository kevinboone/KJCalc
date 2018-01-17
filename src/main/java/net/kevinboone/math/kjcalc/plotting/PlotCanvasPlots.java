/*==========================================================================
KJCALC/plotting
PlotCanvasPlots.java
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
  The display of the plot lines. Does not include axes or axis lines.  
*/ 
public class PlotCanvasPlots extends JPanel
  { 
  int yAxisWidth = 20;
  int xAxisHeight = 20;
  final PlotData plotData;
  final PlotCanvas plotCanvas;

  public PlotCanvasPlots (PlotCanvas _plotCanvas, PlotData _plotData)
    {
    this.plotData = _plotData;
    this.plotCanvas = _plotCanvas;

    addMouseMotionListener (new MouseMotionAdapter()
      {
      @Override
      public void mouseMoved(MouseEvent e)
        { 	
        if (!plotData.hasData()) return;
        Point p = e.getPoint();
        int px = p.x;
        int py = p.y;
        int w = getWidth();
        int h = getHeight();
        PlotRange r = plotData.getRange();

        double xRange = r.xMax - r.xMin;
        double yRange = r.yMax - r.yMin;

        double xScale = (double) w / xRange;
        double yScale = (double) h / yRange;
     
        double x = (double) px / xScale + r.xMin; 
        double y = (double) (h - py) / yScale + r.yMin; 
   
        //System.out.println ("x=" + x + ",y=" + y);

        plotCanvas.showCursorPos (x, y);
        }
      });
    }


  void drawAxes (Graphics g, int w, int h, PlotRange r)
    {
    if (!plotData.hasData()) return;

    // TODO check ranges
    double xRange = r.xMax - r.xMin;
    double yRange = r.yMax - r.yMin;
    

    // TODO color
    g.setColor (Color.BLACK);

    double xScale = (double) w / xRange;
    double yScale = (double) h / yRange;

    int ordinateX0 = 0;
    int ordinateX1 = w;
    int ordinateY0 = h - (int)(yScale * -r.yMin) - 1;
    int ordinateY1 = h - (int)(yScale * -r.yMin) - 1;

    g.drawLine (ordinateX0, ordinateY0, ordinateX1, ordinateY1);

    int abscissaX0 = (int)(xScale * -r.xMin);
    int abscissaX1 = (int)(xScale * -r.xMin);
    int abscissaY0 = 0;
    int abscissaY1 = h;

    g.drawLine (abscissaX0, abscissaY0, abscissaX1, abscissaY1);
    }

  
  void plotLine (Graphics g, PlotPoint[] line, int w, int h, PlotRange r) 
    {
    int l = line.length;
    //for (int i = 0; i < l; i++)
    //  System.out.println ("" + line[i].x + "," + line[i].y);

    double xRange = r.xMax - r.xMin;
    double yRange = r.yMax - r.yMin;
    
    // TODO check ranges

    double xScale = (double) w / xRange;
    double yScale = (double) h / yRange;

    for (int i = 1; i < l; i++)
      {
      double x0 = line[i-1].x;
      double y0 = line[i-1].y;
 
      double x1 = line[i].x;
      double y1 = line[i].y;
 
      double xOff0 = x0 - r.xMin;
      double yOff0 = y0 - r.yMin;

      double xOff1 = x1 - r.xMin;
      double yOff1 = y1 - r.yMin;

      g.drawLine ((int)(xOff0 * xScale), h - 1 - (int)(yOff0 * yScale), 
        (int)(xOff1 * xScale), h - 1-  (int)(yOff1 * yScale)); 
   
      //System.out.println ("" + xOff0 + " " + yOff0); 
      }
    } 

  protected void repaintAll()
    {
    int w = getWidth();
    int h = getHeight();
    repaint (0, 0, 0, w, h);
    }
 
  protected void paintComponent (Graphics g)
    {
    int w = getWidth();
    int h = getHeight();
    g.setColor (Color.WHITE);
    g.fillRect (0, 0, w, h); 

    PlotRange r = plotData.getRange();

    int i = 0;
    for (PlotPoint[] l : plotData.getLines())
      {
      switch (i % 3)
        {
        case 0: g.setColor (Color.RED); break;
        case 1: g.setColor (Color.GREEN); break;
        default: g.setColor (Color.BLUE); break;
        }
      plotLine (g, l, w, h, r);
      i++;
      }

    drawAxes (g, w, h, r);
    }
  }




