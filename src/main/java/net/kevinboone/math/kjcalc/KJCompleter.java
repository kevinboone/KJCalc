/*==========================================================================
KJCALC
KJCompleter.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.*;
import org.jline.reader.*;
import java.util.*;
import java.io.*;

public class KJCompleter implements Completer
  {
  SymbolTable table;

  public KJCompleter (SymbolTable table)
    {
    this.table = table;
    }


  List<String> getNames (SymbolTable table)
    {
    Vector<String> s = new Vector<String>();
    
    Collection<Function> functions = table.getFunctions(); 
    for (Function func: functions)
      {
      s.add (func.getName());
      }
    Collection<Value> values = table.getVariables(); 
    for (Value value : values)
      {
      String name = value.getName();
      if (name.length() > 0)
        s.add (name);
      }
    return s;
    }

  public void complete(LineReader reader, ParsedLine line, 
      List<Candidate> candidates)
    {
    String test = line.word();
   boolean done = false;

    if (line.wordIndex() >= 2)
      {
      Vector<String> v = (Vector<String>)line.words();
      String a0 = v.elementAt(line.wordIndex() - 2);
      String a1 = v.elementAt(line.wordIndex() - 1);
      // TODO
      Function f = table.getFunction (a0);
      if (f != null && a1.equals ("("))
        {
        if (f instanceof StringArgFunction)
          {
          List<String> args = ((StringArgFunction)f).getArgs();
          for (String arg : args)
            {  
            Candidate c = new Candidate (arg, arg, null, null, 
               null, null, true);
	    candidates.add (c);
            }
          done = true;
          }
        }
      }

    if (!done)
      {
      List<String> names = getNames (table);
      for (String name : names)
	{
	if (test == null || test.length() == 0 || name.startsWith (test))
	  {
	  Candidate c = new Candidate(name, name, null, null, null, null, true);
	  candidates.add (c);
	  }
	}
      }
    }
  }



