/*==========================================================================
KJCALC/plotting
PlotBottomBorder.java
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
public class PlotBottomBorder extends JPanel
  { 
  PlotData plotData;

  public PlotBottomBorder (PlotData plotData) 
    {
    setPreferredSize (new Dimension (50, getTextHeight ()));
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

    PlotRange r = plotData.getRange();

    if (plotData.hasData())
      {
      g.setColor (Color.BLACK);

      String ss = String.format ("%2.3G", -99999.9E9);
      int lBorderWidth = getTextWidth (ss) + 2; 

      ss = String.format ("%2.3G", r.xMin);
      char[] cc = ss.toCharArray();
      int l = cc.length;
      g.drawChars (cc, 0, l, lBorderWidth, getTextHeight() - 2);

      ss = String.format ("%2.3G", r.xMax);
      cc = ss.toCharArray();
      l = cc.length;
      g.drawChars (cc, 0, l, w - 2 - getTextWidth (ss), getTextHeight() - 2);
      }
    }
  }





