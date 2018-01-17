/*==========================================================================
KJCALC/plotting
PlotControls.java
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
  Controls shown underneath the plots 
*/ 
public class PlotControls extends JPanel
  { 
  protected PlotCanvas pc;
  protected JLabel cursorPosLabel;

  public PlotControls (PlotCanvas _pc)
    {
    this.pc = _pc;

    JButton b = new JButton ("Clear");
    b.addActionListener (new ActionListener()
      {
      @Override
      public void actionPerformed (ActionEvent e)
        {
        pc.clear();
        }
      });
    setLayout (new FlowLayout (FlowLayout.LEFT));
    add (b);
    b.setVisible (true);
    cursorPosLabel = new JLabel ("");
    add (cursorPosLabel);
    cursorPosLabel.setVisible (true);
    clearCursorPos();
    }


  public void showCursorPos (double x, double y)
    {
    String s = String.format ("(%2.3G,%2.3G)", x, y);
    cursorPosLabel.setText (s);
    }


  public void clearCursorPos()
    {
    cursorPosLabel.setText ("(?,?)");
    }


  public void clear()
    {
    clearCursorPos();
    }
  }
