/*==========================================================================
KJCALC
CommandOutputHandler.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;

public interface CalcOutputHandler
  {
  public void stdout (String s);
  public void stderr (String s);
  public void setWidth (int width);
  }

