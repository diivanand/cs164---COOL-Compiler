// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////



import java.util.Enumeration;
import java.io.PrintStream;
import java.util.Vector;
import java.util.*;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract AbstractSymbol getName();
    public abstract AbstractSymbol getParent();
    public abstract AbstractSymbol getFilename();
    public abstract Features getFeatures();

}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

	public abstract void semant(ClassTable c, class_c curr, PrintStream errorReporter);
}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;                                 
    public AbstractSymbol get_type() { return type; }           
    public Expression set_type(AbstractSymbol s) { type = s; return this; } 
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
            { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
            { out.println(Utilities.pad(n) + ": _no_type"); }
    }
    public abstract void semant(ClassTable c, class_c curr, PrintStream errorReporter);

}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'programc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    /** Creates "programc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
	    ((Class_)e.nextElement()).dump_with_types(out, n + 2);
        }
    }
    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
	<p>
        Your checker should do the following two things:
	<ol>
	<li>Check that the program is semantically correct
	<li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
	</ol>
	<p>
	You are free to first do (1) and make sure you catch all semantic
    	errors. Part (2) can be done in a second stage when you want
	to test the complete compiler.
    */
    public void semant() {
	/* ClassTable constructor may do some semantic analysis */
	ClassTable classTable = new ClassTable(classes);
	PrintStream errorReporter;
	/* some semantic analysis code may go here */

	//Phase 1: Check for Basic Object Redefinitions
	//         Check for disallowed basic object inheriting
	//         Check for cycles
	for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
	    class_c c1 = (class_c)e.nextElement();
	    String c1Name = c1.getName().toString();
		if(c1.getName().toString().equals(TreeConstants.SELF_TYPE.toString())){
			errorReporter = classTable.semantError(c1);
			errorReporter.println("Class name cannot be SELF_TYPE");
			continue;
		}

	    //Check for redefinition of basic objects
	    if(c1Name.equals(TreeConstants.Object_.toString())) {
	    	errorReporter = classTable.semantError(c1);
		errorReporter.println("Redefinition of basic class Object.");
	    } else if (c1Name.equals(TreeConstants.IO.toString())) {
	    	errorReporter = classTable.semantError(c1);
		errorReporter.println("Redefinition of basic class IO.");
	    } else if (c1Name.equals(TreeConstants.Int.toString())) {
	    	errorReporter = classTable.semantError(c1);
		errorReporter.println("Redefinition of basic class Int.");
	    } else if (c1Name.equals(TreeConstants.Str.toString())) {
	    	errorReporter = classTable.semantError(c1);
		errorReporter.println("Redefinition of basic class String.");
	    } else if (c1Name.equals(TreeConstants.Bool.toString())) {
	    	errorReporter = classTable.semantError(c1);
		errorReporter.println("Redefinition of basic class Bool.");
	    } else { //no redefinitions of basic objects
	    	String c2Name = c1.getParent().toString();
			if(c2Name.equals(TreeConstants.SELF_TYPE.toString())){
				errorReporter = classTable.semantError(c1);
				errorReporter.println("Parent Class name cannot be SELF_TYPE");
				continue;
			}

		//check for disallowed basic object inheriting
		if (c2Name.equals(TreeConstants.Int.toString())){
			errorReporter = classTable.semantError(c1);
			errorReporter.println("Class " + c1Name + " cannot inherit class Int.");
		} else if (c2Name.equals(TreeConstants.Str.toString())) {
			errorReporter = classTable.semantError(c1);
			errorReporter.println("Class " + c1Name + " cannot inherit class String.");
		} else if (c2Name.equals(TreeConstants.Bool.toString())) {
			errorReporter = classTable.semantError(c1);
			errorReporter.println("Class " + c1Name + " cannot inherit class Bool.");
		} else { //No disallowed inheritance
			classTable.classNameMapper.put(c1Name, c1);
		  	classTable.inheritanceGraph.addEdge(c2Name, c1Name, 1);
		}
	    }
	}	
	//Check for cycles
	for (Map.Entry pair: classTable.inheritanceGraph.getCycles().entrySet()){
		errorReporter = classTable.semantError(classTable.classNameMapper.get(pair.getValue()));
		errorReporter.println("Class " + pair.getValue() + ", or an ancestor of " + pair.getValue() + ", is involved in an inheritance cycle.");		       	   errorReporter = classTable.semantError(classTable.classNameMapper.get(pair.getKey()));
		errorReporter.println("Class " + pair.getKey() + ", or an ancestor of " + pair.getKey() + ", is involved in an inheritance cycle.");

	}

	//Check for inheriting a class that is not defined
	for (Enumeration e = classes.getElements(); e.hasMoreElements();){
		class_c classy = (class_c) e.nextElement();
		AbstractSymbol parentName = classy.getParent();
		if (classTable.classNameMapper.get(parentName.toString()) == null){
			errorReporter = classTable.semantError();
			errorReporter.println("Error, inheriting from a Parent " + parentName.toString() + " that is not defined");
		}
	}

	//Phase 1 complete if no errors then inheritance graph (actually a tree since no multiple inheritance) is completely valid		

	//Phase 2 Create global Object and Method environments
	//Check for multiply defined errors during this phase as well
	//Check for existence of Main class and main method in main class
	//Check for inherited attribute names being declared in derived class
	Set<String> classNames = new HashSet<String>();
	boolean mainClassExists = false; //flag if Main class is defined
	boolean mainInMain = false; //flag if main() method exists in Main class
	boolean noFormalsInMainMethod = false; //flag if main() method has formals
	for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
	    class_c c1 = (class_c)e.nextElement();
	    if(c1.name.toString().equals("Main"))
	    	mainClassExists = true;
	    
	    if(classNames.contains(c1.name.toString())){
		errorReporter = classTable.semantError(c1);
	    	errorReporter.println("Class " + c1.name.toString() + " was previously defined.");
		continue;
	    } else {
	    	classNames.add(c1.name.toString());
	    }
		
	    Map<AbstractSymbol, List<AbstractSymbol>> methodArgMap = new HashMap<AbstractSymbol, List<AbstractSymbol>>();
	    Map<AbstractSymbol, AbstractSymbol> attrTypeMap = new HashMap<AbstractSymbol, AbstractSymbol>(); 
	    for(Enumeration e2 = c1.getFeatures().getElements(); e2.hasMoreElements();){
		Feature f = (Feature) e2.nextElement();
		if(f instanceof attr){
			attr a = (attr) f;
			//System.out.println(c1.getName().toString() + " attribute: " + a.name);
			if(attrTypeMap.containsKey(a.name)){
				errorReporter = classTable.semantError(c1);
				errorReporter.println("Attribute " + a.name.toString() + " is multiply defined in class.");
			}else if (isAttrInherited(a, c1, classTable)){
				errorReporter = classTable.semantError(c1);
				errorReporter.println("Attribute " + a.name.toString() + " is an attribute of an inherited class.");
			}else {
				attrTypeMap.put(a.name, a.type_decl);	
			}
		} else if (f instanceof method){
			method m = (method) f;
			//System.out.println(c1.getName().toString() + " method: " + m.name);
			if(methodArgMap.containsKey(m.name)) {
				errorReporter = classTable.semantError(c1);
				errorReporter.println("Method " + m.name.toString() + " is multiply defined.");
				continue;
			}else {
				List<AbstractSymbol> typeList = new ArrayList<AbstractSymbol>();
				Set<String> formalNames = new HashSet<String>();
				for(Enumeration e3 = m.formals.getElements(); e3.hasMoreElements();){
					formalc fo = (formalc) e3.nextElement();
					if(formalNames.contains(fo.name.toString())){
						errorReporter = classTable.semantError(c1.getFilename(), this);
						errorReporter.println("Formal parameter " + fo.name + " in function " + m.name + " in class " + c1.name + " is multiply defined");
						continue;
					}else {
						formalNames.add(fo.name.toString());
					}

					if(fo.type_decl.toString().equals(TreeConstants.SELF_TYPE.toString())){
						errorReporter = classTable.semantError(c1.getFilename(), this);
						errorReporter.println("Formal parameter " + fo.name + " in function " + m.name + " in class " + c1.name + " cannot have type SELF_TYPE");
						continue;
					}
					typeList.add(fo.type_decl);
				}
				typeList.add(m.return_type);
				methodArgMap.put(m.name, typeList);
				if(c1.name.toString().equals("Main")) {
					if(m.name.toString().equals("main")) {
						mainInMain = true;
						noFormalsInMainMethod = typeList.size()==1;
					}
				}
			}
		} else {
			System.out.println("Error should never reach here!");
		}
	    }
	    classTable.methodEnv.addId(c1.name, methodArgMap);
	    classTable.objectEnv.addId(c1.name, attrTypeMap);
	}

	//Ensure Main class exists and has a main() method
	if(!mainClassExists){
		errorReporter = classTable.semantError();
		errorReporter.println("Class Main is not defined.");
	} else {
		if(!mainInMain) {
		errorReporter = classTable.semantError(classTable.classNameMapper.get("Main"));
		errorReporter.println("No 'main' method in class Main.");
		} else {
			if(!noFormalsInMainMethod) {
				errorReporter = classTable.semantError(classTable.classNameMapper.get("Main"));
				errorReporter.println("'main' method in class Main should have no arguments.");
			}
		}
	}

	
	
	//Phase 2 completed. This means code has the required classes and methods and all attributes and methods have valid names.

	//Phase 3 TYPE CHECKING YEAHHH BABY!
	for(Enumeration e1 = classes.getElements(); e1.hasMoreElements();){
		class_c classie = (class_c) e1.nextElement();
		 for(Enumeration e2 = classie.getFeatures().getElements(); e2.hasMoreElements();){
		 	Feature f = (Feature) e2.nextElement();
			f.semant(classTable, classie, null);
		 }
	}

	//Semantic Analysis done! Report errors
	if (classTable.errors()) {
	    System.err.println("Compilation halted due to static semantic errors.");
	    System.exit(1);
	}



    }
    // check if an attribute exist in one of its parent classes.
    private boolean isAttrInherited(attr a, class_c cur, ClassTable c) {
    	Map<String, String> nodeParentMap = c.inheritanceGraph.getNodeParentHashMap(TreeConstants.Object_.toString());
        String parentName = nodeParentMap.get(cur.name.toString());
        while(parentName != null){
            class_c parent = c.classNameMapper.get(parentName);
            for (Enumeration e = parent.getFeatures().getElements(); e.hasMoreElements();){
                Feature f = (Feature) e.nextElement();
                if (f instanceof attr) {
                    if(((attr) f).name.toString().equals(a.name.toString()))
                        return true;
                }
            }
            parentName = nodeParentMap.get(parentName);
        }
        return false;
    }
}



