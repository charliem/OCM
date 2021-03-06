/* Generated By:JJTree&JavaCC: Do not edit this line. OCMParser.java */
package com.kissintellignetsystems.ocm.compiler.parser;

import com.kissintellignetsystems.ocm.compiler.*;

import java.io.*;
import java.util.*;

public class OCMParser/*@bgen(jjtree)*/implements OCMParserTreeConstants, OCMParserConstants {/*@bgen(jjtree)*/
  protected static JJTOCMParserState jjtree = new JJTOCMParserState();

  static final public SimpleNode parse() throws ParseException {
                                 /*@bgen(jjtree) #OCMSpec(true) */
  ASTOCMSpec jjtn000 = new ASTOCMSpec(JJTOCMSPEC);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      label_1:
      while (true) {
        ColumnFamily();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COLUMN_FAMILY:
        case LEFT_SQUARE_BRACKET:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
      }
    jjtree.closeNodeScope(jjtn000, true);
    jjtc000 = false;
    {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
    throw new Error("Missing return statement in function");
  }

  static final public void ColumnFamily() throws ParseException {
                                     /*@bgen(jjtree) ColumnFamily */
                                     ASTColumnFamily jjtn000 = new ASTColumnFamily(JJTCOLUMNFAMILY);
                                     boolean jjtc000 = true;
                                     jjtree.openNodeScope(jjtn000);Token t = null; Token i = null;
    try {
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LEFT_SQUARE_BRACKET:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_2;
        }
        jj_consume_token(LEFT_SQUARE_BRACKET);
        i = jj_consume_token(IDENTIFIER);
        jj_consume_token(EQUALS);
        t = jj_consume_token(LIT);
        jj_consume_token(RIGHT_SQUARE_BRACKET);
                jjtn000.setAnnotation(i.image, t.image);
      }
      jj_consume_token(COLUMN_FAMILY);
      t = jj_consume_token(IDENTIFIER);
      jj_consume_token(LEFT_BRACE);
      Field();
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case SUPER_FAMILY:
          SuperFamily();
          break;
        case DYNAMIC_SUPER_FAMILY:
          DynamicSuperFamily();
          break;
        case MANY_2_MANY:
          Many2Many();
          break;
        default:
          jj_la1[2] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case SUPER_FAMILY:
        case DYNAMIC_SUPER_FAMILY:
        case MANY_2_MANY:
          ;
          break;
        default:
          jj_la1[3] = jj_gen;
          break label_3;
        }
      }
      jj_consume_token(RIGHT_BRACE);
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
                jjtn000.setName(t.image);
    } catch (Throwable jjte000) {
    if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  static final public void Field() throws ParseException {
                       /*@bgen(jjtree) Field */
                        ASTField jjtn000 = new ASTField(JJTFIELD);
                        boolean jjtc000 = true;
                        jjtree.openNodeScope(jjtn000);Token t = null; Token i = null;
    try {
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LEFT_SQUARE_BRACKET:
          ;
          break;
        default:
          jj_la1[4] = jj_gen;
          break label_4;
        }
        jj_consume_token(LEFT_SQUARE_BRACKET);
        i = jj_consume_token(IDENTIFIER);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case EQUALS:
          jj_consume_token(EQUALS);
          t = jj_consume_token(LIT);
          jj_consume_token(RIGHT_SQUARE_BRACKET);
                        jjtn000.setAnnotation(i.image, t.image);
          break;
        case RIGHT_SQUARE_BRACKET:
          jj_consume_token(RIGHT_SQUARE_BRACKET);
                        jjtn000.setAnnotation(i.image);
          break;
        default:
          jj_la1[5] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEAN:
        t = jj_consume_token(BOOLEAN);
        break;
      case BYTE:
        t = jj_consume_token(BYTE);
        break;
      case BYTESTREAM:
        t = jj_consume_token(BYTESTREAM);
        break;
      case DOUBLE:
        t = jj_consume_token(DOUBLE);
        break;
      case FLOAT:
        t = jj_consume_token(FLOAT);
        break;
      case INT:
        t = jj_consume_token(INT);
        break;
      case LONG:
        t = jj_consume_token(LONG);
        break;
      case STRING:
        t = jj_consume_token(STRING);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      i = jj_consume_token(IDENTIFIER);
      jj_consume_token(TERM);
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
                jjtn000.setType(t.image);
                jjtn000.setName(i.image);
    } finally {
    if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
  }

  static final public void SuperFamily() throws ParseException {
                                  /*@bgen(jjtree) SuperFamily */
                                   ASTSuperFamily jjtn000 = new ASTSuperFamily(JJTSUPERFAMILY);
                                   boolean jjtc000 = true;
                                   jjtree.openNodeScope(jjtn000);Token t = null;
    try {
      jj_consume_token(SUPER_FAMILY);
      t = jj_consume_token(IDENTIFIER);
      jj_consume_token(LEFT_BRACE);
      label_5:
      while (true) {
        Field();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BOOLEAN:
        case BYTE:
        case BYTESTREAM:
        case DOUBLE:
        case FLOAT:
        case INT:
        case LONG:
        case STRING:
        case LEFT_SQUARE_BRACKET:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_5;
        }
      }
      jj_consume_token(RIGHT_BRACE);
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
                jjtn000.setName(t.image);
    } catch (Throwable jjte000) {
          if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  static final public void DynamicSuperFamily() throws ParseException {
                                                /*@bgen(jjtree) DynamicSuperFamily */
                                                 ASTDynamicSuperFamily jjtn000 = new ASTDynamicSuperFamily(JJTDYNAMICSUPERFAMILY);
                                                 boolean jjtc000 = true;
                                                 jjtree.openNodeScope(jjtn000);Token i = null; Token t = null;
    try {
      jj_consume_token(DYNAMIC_SUPER_FAMILY);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BOOLEAN:
        t = jj_consume_token(BOOLEAN);
        break;
      case BYTE:
        t = jj_consume_token(BYTE);
        break;
      case BYTESTREAM:
        t = jj_consume_token(BYTESTREAM);
        break;
      case DOUBLE:
        t = jj_consume_token(DOUBLE);
        break;
      case FLOAT:
        t = jj_consume_token(FLOAT);
        break;
      case INT:
        t = jj_consume_token(INT);
        break;
      case LONG:
        t = jj_consume_token(LONG);
        break;
      case STRING:
        t = jj_consume_token(STRING);
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      i = jj_consume_token(IDENTIFIER);
      jj_consume_token(TERM);
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
                jjtn000.setName(i.image);
                jjtn000.setType(t.image);
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  static final public void Many2Many() throws ParseException {
                              /*@bgen(jjtree) Many2Many */
                               ASTMany2Many jjtn000 = new ASTMany2Many(JJTMANY2MANY);
                               boolean jjtc000 = true;
                               jjtree.openNodeScope(jjtn000);Token i = null; Token c = null; Token f = null;
    try {
      jj_consume_token(MANY_2_MANY);
      c = jj_consume_token(IDENTIFIER);
      jj_consume_token(DOT);
      f = jj_consume_token(IDENTIFIER);
      i = jj_consume_token(IDENTIFIER);
      jj_consume_token(TERM);
          jjtree.closeNodeScope(jjtn000, true);
          jjtc000 = false;
                jjtn000.setColumnFamily(c.image);
                jjtn000.setField(f.image);

                jjtn000.setName(i.image);
    } finally {
          if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public OCMParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[9];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40200000,0x40000000,0xe0000,0xe0000,0x40000000,0x90000000,0x1fe00,0x4001fe00,0x1fe00,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }

  /** Constructor with InputStream. */
  public OCMParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public OCMParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new OCMParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public OCMParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new OCMParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public OCMParser(OCMParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(OCMParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 9; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List jj_expentries = new java.util.ArrayList();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[34];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 9; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 34; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
