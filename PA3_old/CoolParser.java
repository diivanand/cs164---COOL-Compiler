
//----------------------------------------------------
// The following code was generated by CUP v0.10k
// Sun Feb 23 04:20:49 PST 2014
//----------------------------------------------------

import java_cup.runtime.*;

/** CUP v0.10k generated parser.
  * @version Sun Feb 23 04:20:49 PST 2014
  */
public class CoolParser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public CoolParser() {super();}

  /** Constructor which sets the default scanner. */
  public CoolParser(java_cup.runtime.Scanner s) {super(s);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\033\000\002\003\003\000\002\002\004\000\002\003" +
    "\003\000\002\004\003\000\002\004\004\000\002\005\010" +
    "\000\002\005\012\000\002\006\002\000\002\007\005\000" +
    "\002\007\005\000\002\007\005\000\002\007\005\000\002" +
    "\007\004\000\002\007\005\000\002\007\005\000\002\007" +
    "\005\000\002\007\004\000\002\007\005\000\002\007\003" +
    "\000\002\007\003\000\002\007\003\000\002\007\003\000" +
    "\002\007\004\000\002\010\004\000\002\010\005\000\002" +
    "\011\003\000\002\011\005" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\023\000\006\003\006\004\010\001\002\000\006\002" +
    "\001\004\010\001\002\000\004\002\024\001\002\000\004" +
    "\002\uffff\001\002\000\006\002\ufffe\004\ufffe\001\002\000" +
    "\004\055\011\001\002\000\006\011\013\050\012\001\002" +
    "\000\004\051\ufffa\001\002\000\004\055\014\001\002\000" +
    "\004\050\015\001\002\000\004\051\ufffa\001\002\000\004" +
    "\051\017\001\002\000\004\043\020\001\002\000\006\002" +
    "\ufffb\004\ufffb\001\002\000\004\051\022\001\002\000\004" +
    "\043\023\001\002\000\006\002\ufffc\004\ufffc\001\002\000" +
    "\004\002\000\001\002\000\006\002\ufffd\004\ufffd\001\002" +
    "" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\023\000\010\003\004\004\003\005\006\001\001\000" +
    "\004\005\024\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\006\020\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\006\015\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$CoolParser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$CoolParser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$CoolParser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



    int omerrs = 0;

    public void syntax_error(Symbol cur_token) {
        int lineno = action_obj.curr_lineno();
	String filename = action_obj.curr_filename().getString();
        System.err.print("\"" + filename + "\", line " + lineno + 
		         ": parse error at or near ");
        Utilities.printToken(cur_token);
	omerrs++;
	if (omerrs>50) {
	   System.err.println("More than 50 errors");
	   System.exit(1);
	}
    }

    public void unrecovered_syntax_error(Symbol cur_token) {
    }

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$CoolParser$actions {

 

    int curr_lineno() {
	return ((CoolTokenLexer)parser.getScanner()).curr_lineno();
    }

    AbstractSymbol curr_filename() {
	return ((CoolTokenLexer)parser.getScanner()).curr_filename();
    }


  private final CoolParser parser;

  /** Constructor */
  CUP$CoolParser$actions(CoolParser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$CoolParser$do_action(
    int                        CUP$CoolParser$act_num,
    java_cup.runtime.lr_parser CUP$CoolParser$parser,
    java.util.Stack            CUP$CoolParser$stack,
    int                        CUP$CoolParser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$CoolParser$result;

      /* select the action based on the action number */
      switch (CUP$CoolParser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 26: // exp_list ::= exp_list COMMA expr 
            {
              Expressions RESULT = null;
		Expressions el = (Expressions)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = el.appendElement(e); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(7/*exp_list*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 25: // exp_list ::= expr 
            {
              Expressions RESULT = null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = (new Expressions(curr_lineno())).appendElement(e); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(7/*exp_list*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 24: // actuals ::= LPAREN exp_list RPAREN 
            {
              Expressions RESULT = null;
		Expressions el = (Expressions)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		 RESULT = el; 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(6/*actuals*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 23: // actuals ::= LPAREN RPAREN 
            {
              Expressions RESULT = null;
		 RESULT = new Expressions(curr_lineno()); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(6/*actuals*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 22: // expr ::= OBJECTID actuals 
            {
              Expression RESULT = null;
		AbstractSymbol n = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		Expressions a = (Expressions)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new dispatch(curr_lineno(),
	                             new object(curr_lineno(), 
	                                        AbstractTable.idtable.addString("self")),
				     n, a); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 21: // expr ::= OBJECTID 
            {
              Expression RESULT = null;
		AbstractSymbol o = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new object(curr_lineno(), o); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // expr ::= BOOL_CONST 
            {
              Expression RESULT = null;
		Boolean b = (Boolean)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new bool_const(curr_lineno(), b); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // expr ::= STR_CONST 
            {
              Expression RESULT = null;
		AbstractSymbol s = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new string_const(curr_lineno(), s); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // expr ::= INT_CONST 
            {
              Expression RESULT = null;
		AbstractSymbol i = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new int_const(curr_lineno(), i); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // expr ::= LPAREN expr RPAREN 
            {
              Expression RESULT = null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		 RESULT = e; 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // expr ::= NOT expr 
            {
              Expression RESULT = null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new comp(curr_lineno(), e); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // expr ::= expr LE expr 
            {
              Expression RESULT = null;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new leq(curr_lineno(), e1, e2); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // expr ::= expr EQ expr 
            {
              Expression RESULT = null;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new eq(curr_lineno(), e1, e2); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // expr ::= expr LT expr 
            {
              Expression RESULT = null;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new lt(curr_lineno(), e1, e2); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // expr ::= NEG expr 
            {
              Expression RESULT = null;
		Expression e = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new neg(curr_lineno(), e); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // expr ::= expr DIV expr 
            {
              Expression RESULT = null;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new divide(curr_lineno(), e1, e2); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // expr ::= expr MULT expr 
            {
              Expression RESULT = null;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new mul(curr_lineno(), e1, e2); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // expr ::= expr MINUS expr 
            {
              Expression RESULT = null;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new sub(curr_lineno(), e1, e2); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // expr ::= expr PLUS expr 
            {
              Expression RESULT = null;
		Expression e1 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		Expression e2 = (Expression)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new plus(curr_lineno(), e1, e2); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(5/*expr*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // optional_feature_list ::= 
            {
              Features RESULT = null;
		 RESULT = new Features(curr_lineno()); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(4/*optional_feature_list*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // class ::= CLASS TYPEID INHERITS TYPEID LBRACE optional_feature_list RBRACE SEMI 
            {
              class_c RESULT = null;
		AbstractSymbol n = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-6)).value;
		AbstractSymbol p = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-4)).value;
		Features f = (Features)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		 RESULT = new class_c(curr_lineno(), n, p, f, curr_filename()); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(3/*class*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // class ::= CLASS TYPEID LBRACE optional_feature_list RBRACE SEMI 
            {
              class_c RESULT = null;
		AbstractSymbol n = (AbstractSymbol)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-4)).value;
		Features f = (Features)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-2)).value;
		 RESULT = new class_c(curr_lineno(), n, 
		                    AbstractTable.idtable.addString("Object"), 
				    f, curr_filename()); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(3/*class*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // class_list ::= class_list class 
            {
              Classes RESULT = null;
		Classes cl = (Classes)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		class_c c = (class_c)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = cl.appendElement(c); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(2/*class_list*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // class_list ::= class 
            {
              Classes RESULT = null;
		class_c c = (class_c)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = (new Classes(curr_lineno())).appendElement(c); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(2/*class_list*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // program ::= error 
            {
              programc RESULT = null;
		 RESULT = new programc(curr_lineno(),
                                     new Classes(curr_lineno())); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(1/*program*/, RESULT);
            }
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= program EOF 
            {
              Object RESULT = null;
		programc start_val = (programc)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-1)).value;
		RESULT = start_val;
              CUP$CoolParser$result = new java_cup.runtime.Symbol(0/*$START*/, RESULT);
            }
          /* ACCEPT */
          CUP$CoolParser$parser.done_parsing();
          return CUP$CoolParser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // program ::= class_list 
            {
              programc RESULT = null;
		Classes cl = (Classes)((java_cup.runtime.Symbol) CUP$CoolParser$stack.elementAt(CUP$CoolParser$top-0)).value;
		 RESULT = new programc(curr_lineno(), cl); 
              CUP$CoolParser$result = new java_cup.runtime.Symbol(1/*program*/, RESULT);
            }
          return CUP$CoolParser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}
