/*==========================================================================
KJCALC
KJCompletionParser.java
Copyright (c)2017 Kevin Boone, GPL 3.0
==========================================================================*/
package net.kevinboone.math.kjcalc;
import net.kevinboone.math.kjexpr.*;
import org.jline.reader.*;
import java.util.*;
import java.io.*;

public class KJCompletionParser implements org.jline.reader.Parser 
  {

  public KJCompletionParser()
    {
    }

  public ParsedLine parse (String line, int cursor, ParseContext context) 
     throws SyntaxError 
    {
    try
      {
      Vector<Token> tokens = Tokenizer.getDefault().run (line, "stdin"); 
      Vector<String> words = new Vector<String>();
      String word = null;
      int wordIndex = 0;
      int wordCursor = 0;

      int i = 0;
      for (Token token : tokens)
	{
	String text = token.getText();
        // Note that the tokenizer's idea of columns is 1-based, 
        //   not zero-based.
	//int col = token.getCol() - 1;
	int col = token.getCol();
        //System.out.println ("i=" + i + " text= " +  
        //  text + " col=" + col + " curs="+ cursor);

	if (cursor >= col && cursor <= col + text.length())
	   {
	   //cursor is in this token
	   word = text;
	   wordIndex = i;
           //System.out.println ("word="+ word);
           //System.out.println ("index="+ wordIndex);
	   wordCursor = cursor - col;
	   }

	words.add (text);
	i++;
	}

      if (word == null)
	{
	word = "";
	wordIndex = tokens.size();
	wordCursor = 0;
	}

      return new KJParsedLine (line, cursor, word, 
          wordIndex, words, wordCursor);
      }
    catch (ParseException e)
      {
      throw new SyntaxError (0, 0, e.toString());
      }
    }

  }



