/*==========================================================================
KJCALC
FunctionHelp.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 
import java.io.*; 


public class FunctionHelp extends Function implements StringArgFunction
  { 
  CalcOutputHandler outputHandler;

  public FunctionHelp (CalcOutputHandler coh)
    {
    super ("help", "get help on functions, etc", 1);
    this.outputHandler = coh;
    }


  public List<String> getArgs()
    {
    try
      {
      return getTopicList();
      } 
    catch (Exception e)
      {
      return new Vector<String>();
      }
    }

  private ClassLoader getContextClassLoader() 
    {
    return Thread.currentThread().getContextClassLoader();
    }


 private InputStream getResourceAsStream (String resource) 
      throws FileNotFoundException
    {
    final InputStream in
      = getContextClassLoader().getResourceAsStream (resource);

    InputStream is = getClass().getResourceAsStream (resource);
    if (is != null)
      return is;

    throw new FileNotFoundException (resource);
    }


  private List<String> getTopicList() 
      throws IOException 
    {
    List<String> topics = new ArrayList<String>();

    InputStream in = getResourceAsStream ("/help/topics.lst");
    BufferedReader br = new BufferedReader (new InputStreamReader (in));
    String resource;

     while((resource = br.readLine()) != null) 
        {
        topics .add( resource ); 
        }
    return topics;
    }


  public void showHelp (String topic)
    {
    if (topic == null)
      {
      try
        {
        List<String> topics = getTopicList ();
        outputHandler.stdout ("**KJCalc help\n\n"
          + "Help is available on the topics listed below. "
          + "For general information, enter `help(overview)`; "
          + "for a list of defined functions, enter `list(funcs)`; " 
          + "for a list of operators, enter `list(ops)`."); 
        outputHandler.stdout ("\n");
        outputHandler.stdout (topics.toString());
        }
      catch (Exception e)
        {
        outputHandler.stderr (e.toString());
        }
      }
    else
      {
      String url = "/help/" + topic + ".md";
      try
        {
        InputStream in = getResourceAsStream (url);
        BufferedReader br = new BufferedReader (new InputStreamReader (in));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null)
          {
          sb.append (line);
          sb.append ("\n");
          }
        String text = new String (sb);
        outputHandler.stdout (text);
        }
      catch (FileNotFoundException e)
        {
        outputHandler.stderr ("No help for topic '" + topic + "'");
        }
      catch (Exception e)
        {
        outputHandler.stderr (e.toString());
        }
      }
    }

  
  public ParseTree evaluate (SymbolTable symbolTable, ParseTreeList args,
       EvaluationContext context)
     throws EvaluationException
    {
    ParseTree ret = new ParseTreeNumber (BigDecimal.ZERO);
    if (args.size() == 0)
      {
      showHelp (null);
      return ret;
      }

    ParseTree pt = args.get (0);
    if (pt instanceof ParseTreeVariable)
      {
      String name = ((ParseTreeVariable)pt).getVariableName();
      showHelp (name);
      return ret;
      }
    else
      {
      throw new EvaluationException 
        ("Wrong kind of argument to list() -- expected funcs, vars, or ops");
      }
    }


  }





