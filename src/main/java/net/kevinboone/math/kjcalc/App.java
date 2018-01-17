/*==========================================================================
KJCALC
App.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.Parser;
import net.kevinboone.math.kjexpr.ParseTree;
import net.kevinboone.math.kjexpr.Token;
import net.kevinboone.math.kjexpr.Tokenizer;
import net.kevinboone.math.kjexpr.Evaluator;
import net.kevinboone.math.kjexpr.EvaluationException;
import net.kevinboone.math.kjexpr.SymbolTable;
import net.kevinboone.math.kjexpr.CommonSymbolTable;
import net.kevinboone.math.kjexpr.Value;
import net.kevinboone.math.kjexpr.NumberValue;
import net.kevinboone.math.kjexpr.ParseException;
import org.jline.terminal.Terminal;
import org.jline.reader.UserInterruptException;
import org.jline.reader.EndOfFileException;
import org.jline.terminal.TerminalBuilder;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.LineReader;
import org.jline.reader.Completer;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.commons.cli.*;
import org.fusesource.jansi.AnsiConsole;


/**
   This is the main class for the KJCalc application.
*/
public class App
  {
  static final String VERSION = "0.0.2";
  Parser parser; 
  Evaluator evaluator;
  SymbolTable symbolTable;
  ValueFormatter formatter;
  CalcOutputHandler outputHandler;
  FileLoader fileLoader;
    
/**
   Constructor. Set up the parser, evaluator, formatter, symbol table,
     command processor, etc. Note that initialization is set up in two
     parts -- the formatter cannot be initialized until the symbol table
     is initialized, but some functions cannot be initailized until
     the formatter is initialized.
*/
  public App()
    {
    initKJExpr1();
    initFormatter();
    initKJExpr2();
    }


/**
  Initialize the parser and expression evaluator   
*/
  void initKJExpr1()
    {
    parser = new Parser();
    evaluator = new Evaluator();
    symbolTable = new CommonSymbolTable (parser); 
    evaluator.setSymbolTable (symbolTable);
    AnsVariable ans = new AnsVariable();
    symbolTable.addVariable (ans);
    symbolTable.addVariable (new NumberValue 
      ("eps", "desired precision for iterative operations", 
        new BigDecimal (0.00000001)));
    symbolTable.addVariable (new NumberValue 
      ("iters", "maximum tries for iterative operations", 
        new BigDecimal (100)));
    symbolTable.addVariable 
       (new NumberValue ("scibreak", 
          "Exponent above which scientific notation is used", 
             new BigDecimal ("8")));
    }


/**
  Initialize the parser and expression evaluator   
*/
  void initKJExpr2()
    {
    symbolTable.addFunction (new FunctionDisp(formatter));
    symbolTable.addFunction (new FunctionAngle());
    outputHandler = new CommandLineOutputHandler();
    symbolTable.addFunction (new FunctionList (outputHandler)); 
    symbolTable.addFunction (new FunctionHelp (outputHandler));
    fileLoader = new FileLoader (parser, evaluator,
      formatter, symbolTable);
    symbolTable.addFunction (new FunctionLoad (fileLoader));
    symbolTable.addFunction (new FunctionPlot());
    }


/**
  Initialize the result formatter 
*/
  void initFormatter()
    {
    formatter = new ValueFormatter();
    //formatter.setMC (evaluator.getMC());
    }


/**
  Helper function to get the value of the scibreak variable from
  the symbol table 
*/
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


/**
  Helper function to get the value of the precision variable from
  the symbol table 
*/
  int getPrecision (SymbolTable symbolTable)
    {
    int deflt = 30;
    Value _precision = symbolTable.getVariable ("precision");
    if (_precision != null)
      { 
      if (_precision instanceof NumberValue)
        {
        NumberValue precision = (NumberValue)_precision;
        return precision.toBigDecimal().intValue();
        }
      else
        return deflt;
      }
    else
      return deflt;
    }


/**
  Tokenize and parse a single line 
*/
  boolean doLine (String line, String filename)
    {
    boolean quit = false;
    try
      {
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
	    // This section removed
	    }
	  else
	    {
	    try
	      {
	      ParseTree pt = parser.parseTokens (tokens, "stdin");
	      //System.out.println ("pt=" + pt);
	      formatter.setSciBreak (getSciBreak(symbolTable));
	      int prec = getPrecision(symbolTable);
	      formatter.setMC (new MathContext (prec - 1));
              evaluator.setPrecision (prec);
	      Value result = evaluator.evaluate (pt);
	      System.out.println (formatter.format (result));
	      AnsVariable ans = (AnsVariable)symbolTable.getVariable ("ans");
	      ans.setValue (result);
	      }
	    catch (EvaluationException e) 
	      {
	      System.err.println (e.getMessage());
	      } 
	    }
	  }
	}
      }
    catch (ParseException e)
      {
      System.err.println (e);
      }
    return quit;
    }


  public static void showVersion()
    {
    System.out.println ("KJExpr/KJCalc " + VERSION);
    System.out.println ("Expression evaluator version " + Parser.VERSION);
    System.out.println ("User interface version " + VERSION);
    System.out.println ("Copyright (c)2017 Kevin Boone");
    System.out.println 
      ("Distributed under the terms of the GNU Public Licence, v3.0");
    }


  /**
    Run the calculator in interactive mode
  */
  void interactive()
      throws IOException
    {
    Completer completer = new KJCompleter (symbolTable);
    org.jline.reader.Parser completionParser = new KJCompletionParser();
    AnsiConsole.systemInstall();
    Terminal terminal = TerminalBuilder.terminal();
    LineReader reader = LineReaderBuilder.builder()
        .terminal (terminal)
        .appName("KJCalc")
        .completer (completer)
        .parser (completionParser)
        .build();


    String historyFile = System.getProperty ("user.home") +   
      File.separator + ".kjcalc.history";

    reader.setVariable ("history-file", historyFile);
    reader.unsetOpt (LineReader.Option.INSERT_TAB);

    boolean quit = false;

    System.out.println ("KJCalc version " + VERSION + ". Enter 'help()' for help,");
    while (!quit) 
      {
      outputHandler.setWidth (terminal.getWidth());
      String line = null;
      String prompt = "> ";
      try 
        {
        line = reader.readLine (prompt);
        if (doLine (line, "stdin")) quit = true;
        }
      catch (UserInterruptException e) 
        {
        quit = true;
        } 
      catch (EndOfFileException e) 
        {
        quit = true;
        } 
      }
    AnsiConsole.systemUninstall();
    }


  /**
   It all starts here
  */
  public static void main (String[] args)
      throws Exception
    {
    Options options = new Options();
    options.addOption ("h", "help", false, 
       "show brief help");
    options.addOption ("v", "version", false, 
       "show version and copyright");
    CommandLineParser clp = new GnuParser();
    CommandLine cl = null; 
    cl = clp.parse (options, args);

    if (cl.hasOption ("help"))
      {
      System.out.println 
        ("For help, run the program and type 'help()' at the prompt");
      return;
      }

    if (cl.hasOption ("version"))
      {
      showVersion ();
      return;
      }

    App app = new App();

    String rcFile = System.getProperty ("user.home") +   
      File.separator + ".kjcalc.rc";

    try
      {
      app.fileLoader.load (rcFile); 
      }
    catch (FileNotFoundException e)
      {
      // Do nothing -- it is not necessary to have an rc file
      }

    List<String> _realArgs = cl.getArgList();
    String[] realArgs = _realArgs.toArray (new String[0]);
    if (args.length > 0)
      {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < args.length; i++)
        {
        sb.append (args[i]);
        sb.append (" ");
        }
      app.doLine (new String(sb), "cmdline");
      }
    else
      app.interactive();
    System.exit (0);
    }
  }



