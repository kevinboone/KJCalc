/*==========================================================================
KJCALC
ValueFormatter.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.Value;
import net.kevinboone.math.kjexpr.NumberValue;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import ch.obermuhlner.math.big.BigDecimalMath;

public class ValueFormatter 
  {
  static final BigDecimal ten = new BigDecimal ("10"); 
  public enum Mode {DEFAULT, RAW, SCI, ENG, HEX, RAT};

  Mode mode = Mode.DEFAULT;
  int sciBreak = 5;
  MathContext mc = new MathContext (10);


  public ValueFormatter.Mode getMode()
    {
    return mode;
    }


  public void setMode (ValueFormatter.Mode mode)
    {
    this.mode = mode;
    }
   

  /** Sets the MC for display purposes only -- 
      calculations are not affected. */
  public void setMC (MathContext mc)
    {
    this.mc = mc;
    }


  public void setSciBreak (int b)
    {
    sciBreak = b;
    }

   
  public int getSciBreak()
    {
    return sciBreak;
    }


  int findLastZero (String s)
    {
    int l = s.length();
    for (int i = l-1; i >= 0; i--)
      {
      if (s.charAt(i) != '0') return i + 1;
      }
    return -1;
    }
   

  String removeTrailingZerosAndPoint (String s)
    {
    int dot = s.indexOf ('.');
    if (dot < 0) return s; // No decimal point
    int zero = findLastZero (s);;
    if (zero < 0) return s; // No trailing zeros
    if (zero < dot) return s; // Avoid truncating 2.0 to 2.
    String ss = s.substring (0, zero);
    if (ss.endsWith ("."))
      return ss.substring (0, ss.length() - 1);
    return ss;
    }


  public String formatSci (BigDecimal v)
   {
   BigDecimal mant = BigDecimalMath.mantissa (v);
   int exp = BigDecimalMath.exponent (v);
   String mStr = "" + mant;
   mStr = removeTrailingZerosAndPoint (mStr); // Should we always do this?
   return mStr + "E" + exp; 
   }


  public String formatDefault (BigDecimal v)
   {
   BigDecimal mant = BigDecimalMath.mantissa (v);
   int exp = BigDecimalMath.exponent (v);
   if (exp > sciBreak || exp < - sciBreak)
     {
     String mStr = "" + mant;
     mStr = removeTrailingZerosAndPoint (mStr); // Should we always do this?
     return mStr + "E" + exp; 
     }
   else
     {
     return removeTrailingZerosAndPoint (v.toPlainString());
     }
   }

  
  public String formatRat (BigDecimal _v)
   {
   BigDecimal v = _v.add (BigDecimal.ZERO);

   //MathContext mc = new MathContext (20); // FRIG
   boolean minus = false;
   if (v.compareTo (BigDecimal.ZERO) < 0)
     {
     v = BigDecimal.ZERO.subtract (v);
     minus = true;
     }
  
   BigInteger i = v.toBigInteger();
   v = v.subtract (new BigDecimal (i));
   if (v.compareTo (BigDecimal.ZERO) == 0)
     {
     // It's an integer
     return formatDefault (_v);
     }

   BigDecimal n = BigDecimal.ONE; 
   BigDecimal d = BigDecimal.ONE; 

   int precExp = -mc.getPrecision() + 1;
   BigDecimal bdEps = BigDecimalMath.pow 
      (ten, new BigDecimal (precExp), mc);
   int iters = 0;
   boolean done = false;
   while (iters < 1000 && !done)
     {
     BigDecimal test = n.divide (d, mc);
     BigDecimal eps = test.subtract (v).abs();
     if (eps.abs().compareTo (bdEps) <= 0)
       {
       done = true; 
       }
    else
       {
       int comp = test.compareTo (v);
       if (comp > 0)
         {
         // Test too large, increase denom
         d = d.add (BigDecimal.ONE);
         }
       else if (comp < 0)
         {
         // Test too large, increase num 
         n = n.add (BigDecimal.ONE);
         }
       else
         {
         done = true;
         }
       }
     iters++;
     }
   
   if (done)
     {
     String s = "";
     if (i.compareTo (BigInteger.ZERO) > 0) s += i + " ";
     s += n + "/" + d;
     if (minus) s = "-" + s;
     return s;
     }
   else
     {
     return formatDefault (_v);
     }
   }

  
  public String formatHex (BigDecimal v)
    {
    try
      {
      BigInteger bi = v.toBigIntegerExact ();
      return "0x" + bi.toString(16);
      }
    catch (ArithmeticException e) 
      {
      return "(dec) " + formatDefault (v);
      }
    }


  public String formatEng (BigDecimal v)
   {
   BigDecimal mant = BigDecimalMath.mantissa (v);
   int exp = BigDecimalMath.exponent (v);
   int engExp; 
   if (exp >= 0)
     engExp = exp / 3 * 3;
   else
     engExp = (exp - 2 ) / 3 * 3;
   int expDiff = exp - engExp;

   String mStr = "" + mant.multiply (new BigDecimal(10).pow (expDiff));

   mStr = removeTrailingZerosAndPoint (mStr); // Should we always do this?
   String suffix;
   if (engExp == 0) 
      suffix = "";
   else if (engExp == 3) 
      suffix = "k";
   else if (engExp == 6) 
      suffix = "M";
   else if (engExp == 9) 
      suffix = "G";
   else if (engExp == 12) 
      suffix = "T";
   else if (engExp == -3) 
      suffix = "m";
   else if (engExp == -6) 
      suffix = "μ";
   else if (engExp == -6) 
      suffix = "n";
   else if (engExp == -6) 
      suffix = "p";
   else
      suffix = "E" + engExp;
   return mStr + suffix; 
   }


  public String format (Value value)
    {
    if (value instanceof NumberValue)
      {
      BigDecimal v = ((NumberValue)value).toBigDecimal();
      v = v.round (mc);

      if (false) // base
        {
        }
      else
        {
        if (mode == Mode.RAW)
          {
          return v.toString();
          }
        else if (mode == Mode.SCI)
          {
          return formatSci (v);
          }
        else if (mode == Mode.ENG)
          {
          return formatEng (v);
          }
        else if (mode == Mode.HEX)
          {
          return formatHex (v);
          }
        else if (mode == Mode.RAT)
          {
          return formatRat (v);
          }
        else
          {
          return formatDefault (v);
          }
        }
      return v.toString();
      } 
    else
       return value.toString();
    }

  }

