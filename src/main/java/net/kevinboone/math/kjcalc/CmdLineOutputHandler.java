/*==========================================================================
KJCALC
CmdLineOutputHandler.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import org.apache.commons.cli.*;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
import java.io.*;


class CommandLineOutputHandler implements CalcOutputHandler
  {
  int width = 80;

  public void setWidth (int width)
    {
    this.width = width;
    }

  public void stdout (String s)
     {
     MarkDownFormatter mdf = new MarkDownFormatter();
     mdf.outputMDToConsole (System.out, width, s);
     }


  public void stderr (String s)
     {
     MarkDownFormatter mdf = new MarkDownFormatter();
     mdf.outputMDToConsole (System.err, width, s);
     }
  }



