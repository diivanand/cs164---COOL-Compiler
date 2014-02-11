/*
 * CS164: Spring 2014
 * Programming Assignment 2
 *
 * The scanner definition for Cool.
 *
 * Author: Diivanand Ramalingam
 * Co-Author: Min Yoon Jung
 */

import java_cup.runtime.Symbol;

%%

/* Code enclosed in %{ %} is copied verbatim to the lexer class definition.
 * All extra variables/methods you want to use in the lexer actions go
 * here.  Don't remove anything that was here initially.  */
%{
    // Max size of string constants
    static int MAX_STR_CONST = 1024;

    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();

    // For line numbers
    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }

    private AbstractSymbol filename;

    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }

    AbstractSymbol curr_filename() {
	return filename;
    }

    /*
     * Add extra field and methods here.
     */
    // For nested comment parenthesis balancing
    private int paren_len = 0;
    int get_paren_len() {
    return paren_len;
    }
    void reset_paren_len() {
        paren_len = 0;
    }
%}


/*  Code enclosed in %init{ %init} is copied verbatim to the lexer
 *  class constructor, all the extra initialization you want to do should
 *  go here. */
%init{
    // empty for now
%init}

/*  Code enclosed in %eofval{ %eofval} specifies java code that is
 *  executed when end-of-file is reached.  If you use multiple lexical
 *  states and want to do something special if an EOF is encountered in
 *  one of those states, place your code in the switch statement.
 *  Ultimately, you should return the EOF symbol, or your lexer won't
 *  work. */
%eofval{
    switch(yy_lexical_state) {
    case YYINITIAL:
      return new Symbol(TokenConstants.EOF);
/* If necessary, add code for other states here, e.g: */
    case LINE_COMMENT:
	   yybegin(YYINITIAL);
	   break;
    case BLOCK_COMMENT:
       yybegin(YYINITIAL);
       return new Symbol(TokenConstants.ERROR, "EOF in comment");
    case STRING_MODE:
       yybegin(YYINITIAL);
       string_buf = new StringBuffer();
       return new Symbol(TokenConstants.ERROR, "EOF in string constant");
    }
%eofval}

/* Do not modify the following two jlex directives */
%class CoolLexer
%cup

/* Jlex doesn't understand \v, so use {VTAB} instead. */
VTAB            = \x0b

/* Define names for regular expressions here. */
NEWLINE		= \n
WHITESPACE	= " "
BACKSPACE = \b
TAB = \t
FORMFEED = \f 
CARRIAGE = \r


/* This defines a new start condition for line comments.
 * .
 * Hint: You might need additional start conditions. */
%state LINE_COMMENT

/* This defines a new start condition for block comments. */
%state BLOCK_COMMENT

/* This defines a new start condition when an unescaped " 
 *   is encountered */
%state STRING_MODE

/* Define lexical rules after the %% separator.  There is some code
 * provided for you that you may wish to use, but you may change it
 * if you like.
 * .
 * Some things you must fill-in (not necessarily a complete list):
 *   + Handle (* *) comments.  These comments should be properly nested.
 *   + Some additional multiple-character operators may be needed.  One
 *     (DARROW) is provided for you.
 *   + Handle strings.  String constants adhere to C syntax and may
 *     contain escape sequences: \c is accepted for all characters c
 *     (except for \n \t \b \f) in which case the result is c.
 * .
 * The complete Cool lexical specification is given in the Cool
 * Reference Manual (CoolAid).  Please be sure to look there. */
%%

<YYINITIAL>{NEWLINE}	 { curr_lineno++; }
<YYINITIAL>[{WHITESPACE}]+ { /* do nothing just eat it up */ }
<YYINITIAL>[{TAB}]+ { /* do nothing just eat it up */ }
<YYINITIAL>[{BACKSPACE}]+ { /* do nothing just eat it up */ }
<YYINITIAL>[{FORMFEED}]+ { /* do nothing just eat it up */ }
<YYINITIAL>[{CARRIAGE}]+ { /* do nothing just eat it up */ }
<YYINITIAL>[{VTAB}]+ { /* do nothing just eat it up */ }

