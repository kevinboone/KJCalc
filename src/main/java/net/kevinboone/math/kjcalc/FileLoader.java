/*==========================================================================
KJCALC
FileLoader.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 
import java.io.*; 


public class FileLoader
  { 
  Parser parser;
  ValueFormatter formatter;
  SymbolTable symbolTable;
  Evaluator evaluator;

  // I don't particular like that this method is duplicate in App; but
  //  I don't want to introduce any more dependencies between
  //  these classes.
  int getSciBreak (SymbolTable symbolTable)
    {
    int deflt = 8;
    Value _sciBreakValue = symbolTable.getVariable ("scibreak");
    if (_sciBreakValue != null)
      { 
      if (_sciBreakValue instanceof NumberValue)
        {
        NumberValue sciBreakValue = (NumberValue)_sciBreakValue;
        return sciBreakValue.toBigDecimal().intValue();
        }
      else
        return deflt;
      }
    else
      return deflt;
    }

  public FileLoader (Parser parser, Evaluator evaluator, 
        ValueFormatter formatter, SymbolTable symbolTable)
    {
    this.parser = parser;
    this.evaluator = evaluator;
    this.formatter = formatter;
    this.symbolTable = symbolTable;
    }

  /**
    Load and evaluate a file, line by line. The final result of
    this function is the result (in the form of a Value) of the
    final line
  */
  public Value load (String filename)
      throws EvaluationException, ParseException, IOException
    {
    Value ret = new NumberValue ("return", "", BigDecimal.ZERO);
    FileInputStream fis = new FileInputStream (filename);
    BufferedReader br = new BufferedReader (new InputStreamReader (fis)); 
    String line;
    boolean quit = false;
    int lnum = 0;
    while ((line = br.readLine()) != null && !quit)
      {
      lnum++;
      Vector<Token> tokens = Tokenizer.getDefault().run (line, filename);

      if (tokens.size() > 0)
        {
        String t0 = tokens.elementAt(0).getText();
        if (t0.equalsIgnoreCase ("quit")
             || t0.equalsIgnoreCase ("exit"))
           quit = true;
        else
          {
          if (false)
            {
            }
          else
            {
            try
              {
              ParseTree pt = parser.parseTokens (tokens, filename);
              formatter.setSciBreak (getSciBreak(symbolTable));
              ret = evaluator.evaluate (pt);
              }
            catch (ParseException e) 
              {
              e.setLine (lnum);
              throw e;
              } 
            }
          }
        }
      }

    fis.close();

    return ret; 
    }
  }





