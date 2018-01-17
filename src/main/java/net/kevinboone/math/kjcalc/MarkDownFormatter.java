/*==========================================================================
KJCALC
MarkDownFormatter.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
import java.io.*;


public class MarkDownFormatter
  {
  final int STATE_START = 0;
  final int STATE_WHITE = 1;
  final int STATE_TEXT = 2;

  int outCol;
  int inCol;
  int state;
  int heading;
  boolean underline;
  boolean tt;
  boolean preformat;
  StringBuffer current;


  public MarkDownFormatter()
     {
     reset();
     }


  public static String escapeMarkdown (String s)
    {
    s = s.replace ("_", "__");
    return s; 
    }

  public void reset()
    {
    outCol = 0;
    inCol = 0;
    state = STATE_START;
    heading = 0;
    underline = false;
    tt = false;
    preformat = false;
    current = new StringBuffer();
    }


  public void flushCurrent(PrintStream f, int width)
    {
    int l = current.length();
    if (l == 0) return;
    if (outCol + l > width - 2)
      {
      f.print ("\n");
      outCol = 0;
      } 
    f.print (current);
    f.flush();
    outCol += l;
    current.delete (0, l);
    }


  public void newline (PrintStream f)
    {
    f.print ("\n");
    outCol = 0;
    }


  void outputMDToConsole (PrintStream f, int width, String s)
    {
    int l = s.length();

    char last = 0;
    for (int i = 0; i <= l; i++)
       {
       char c;
       if (i == l)
         {
         c = (char)-1;
         }
       else
         c = s.charAt(i);         

       // STATE_START
       
       if (state == STATE_START)
         {
         if (c == (char)-1)
             flushCurrent (f, width);
         else if (c == ' ' || c == '\t') 
           {
           f.print(" ");
           outCol = 1;
           inCol++;
           preformat = true;
           state = STATE_WHITE;
           }
         else if (c == '`') 
           {
           if (i < l - 1)
             {
             if (s.charAt(i + 1) == '`')
               {
               current.append ("`");
               inCol++;
               i++;
               }
             else
               {
               flushCurrent (f, width);
               if (tt)
                 f.print(ansi().reset());
               else
                 f.print(ansi().fg(GREEN));
               tt = !tt;
               inCol++;
               }
             }
           state = STATE_START;
           }
         else if (c == '\n') 
           {
           if (last == '\n')
             {
             f.print ("\n\n");
             outCol = 0;
             inCol++;
             }
           else
             {
             state = STATE_START;
             inCol++;
             }
           preformat = false;
           }
         else if (c == '_') 
           {
           if (i < l)
             {
             if (s.charAt(i + 1) == '_')
               {
               current.append ("_");
               inCol++;
               i++;
               }
             else
               {
               flushCurrent (f, width);
               if (underline)
                 f.print(ansi().a(Attribute.UNDERLINE_OFF));
               else
                 f.print(ansi().a(Attribute.UNDERLINE));
               underline = !underline;
               inCol++;
               }
             }
           state = STATE_TEXT;
           }
         else if (c == '*')
           {
           heading++;
           while (c == '*' && i < l)
             {
             i++;
             c = s.charAt(i);
             heading++;
             inCol++;
             }
           i--;
           state = STATE_TEXT;
           // TODO different heading strengths
           f.print(ansi().bold());
           }
         else if (c == (char)-1)
           {
           if (preformat) 
             {
             newline (f);
             preformat = false;
             }
           }
         else 
           {
           current.append (c);
           state = STATE_TEXT;
           inCol++;
           }
         }
  
       // STATE_TEXT

       else if (state == STATE_TEXT)
         {
         if (c == (char)-1)
             flushCurrent (f, width);
         else if (c == ' ' || c == '\t') 
           {
           flushCurrent (f, width);
           f.print (" ");
           outCol++; // TODO -- start of line
           state = STATE_WHITE;
           inCol++;
           }
         else if (c == '_') 
           {
           if (i < l)
             {
             if (s.charAt(i + 1) == '_')
               {
               current.append ("_");
               inCol++;
               i++;
               }
             else
               {
               flushCurrent (f, width);
               if (underline)
                 f.print(ansi().a(Attribute.UNDERLINE_OFF));
               else
                 f.print(ansi().a(Attribute.UNDERLINE));
               underline = !underline;
               inCol++;
               }
             }
           }
         else if (c == '`') 
           {
           if (i < l - 1)
             {
             if (s.charAt(i + 1) == '`')
               {
               current.append ("`");
               inCol++;
               i++;
               }
             else
               {
               flushCurrent (f, width);
               if (tt)
                 f.print(ansi().reset());
               else
                 f.print(ansi().fg(GREEN));
               tt = !tt;
               inCol++;
               }
             }
           }
         else if (c == '\n') 
           {
           flushCurrent (f, width);
           if (preformat)
             {
             newline (f);
             }
           else
             {
             f.print(" ");
             if (heading > 0)
               {
               f.print(ansi().reset());
               heading = 0;
               }
             outCol++;
             }
           inCol = 0;
           state = STATE_START;
           }
         else if (c == (char)-1)
           {
           newline (f);
           }
         else 
           {
           current.append (c);
           state = STATE_TEXT;
           inCol++;
           }
         }

       // STATE_WHITE

       else if (state == STATE_WHITE)
         {
         if (c == (char)-1)
           flushCurrent (f, width);
         else if (c == ' ' || c == '\t') 
           {
           if (preformat)
             {
             f.print (" ");
             outCol = 0;
             }
           state = STATE_WHITE;
           inCol++;
           }
         else if (c == '_') 
           {
           if (i < l)
             {
             if (s.charAt(i + 1) == '_')
               {
               current.append ("_");
               inCol++;
               i++;
               }
             else
               {
               if (underline)
                 f.print(ansi().a(Attribute.UNDERLINE_OFF));
               else
                 f.print(ansi().a(Attribute.UNDERLINE));
               underline = !underline;
               inCol++;
               }
             }
           }
         else if (c == '`') 
           {
           if (i < l)
             {
             if (s.charAt(i + 1) == '`')
               {
               current.append ("`");
               inCol++;
               i++;
               }
             else
               {
               flushCurrent (f, width);
               if (tt)
                 f.print(ansi().reset());
               else
                 f.print(ansi().fg(GREEN));
               tt = !tt;
               inCol++;
               }
             }
           }
         else if (c == '\n') 
           {
           if (preformat)
             {
             newline (f);
             }
           else
             {
             f.print (" ");
             }
           state = STATE_START;
           inCol=0;
           }
         else if (c == (char)-1)
           {
           if (preformat) 
             newline (f);
           }
         else 
           {
           current.append (c);
           state = STATE_TEXT;
           inCol++;
           }
         }
       last = c;
       }

    f.print(ansi().reset());
    newline (f);
    f.flush();
    }
  }