/** Defines AST constructor 'class_c'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;
    /** Creates "class_c" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
	    ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }
    public AbstractSymbol getFilename() { return filename; }
    public Features getFeatures()       { return features; }

}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
    /** Creates "method" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for formals
      * @param a2 initial value for return_type
      * @param a3 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	    ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
	expr.dump_with_types(out, n + 2);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
    	Map<AbstractSymbol, List<AbstractSymbol>> methodArgMap = (Map<AbstractSymbol, List<AbstractSymbol>>) c.methodEnv.lookup(curr.name);
	  	if (methodArgMap == null) {
	  		errorReporter = c.semantError(curr.getFilename(), this);
			errorReporter.println("Unexpected Error occurred in method, current class not in methodEnv");
    	} else {
	        List<AbstractSymbol> formalList = methodArgMap.get(name);
			if(formalList == null){
				errorReporter = c.semantError(curr.getFilename(), this);
				errorReporter.println("Unexpected Error occurred in method, method name not in methodEnv");
			} else {
					//Bind self to SELF_TYPE and formals to their types
					List<Pair<AbstractSymbol, AbstractSymbol>> newBindings = new ArrayList<Pair<AbstractSymbol, AbstractSymbol>>();
					newBindings.add(new Pair<AbstractSymbol, AbstractSymbol>(TreeConstants.self, TreeConstants.SELF_TYPE));
					for(Enumeration e = formals.getElements(); e.hasMoreElements();){
						formalc formy = (formalc) e.nextElement();
						AbstractSymbol namey = formy.name;
						AbstractSymbol typey = formy.type_decl;
						newBindings.add(new Pair<AbstractSymbol, AbstractSymbol>(namey, typey));
					}

					Helper.updateObjectEnv(c.objectEnv, curr, newBindings); //enter a new scope with new bindings
					expr.semant(c, curr, errorReporter);
					c.objectEnv.exitScope(); //restore old scope
					
					String T0_prime_string = Helper.handleSELF_TYPE(expr.get_type().toString(), curr);
					String return_type_string;	

					if(return_type.toString().equals(TreeConstants.SELF_TYPE.toString())){
						return_type_string = curr.name.toString();
					}else{
						return_type_string = return_type.toString();
					}

					if (!return_type.toString().equals(TreeConstants.SELF_TYPE.toString()) && c.classNameMapper.get(return_type.toString())==null) {
						errorReporter = c.semantError(curr.getFilename(), this);
						errorReporter.println("Undefined return type " + return_type + " in method " + name + ".");
                    } else if(!c.inheritanceGraph.conforms(T0_prime_string, Helper.handleSELF_TYPE(return_type_string, curr), TreeConstants.Object_.toString())){
						errorReporter = c.semantError(curr.getFilename(), this);
						errorReporter.println("Inferred return type " + T0_prime_string + " of method " + name + " does not conform to declared return type " + return_type.toString() + ".");
					}
			}
		}

    }


}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;
    /** Creates "attr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
		init.dump_with_types(out, n + 2);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
    	Map<AbstractSymbol, AbstractSymbol> attrTypeMap = (Map<AbstractSymbol, AbstractSymbol>) c.objectEnv.lookup(curr.name);
	  	if (attrTypeMap == null) {
	  		errorReporter = c.semantError(curr.getFilename(), this);
			errorReporter.println("Unexpected Error occurred in attr, current class not in objectEnv");
    	} else {
	        AbstractSymbol T0 = attrTypeMap.get(name);
			if(T0 == null){
				errorReporter = c.semantError(curr.getFilename(), this);
				errorReporter.println("Unexpected Error occurred in attr: attr name " + name + " is not in objectEnv for class " + curr.name + " this is usually caused if you have multiply defined or redifining inherited variable errors so fix those please.");
			} else {
				if(!T0.toString().equals(type_decl.toString())){
					errorReporter = c.semantError(curr.getFilename(), this);
					errorReporter.println("Unexpected Error occurred in attr: type " + T0.toString() + " in objectEnv not equal to type_decl " + type_decl.toString() + ". This is usually caused if you have mutiply defined or redifining inherited variable errors so fix those please.");
				} else {
					//Bind self to SELF_TYPE
					List<Pair<AbstractSymbol, AbstractSymbol>> newBindings = new ArrayList<Pair<AbstractSymbol, AbstractSymbol>>();
					newBindings.add(new Pair<AbstractSymbol, AbstractSymbol>(TreeConstants.self, TreeConstants.SELF_TYPE));

					Helper.updateObjectEnv(c.objectEnv, curr, newBindings); //enter a new scope with new bindings
					init.semant(c, curr, errorReporter);
					AbstractSymbol T1 = init.get_type();
					if(!c.inheritanceGraph.conforms(T1.toString(), T0.toString(), TreeConstants.Object_.toString())){
						errorReporter = c.semantError(curr.getFilename(), this);
						errorReporter.println("Inferred type " + T1.toString() + " of initialization of attribute " + name + " does not conform to declared type " + type_decl.toString() + ".");
					}
					c.objectEnv.exitScope(); //restore old scope.
				}
			}
		}

    }

}


/** Defines AST constructor 'formalc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression expr;
    /** Creates "branch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	expr.dump_with_types(out, n + 2);
    }
}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;
    /** Creates "assign" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
    	Map<AbstractSymbol, AbstractSymbol> varMap = (Map<AbstractSymbol, AbstractSymbol>) c.objectEnv.lookup(curr.name);
		if (varMap == null) {
			errorReporter = c.semantError(curr.getFilename(), this);
				errorReporter.println("Unexpected Error occurred in assign, current class not in objectEnv");
				set_type(TreeConstants.Object_);
    	} else {
			AbstractSymbol type =  Helper.attrType(this.name, curr, c);
			if(type == null){
                errorReporter = c.semantError(curr.getFilename(), this);
				errorReporter.println("Identifier: " + name + " in class " + curr.name.toString() + " is undefined");
				set_type(TreeConstants.Object_);
			} else {
				expr.semant(c, curr, errorReporter);
				AbstractSymbol type_prime = expr.get_type();
				if(c.inheritanceGraph.conforms(type_prime.toString(), type.toString(), TreeConstants.Object_.toString())){
					set_type(type_prime);
				} else {
                    errorReporter = c.semantError(curr.getFilename(), this);
					errorReporter.println("Type " + type_prime.toString() + " of assigned expression does not conform to declared type " + type.toString() + " of identifier "+ name + ".");
					set_type(TreeConstants.Object_);
				}
			}
		}

	}
    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
		expr.dump_with_types(out, n + 2);
		dump_type(out, n);
    }

}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "static_dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for type_name
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }
	
	public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
		expr.semant(c, curr, errorReporter);
		AbstractSymbol T0 = expr.get_type();
		List<AbstractSymbol> actualTypes = new ArrayList<AbstractSymbol>();
		for(Enumeration e = actual.getElements(); e.hasMoreElements();){
			Expression exp = (Expression) e.nextElement();
			exp.semant(c, curr, errorReporter);
			actualTypes.add(exp.get_type());
		}

		if(type_name.toString().equals(TreeConstants.SELF_TYPE.toString())){
			errorReporter = c.semantError(curr.getFilename(), this);
			errorReporter.println("Cannot call static dispatch on SELF_TYPE");
			set_type(TreeConstants.Object_);
			return;
		}

		if(!c.inheritanceGraph.conforms(T0.toString(),type_name.toString(), TreeConstants.Object_.toString())){
			errorReporter = c.semantError(curr.getFilename(), this);
			errorReporter.println("Expression type " + T0.toString() + " does not conform to declared static dispatch type " + type_name.toString());
		}

		Map<AbstractSymbol, List<AbstractSymbol>> curr_methods_map = (Map<AbstractSymbol, List<AbstractSymbol>>) c.methodEnv.lookup(type_name);
		if(curr_methods_map == null){
			errorReporter = c.semantError(curr.getFilename(), this);
			errorReporter.println("Unexpected error occurred in static dispatch. Class " + type_name.toString() + " not in methodEnv");
			set_type(TreeConstants.Object_);
		}else {
			List<AbstractSymbol> formalTypes = curr_methods_map.get(name);
			if(formalTypes == null) {
				errorReporter = c.semantError(curr.getFilename(), this);
				errorReporter.println("Dispatch to undefined method " + name + ".");
				set_type(TreeConstants.Object_);
			} else {
				if(actualTypes.size() != formalTypes.size()-1){
					errorReporter = c.semantError(curr.getFilename(), this);
					errorReporter.println("Method "+name+" called with wrong number of arguments.");
					set_type(TreeConstants.Object_);
					} else {
					for(int i = 0;i < actualTypes.size(); i++) {
						if(!c.inheritanceGraph.conforms(actualTypes.get(i).toString(), formalTypes.get(i).toString(), TreeConstants.Object_.toString())){
							errorReporter = c.semantError(curr.getFilename(), this);
							errorReporter.println("Inferred type " + actualTypes.get(i).toString() + " does not conform to formal type " + formalTypes.get(i).toString());
							set_type(TreeConstants.Object_);
							return;
						}
					}
					AbstractSymbol T_return;
					if(formalTypes.get(formalTypes.size()-1).toString().equals(TreeConstants.SELF_TYPE.toString()))
						T_return = T0;
					else
						T_return = formalTypes.get(formalTypes.size()-1);
					set_type(T_return);
				}
			}
		}
	}
}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for name
      * @param a2 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
		expr.semant(c, curr, errorReporter);
		AbstractSymbol T0 = expr.get_type();
		List<AbstractSymbol> actualTypes = new ArrayList<AbstractSymbol>();
		for(Enumeration e = actual.getElements(); e.hasMoreElements();){
			Expression exp = (Expression) e.nextElement();
			exp.semant(c, curr, errorReporter);
			actualTypes.add(exp.get_type());
		}
		AbstractSymbol T0_prime;
		if(T0.toString().equals(TreeConstants.SELF_TYPE.toString()))
			T0_prime = curr.name;
		else
			T0_prime = T0;
		Map<AbstractSymbol, List<AbstractSymbol>> curr_methods_map = (Map<AbstractSymbol, List<AbstractSymbol>>) c.methodEnv.lookup(curr.name);
		if(curr_methods_map == null){
			errorReporter = c.semantError(curr.getFilename(), this);
			errorReporter.println("Unexpected error occurred in dispatch. Class " + curr.name.toString() + " not in methodEnv");
			set_type(TreeConstants.Object_);
		}else {
			List<AbstractSymbol> formalTypes = Helper.getFormalList(name, c.classNameMapper.get(T0_prime.toString()), c);
			if(formalTypes == null) {
				errorReporter = c.semantError(curr.getFilename(), this);
				errorReporter.println("Dispatch to undefined method " + name + ".");
				set_type(TreeConstants.Object_);
			} else {
				if(actualTypes.size() != formalTypes.size()-1){
					errorReporter = c.semantError(curr.getFilename(), this);
					errorReporter.println("Method "+name+" called with wrong number of arguments.");
                    set_type(TreeConstants.Object_);
					} else {
					for(int i = 0;i < actualTypes.size(); i++) {
						if(!c.inheritanceGraph.conforms(Helper.handleSELF_TYPE(actualTypes.get(i).toString(), curr), formalTypes.get(i).toString(), TreeConstants.Object_.toString())){
                            
							errorReporter = c.semantError(curr.getFilename(), this);
                            // TODO: fill in the parameter name
                            errorReporter.println("In call of method "+name+", type "+ actualTypes.get(i).toString() +" of parameter "+ "variable" +" does not conform to declared type " + formalTypes.get(i).toString());
							set_type(TreeConstants.Object_);
							return;
						}
					}
					AbstractSymbol T_return;
					if(formalTypes.get(formalTypes.size()-1).toString().equals(TreeConstants.SELF_TYPE.toString()))
						T_return = T0;
					else
						T_return = formalTypes.get(formalTypes.size()-1);
					set_type(T_return);
				}
			}
		}
	}

}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
    /** Creates "cond" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for then_exp
      * @param a2 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
	pred.dump_with_types(out, n + 2);
	then_exp.dump_with_types(out, n + 2);
	else_exp.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
        pred.semant(c, curr, errorReporter);
        if ( ! pred.get_type().toString().equals(TreeConstants.Bool.toString()) ) {
            errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("Predicate of conditional has to be boolean");
            set_type(TreeConstants.Object_);
        }
        then_exp.semant(c, curr, errorReporter);
        else_exp.semant(c, curr, errorReporter);
        set_type(c.classNameMapper.get(c.inheritanceGraph.lub(Helper.handleSELF_TYPE(then_exp.get_type().toString(), curr),
                        Helper.handleSELF_TYPE(else_exp.get_type().toString(), curr),
                        TreeConstants.Object_.toString())).name);
    }
}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
    /** Creates "loop" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
	pred.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
		pred.semant(c, curr, errorReporter);
		AbstractSymbol T1 = pred.get_type();
		if(!T1.toString().equals(TreeConstants.Bool.toString())){
			errorReporter = c.semantError(curr.getFilename(), this);
			errorReporter.println("Inferred type of predicate in while loop is not Bool: "+T1);
			set_type(TreeConstants.Object_);
		}else{
			body.semant(c, curr, errorReporter);
			set_type(TreeConstants.Object_);
		}
	}
}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
    /** Creates "typcase" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
	expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
	    ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
        expr.semant(c, curr, errorReporter); // calculate the LUB of e0 first.
        Set<AbstractSymbol> branch_decl = new HashSet<AbstractSymbol>(); // to see if types are distinct in cases
        List<AbstractSymbol> casetypes = new LinkedList<AbstractSymbol>(); // for LUB of types
        for (Enumeration en = cases.getElements(); en.hasMoreElements(); ) {
            branch br = (branch) en.nextElement();
            // Enter scope for each branch of cases
            ArrayList<Pair<AbstractSymbol, AbstractSymbol>> newBindings = new ArrayList<Pair<AbstractSymbol, AbstractSymbol>>();
            newBindings.add(new Pair<AbstractSymbol, AbstractSymbol>(br.name, br.type_decl));
            Helper.updateObjectEnv(c.objectEnv, curr, newBindings);	

            // checks if declared types of each branch is uqique
            if(branch_decl.contains(br.type_decl)) { 
                errorReporter = c.semantError(curr.getFilename(), this);
                errorReporter.println("Duplicate branch "+br.type_decl.toString()+" in case statement.");
                set_type(TreeConstants.Object_);
            } else {
                branch_decl.add(br.type_decl);
            }

            // Evaluate the expression, then exit the scope.
            Expression e = br.expr;
            e.semant(c, curr, errorReporter);
            AbstractSymbol caseType = e.get_type();
            casetypes.add(caseType);
            c.objectEnv.exitScope();
        }
        if(casetypes.isEmpty()) { // if there is no case branch, should be error
            errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("There should be at least one branch in cases");
            set_type(TreeConstants.Object_);
        } else {
            // Calculates the Least Upper Bound of the case expressions.
            AbstractSymbol case_lub = casetypes.remove(0);
            while(!casetypes.isEmpty()) {
                String lub_string = c.inheritanceGraph.lub(case_lub.toString(),
                        casetypes.remove(0).toString(),
                        TreeConstants.Object_.toString());
                case_lub = c.classNameMapper.get(lub_string).name;
            }
            set_type(case_lub);
        }
    }
}


/** Defines AST constructor 'block'.
  <p>
  See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
    /** Creates "block" AST node. 
     *
     * @param lineNumber the line in the source file from which this node came.
     * @param a0 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
		for(Enumeration e = body.getElements(); e.hasMoreElements();) {
			Expression expr = (Expression) e.nextElement();
			expr.semant(c, curr, errorReporter);
			set_type(expr.get_type());
		}
	}

}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;
    /** Creates "let" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for identifier
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      * @param a3 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
		dump_AbstractSymbol(out, n + 2, identifier);
		dump_AbstractSymbol(out, n + 2, type_decl);
		init.dump_with_types(out, n + 2);
		body.dump_with_types(out, n + 2);
		dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
		String T0_prime_string;
		if(type_decl.toString().equals(TreeConstants.SELF_TYPE)){
			T0_prime_string = type_decl.toString()+"_"+curr.name.toString();
		}else{
			T0_prime_string = type_decl.toString();
		}
		init.semant(c, curr, errorReporter);
		AbstractSymbol T1 = init.get_type();
		if(!c.inheritanceGraph.conforms(T1.toString(), T0_prime_string, TreeConstants.Object_.toString())){
			errorReporter = c.semantError(curr.getFilename(), this);
			errorReporter.println("Inferred type " + T1.toString() + " does not conform to indentifier " + identifier.toString() + "'s declared type " + T0_prime_string);
		}
		
		ArrayList<Pair<AbstractSymbol, AbstractSymbol>> newBindings = new ArrayList<Pair<AbstractSymbol, AbstractSymbol>>();
		newBindings.add(new Pair<AbstractSymbol, AbstractSymbol>(identifier, type_decl));
		
		Helper.updateObjectEnv(c.objectEnv, curr, newBindings);	
		body.semant(c, curr, errorReporter);
		
		set_type(body.get_type());
		//System.out.println("let returned: " + this.get_type());
		
		c.objectEnv.exitScope();
		
	}

}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "plus" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
        e1.semant(c, curr, errorReporter);
        if ( ! e1.get_type().toString().equals(TreeConstants.Int.toString()) ) {
			errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("First Element of plus should be Int");
            set_type(TreeConstants.Object_);
        }
        e2.semant(c, curr, errorReporter);
        if ( ! e2.get_type().toString().equals(TreeConstants.Int.toString()) ) {
			errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("Second Element of plus should be Int");
            set_type(TreeConstants.Object_);
        }
        set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "sub" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
        e1.semant(c, curr, errorReporter);
        if ( ! e1.get_type().toString().equals(TreeConstants.Int.toString()) ) {
			errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("First Element of sub should be Int");
            set_type(TreeConstants.Object_);
        }
        e2.semant(c, curr, errorReporter);
        if ( ! e2.get_type().toString().equals(TreeConstants.Int.toString()) ) {
			errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("Second Element of sub should be Int");
            set_type(TreeConstants.Object_);
        }
        set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "mul" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
        e1.semant(c, curr, errorReporter);
        if ( ! e1.get_type().toString().equals(TreeConstants.Int.toString()) ) {
			errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("First Element of mul should be Int");
            set_type(TreeConstants.Object_);
        }
        e2.semant(c, curr, errorReporter);
        if ( ! e2.get_type().toString().equals(TreeConstants.Int.toString()) ) {
			errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("Second Element of mul should be Int");
            set_type(TreeConstants.Object_);
        }
        set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "divide" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
        e1.semant(c, curr, errorReporter);
        if ( ! e1.get_type().toString().equals(TreeConstants.Int.toString()) ) {
			errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("First Element of divide should be Int");
            set_type(TreeConstants.Object_);
        }
        e2.semant(c, curr, errorReporter);
        if ( ! e2.get_type().toString().equals(TreeConstants.Int.toString()) ) {
			errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("Second Element of divide should be Int");
            set_type(TreeConstants.Object_);
        }
        set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
    /** Creates "neg" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
        e1.semant(c, curr, errorReporter);
        if ( ! e1.get_type().toString().equals(TreeConstants.Int.toString()) ) {
			errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("Expr of neg should be Int");
            set_type(TreeConstants.Object_);
        }
        set_type(TreeConstants.Int);
    }
}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "lt" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
        e1.semant(c, curr, errorReporter);
        if ( ! e1.get_type().toString().equals(TreeConstants.Int.toString()) ) {
            errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("First Expr of less than should be Int type");
            set_type(TreeConstants.Object_);
        }
        e2.semant(c, curr, errorReporter);
        if ( ! e2.get_type().toString().equals(TreeConstants.Int.toString()) ) {
            errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("Second Expr of less than should be Int type");
            set_type(TreeConstants.Object_);
        }
        set_type(TreeConstants.Bool);
    }
}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "eq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
		e1.semant(c, curr, errorReporter);
		AbstractSymbol T1 = e1.get_type();
		e2.semant(c, curr, errorReporter);
		AbstractSymbol T2 = e2.get_type();

		Set<String> special_case_strings = new HashSet<String>();
		special_case_strings.add(TreeConstants.Int.toString());
		special_case_strings.add(TreeConstants.Str.toString());
		special_case_strings.add(TreeConstants.Bool.toString());

		if(special_case_strings.contains(T1.toString()) || special_case_strings.contains(T2.toString())){
			if(!T1.toString().equals(T2.toString())) {
				errorReporter = c.semantError(curr.getFilename(), this);
				errorReporter.println("Int, Bool, and String may only be compared with objects of the same type");
				set_type(TreeConstants.Object_);
			}else {
				set_type(TreeConstants.Bool);
			}
		}else {
			set_type(TreeConstants.Bool);
		}
	}

}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "leq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
        e1.semant(c, curr, errorReporter);
        if ( ! e1.get_type().toString().equals(TreeConstants.Int.toString()) ) {
            errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("First Expr of less than should be Int type");
            set_type(TreeConstants.Object_);
        }
        e2.semant(c, curr, errorReporter);
        if ( ! e2.get_type().toString().equals(TreeConstants.Int.toString()) ) {
            errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("Second Expr of less than should be Int type");
            set_type(TreeConstants.Object_);
        }
        set_type(TreeConstants.Bool);
    }
}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;
    /** Creates "comp" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

    public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
        e1.semant(c, curr, errorReporter);
        if ( ! e1.get_type().toString().equals(TreeConstants.Bool.toString()) ) {
            errorReporter = c.semantError(curr.getFilename(), this);
            errorReporter.println("Expression of not should be Bool type");
            set_type(TreeConstants.Object_);
        }
        set_type(TreeConstants.Bool);
    }
}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "int_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
	dump_AbstractSymbol(out, n + 2, token);
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
		set_type(TreeConstants.Int);
	}

}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
    /** Creates "bool_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
	dump_Boolean(out, n + 2, val);
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
		set_type(TreeConstants.Bool);
	}

}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "string_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
	out.print(Utilities.pad(n + 2) + "\"");
	Utilities.printEscapedString(out, token.getString());
	out.println("\"");
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
		set_type(TreeConstants.Str);
	}

}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;
    /** Creates "new_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
	dump_AbstractSymbol(out, n + 2, type_name);
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
		if (type_name.toString().equals(TreeConstants.SELF_TYPE.toString())){
			set_type(TreeConstants.SELF_TYPE);
		} else {
			set_type(type_name);
		}
	}

}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
    /** Creates "isvoid" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter) {
		e1.semant(c, curr, errorReporter);
		set_type(TreeConstants.Bool);
	}

}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
	dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
		set_type(TreeConstants.No_type);
	}

}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
    /** Creates "object" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
		dump_AbstractSymbol(out, n + 2, name);
		dump_type(out, n);
    }

	public void semant(ClassTable c, class_c curr, PrintStream errorReporter){
		Map<AbstractSymbol, AbstractSymbol> varMap = (Map<AbstractSymbol, AbstractSymbol>) c.objectEnv.lookup(curr.name);
		if (varMap == null) {
			errorReporter = c.semantError(curr.getFilename(), this);
			errorReporter.println("Unexpected Error occurred in object, current class not in objectEnv");
			set_type(TreeConstants.Object_);
    	} else {
			AbstractSymbol type = Helper.attrType(this.name, curr, c);
			if(type == null){
				errorReporter = c.semantError(curr.getFilename(), this);
				errorReporter.println("Identifier: " + name + " in class " + curr.name.toString() + " is undefined");
				set_type(TreeConstants.Object_);
			} else {
				set_type(type);
			}
		}
	}
}


/**
 *  Helper class for static help methods
 **/
