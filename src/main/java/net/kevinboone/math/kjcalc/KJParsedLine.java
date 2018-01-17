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

public class KJParsedLine implements org.jline.reader.ParsedLine 
  {
  String word;
  int wordCursor;
  int wordIndex;
  List<String> words;
  String line;
  int cursor;

  public KJParsedLine (String line, int cursor, String word, int wordIndex,
       List<String> words, int wordCursor)
    {
    this.line = line;
    this.cursor = cursor;
    this.word = word;
    this.wordIndex = wordIndex;
    this.words = words;
    this.wordCursor = wordCursor;
    }

  public String word() { return word; } 
  public int wordCursor() { return wordCursor; } 
  public int wordIndex() { return wordIndex; }
  public List<String> words() { return words; }
  public String line() { return line; }
  public int cursor() { return cursor; }
  }