<YYINITIAL>"--"         { yybegin(LINE_COMMENT); }
<YYINITIAL>"(*"         { paren_len++; yybegin(BLOCK_COMMENT);}
<YYINITIAL>"*)"         { return new Symbol(TokenConstants.ERROR, "Unmatched *)");}
<YYINITIAL>"\""         { yybegin(STRING_MODE);}

<LINE_COMMENT>[^\n]*        { /* do nothing eat it up anything that's not a newline */ }
<LINE_COMMENT>\n        { curr_lineno++; yybegin(YYINITIAL); }


<BLOCK_COMMENT>"(*"     {/*handle potential nesting by keeping count*/ paren_len++;}
<BLOCK_COMMENT>"*)"     { 
                            paren_len--;
                            if(paren_len < 0) {
                                return new Symbol(TokenConstants.ERROR, "Unmatched *)");
                            }else if (paren_len == 0) {
                                /*the nesting was balanced and we are outside block comment*/
                                yybegin(YYINITIAL);
                            }else {
                                /*do nothing eat up character still inside nesting*/
                            }
                        }
<BLOCK_COMMENT>\n      { curr_lineno++; }
<BLOCK_COMMENT>.       { /* do nothing eat it up not doing .* because maximal munch will mess things up so one at a time instead */ }


<STRING_MODE>"\""         {
                            yybegin(YYINITIAL);
//                  System.out.println("quote matched");
                            if(string_buf.length() > MAX_STR_CONST) {
                              return new Symbol(TokenConstants.ERROR, "String constant too long");
                            }else {
                              String s = string_buf.toString();
                              string_buf = new StringBuffer();
 //                             System.out.println("string:"+s);
                              return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(s));
                            }
                          }
<STRING_MODE>\0[^"\""]*["\""] { //eat up all characters after null and stop at ending quote (eat it too)
                            yybegin(YYINITIAL);
                            string_buf = new StringBuffer();
                            return new Symbol(TokenConstants.ERROR, "String contains null character");
                          }
<STRING_MODE>\0[^"\""]*\n { //eat up all characters after null and stop at ending quote (eat it too)
                            yybegin(YYINITIAL);
                            string_buf = new StringBuffer();
                            return new Symbol(TokenConstants.ERROR, "String contains null character");
                          }
<STRING_MODE>\\[{WHITESPACE}{TAB}{BACKSPACE}{FORMFEED}{CARRIAGE}{VTAB}]*\n {
 //                 System.out.println("whitespace matched");
                                                                            string_buf = string_buf.append('\n');
                                                                            curr_lineno++;
                                                                          }

<STRING_MODE>\\\n { 
                System.out.println("new line regex matched");
                  yybegin(YYINITIAL);
                  string_buf = new StringBuffer();
                  curr_lineno++;
                  return new Symbol(TokenConstants.ERROR, "Unterminated string constant"); 
                }
<STRING_MODE>.  { 
                  string_buf = string_buf.append(yytext().charAt(0));
                }




<YYINITIAL>"=>"		{ return new Symbol(TokenConstants.DARROW); }
<YYINITIAL>"<="     { return new Symbol(TokenConstants.LE); }
<YYINITIAL>"<-"     { return new Symbol(TokenConstants.ASSIGN); }




<YYINITIAL>[0-9][0-9]*  { /* Integers */
                          return new Symbol(TokenConstants.INT_CONST,
					    AbstractTable.inttable.addString(yytext())); }