class Helper {
    public static AbstractSymbol attrType(AbstractSymbol attrName, class_c cur, ClassTable c) {
    	if(c.objectEnv.lookup(cur.name) != null && ((Map<AbstractSymbol, AbstractSymbol>) c.objectEnv.lookup(cur.name)).get(attrName)!=null)
            return ((Map<AbstractSymbol, AbstractSymbol>) c.objectEnv.lookup(cur.name)).get(attrName);
        Map<String, String> nodeParentMap = c.inheritanceGraph.getNodeParentHashMap(TreeConstants.Object_.toString());
        String parentName = nodeParentMap.get(cur.name.toString());
        while(parentName != null){
            class_c parent = c.classNameMapper.get(parentName);
            for (Enumeration e = parent.getFeatures().getElements(); e.hasMoreElements();){
                Feature f = (Feature) e.nextElement();
                if (f instanceof attr) {
                    if(((attr) f).name.toString().equals(attrName.toString()))
                        return ((Map<AbstractSymbol, AbstractSymbol>)c.objectEnv.lookup(parent.name)).get(attrName);
                }
            }
            parentName = nodeParentMap.get(parentName);
        }
        return null;
    }

	public static List<AbstractSymbol> getFormalList(AbstractSymbol methodName, class_c cur, ClassTable c) {
    	if(c.methodEnv.lookup(cur.name) != null && ((Map<AbstractSymbol, List<AbstractSymbol>>) c.methodEnv.lookup(cur.name)).get(methodName)!=null)
            return ((Map<AbstractSymbol, List<AbstractSymbol>>) c.methodEnv.lookup(cur.name)).get(methodName);
        Map<String, String> nodeParentMap = c.inheritanceGraph.getNodeParentHashMap(TreeConstants.Object_.toString());
        String parentName = nodeParentMap.get(cur.name.toString());
        while(parentName != null){
            class_c parent = c.classNameMapper.get(parentName);
            for (Enumeration e = parent.getFeatures().getElements(); e.hasMoreElements();){
                Feature f = (Feature) e.nextElement();
				if (f instanceof method) {
                    if(((method) f).name.toString().equals(methodName.toString()))
                        return ((Map<AbstractSymbol, List<AbstractSymbol>>)c.methodEnv.lookup(parent.name)).get(methodName);
                }
            }
            parentName = nodeParentMap.get(parentName);
        }
        return null;
    }
	
	//Precondition: class curr's name should be in the SymbolTable otherwise NullPointerException
	//Note this method adds a scope the argument SymbolTable
	public static void updateObjectEnv(SymbolTable objectEnv, class_c curr, List<Pair<AbstractSymbol, AbstractSymbol>>newBindings){
		//get current attrTypeMap for the class curr and place all values in newAttrTypeMap
		Map<AbstractSymbol, AbstractSymbol> newAttrTypeMap = new HashMap<AbstractSymbol, AbstractSymbol>();
		newAttrTypeMap.putAll((Map<AbstractSymbol,AbstractSymbol>)objectEnv.lookup(curr.name));

		for(int i = 0; i < newBindings.size(); i++){
			newAttrTypeMap.put(newBindings.get(i).getLeft(), newBindings.get(i).getRight());
		}
		
		objectEnv.enterScope();
		objectEnv.addId(curr.name, newAttrTypeMap);
	}

	public static String handleSELF_TYPE(String s, class_c curr){
		if(s.equals(TreeConstants.SELF_TYPE.toString()))
			return s + "_" + curr.name.toString();
		else
			return s;
	}
}
