/*==========================================================================
KJCALC
FunctionList.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.*;
import java.math.*; 
import java.util.*; 


public class FunctionList extends Function implements StringArgFunction
  { 
  CalcOutputHandler outputHandler;

class FunctionComparator implements Comparator<Function> 
  {
  @Override
  public int compare (Function f1, Function f2) 
    {
    return f1.getName().compareTo (f2.getName());
    }
  }

class VarComparator implements Comparator<Value> 
  {
  @Override
  public int compare (Value f1, Value f2) 
    {
    return f1.getName().compareTo (f2.getName());
    }
  }


  public List<String> getArgs()
    {
    Vector<String> list = new Vector<String>();
    list.add ("funcs");
    list.add ("vars");
    list.add ("ops");
    return list;
    }
  

  void listAll (SymbolTable symbolTable)
    {
    outputHandler.stdout ("**Functions\n\n");
    listFuncs (symbolTable);
    outputHandler.stdout ("\n\n**Variables\n\n");
    listVars (symbolTable);
    outputHandler.stdout ("\n\n**Operators\n\n");
    listOps (symbolTable);
    }


  void listFuncs (SymbolTable symbolTable)
    {
    Collection<Function> functions = symbolTable.getFunctions();
    ArrayList<Function> list = new ArrayList<Function>(functions);
    Collections.sort (list, new FunctionComparator());
    for (Function f : list)
      {
      String s = String.format ("`%17s`   %s", 
        MarkDownFormatter.escapeMarkdown (f.getName()), f.getDesc());
      outputHandler.stdout (s);
      }
    }


  void listVars (SymbolTable symbolTable)
    {
    Collection<Value> values = symbolTable.getVariables();
    ArrayList<Value> list = new ArrayList<Value>(values);
    Collections.sort (list, new VarComparator());
    for (Value v : list)
      {
      String s = String.format ("`%17s`   %s",  
         MarkDownFormatter.escapeMarkdown (v.getName()), v.getDesc());
      outputHandler.stdout (s);
      }
    }


  void listOps (SymbolTable symbolTable)
    {
    Collection<BinaryOperator> operators = symbolTable.getBinaryOperators();
    for (BinaryOperator o : operators)
      {
      String s = String.format ("`%17s`   %s", o.getSymbol(), o.getDesc());
      outputHandler.stdout (s);
      }
    }


  public FunctionList (CalcOutputHandler coh)
    {
    super ("list", "list functions, variables, etc", 1);
    this.outputHandler = coh;
    }
  
  public ParseTree evaluate (SymbolTable symbolTable, ParseTreeList args,
       EvaluationContext context)
     throws EvaluationException
    {
    ParseTree ret = new ParseTreeNumber (BigDecimal.ZERO);
    if (args.size() == 0)
      {
      listAll (symbolTable);
      return ret;
      }

    ParseTree pt = args.get (0);
    if (pt instanceof ParseTreeVariable)
      {
      String name = ((ParseTreeVariable)pt).getVariableName();
      if (name.equalsIgnoreCase ("funcs"))
        {
        listFuncs (symbolTable);
        return ret; 
        }
      else if (name.equalsIgnoreCase ("vars"))
        {
        listVars (symbolTable);
        return ret; 
        }
      else if (name.equalsIgnoreCase ("ops"))
        {
        listOps (symbolTable);
        return ret; 
        }
      else
        {
        throw new EvaluationException 
          ("Argument to angle() must be funcs, vars, or ops");
        }
      }
    else
      {
      throw new EvaluationException 
        ("Wrong kind of argument to list() -- expected funcs, vars, or ops");
      }
    }


  }




