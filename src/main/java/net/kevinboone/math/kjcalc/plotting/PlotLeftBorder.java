/*==========================================================================
KJCALC/plotting
PlotLeftBorder.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc.plotting;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 
import javax.swing.*; 
import java.awt.*; 

/** 
*/ 
public class PlotLeftBorder extends JPanel
  { 
  PlotData plotData;

  public PlotLeftBorder (PlotData plotData) 
    {
    String ss = String.format ("%2.3G", -99999.9E9);
    setPreferredSize (new Dimension (getTextWidth (ss) + 2, 50));
    this.plotData = plotData;
    }

  protected void repaintAll()
    {
    int w = getWidth();
    int h = getHeight();
    repaint (0, 0, 0, w, h);
    }


  int getTextWidth (String s)
    {
    Font font = getFont();
    FontMetrics fm = getFontMetrics (font);
    char[] cc = s.toCharArray();
    int l = cc.length;
    int w = fm.charsWidth (cc, 0, l); 
    return w; 
    }
 

  int getTextHeight ()
    {
    Font font = getFont();
    FontMetrics fm = getFontMetrics (font);
    int h = fm.getHeight();
    return h; 
    }
 

  protected void paintComponent (Graphics g)
    {
    int w = getWidth();
    int h = getHeight();
    g.setColor (Color.WHITE);
    g.fillRect (0, 0, w, h); 

    if (plotData.hasData())
      {
      PlotRange r = plotData.getRange();

      g.setColor (Color.BLACK);
      String ss = String.format ("%2.3G", r.yMax);
      char[] cc = ss.toCharArray();
      int l = cc.length;
      g.drawChars (cc, 0, l, 2, getTextHeight() + 2);

      ss = String.format ("%2.3G", r.yMin);
      cc = ss.toCharArray();
      l = cc.length;
      g.drawChars (cc, 0, l, 2, h - 2);
      }
    }
  }