<YYINITIAL>[Cc][Aa][Ss][Ee]	{ return new Symbol(TokenConstants.CASE); }
<YYINITIAL>[Cc][Ll][Aa][Ss][Ss] { return new Symbol(TokenConstants.CLASS); }
<YYINITIAL>[Ee][Ll][Ss][Ee]  	{ return new Symbol(TokenConstants.ELSE); }
<YYINITIAL>[Ee][Ss][Aa][Cc]	{ return new Symbol(TokenConstants.ESAC); }
<YYINITIAL>f[Aa][Ll][Ss][Ee]	{ return new Symbol(TokenConstants.BOOL_CONST, Boolean.FALSE); }
<YYINITIAL>[Ff][Ii]             { return new Symbol(TokenConstants.FI); }
<YYINITIAL>[Ii][Ff]  		{ return new Symbol(TokenConstants.IF); }
<YYINITIAL>[Ii][Nn]             { return new Symbol(TokenConstants.IN); }
<YYINITIAL>[Ii][Nn][Hh][Ee][Rr][Ii][Tt][Ss] { return new Symbol(TokenConstants.INHERITS); }
<YYINITIAL>[Ii][Ss][Vv][Oo][Ii][Dd] { return new Symbol(TokenConstants.ISVOID); }
<YYINITIAL>[Ll][Ee][Tt]         { return new Symbol(TokenConstants.LET); }
<YYINITIAL>[Ll][Oo][Oo][Pp]  	{ return new Symbol(TokenConstants.LOOP); }
<YYINITIAL>[Nn][Ee][Ww]		{ return new Symbol(TokenConstants.NEW); }
<YYINITIAL>[Nn][Oo][Tt] 	{ return new Symbol(TokenConstants.NOT); }
<YYINITIAL>[Oo][Ff]		{ return new Symbol(TokenConstants.OF); }
<YYINITIAL>[Pp][Oo][Oo][Ll]  	{ return new Symbol(TokenConstants.POOL); }
<YYINITIAL>[Tt][Hh][Ee][Nn]   	{ return new Symbol(TokenConstants.THEN); }
<YYINITIAL>t[Rr][Uu][Ee]	{ return new Symbol(TokenConstants.BOOL_CONST, Boolean.TRUE); }
<YYINITIAL>[Ww][Hh][Ii][Ll][Ee] { return new Symbol(TokenConstants.WHILE); }



<YYINITIAL>"SELF_TYPE" {return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
<YYINITIAL>"self" {return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext()));}
<YYINITIAL>[A-Z][_A-Za-z0-9]* { /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
<YYINITIAL>[a-z][_A-Za-z0-9]* { /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }



<YYINITIAL>"+"			{ return new Symbol(TokenConstants.PLUS); }
<YYINITIAL>"/"			{ return new Symbol(TokenConstants.DIV); }
<YYINITIAL>"-"			{ return new Symbol(TokenConstants.MINUS); }
<YYINITIAL>"*"			{ return new Symbol(TokenConstants.MULT); }
<YYINITIAL>"="			{ return new Symbol(TokenConstants.EQ); }
<YYINITIAL>"<"			{ return new Symbol(TokenConstants.LT); }
<YYINITIAL>"."			{ return new Symbol(TokenConstants.DOT); }
<YYINITIAL>"~"			{ return new Symbol(TokenConstants.NEG); }
<YYINITIAL>","			{ return new Symbol(TokenConstants.COMMA); }
<YYINITIAL>";"			{ return new Symbol(TokenConstants.SEMI); }
<YYINITIAL>":"			{ return new Symbol(TokenConstants.COLON); }
<YYINITIAL>"("			{ return new Symbol(TokenConstants.LPAREN); }
<YYINITIAL>")"			{ return new Symbol(TokenConstants.RPAREN); }
<YYINITIAL>"@"			{ return new Symbol(TokenConstants.AT); }
<YYINITIAL>"}"			{ return new Symbol(TokenConstants.RBRACE); }
<YYINITIAL>"{"			{ return new Symbol(TokenConstants.LBRACE); }




.                { /*
                    *  This should be the very last rule and will match
                    *  everything not matched by other lexical rules.
                    */
                   return new Symbol(TokenConstants.ERROR, yytext());
                   //System.err.println("LEXER BUG - UNMATCHED: " + yytext()); 
                 }
