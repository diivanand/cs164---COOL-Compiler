/*
   Copyright (c) 2000 The Regents of the University of California.
   All rights reserved.

   Permission to use, copy, modify, and distribute this software for any
   purpose, without fee, and without written agreement is hereby granted,
   provided that the above copyright notice and the following two
   paragraphs appear in all copies of this software.

   IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
   DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
   OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
   CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

   THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
   INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
   AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
   ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
   PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
   */

// This is a project skeleton file

import java.io.PrintStream;
import java.util.Vector;
import java.util.Enumeration;
import java.util.*; // for map and stuff

class CgenNode extends class_c {

    /** The parent of this node in the inheritance tree */
    private CgenNode parent;

    /** The children of this node in the inheritance tree */
    private Vector children;

    /** Indicates a basic class */
    final static int Basic = 0;

    /** Indicates a class that came from a Cool program */
    final static int NotBasic = 1;

    /** Does this node correspond to a basic class? */
    private int basic_status;

    /** PRIVATE HELPER VARIABLES WE ADDED */
    /** Map from Class to Offset to the dispatch table */
    private Map<AbstractSymbol, Integer> methodMap;

    // Queue of atrributes used when creating prototype object


    //static variable that generates class tags
    private final static int OBJECT_CLASS_TAG = 0;
    private final static int IO_CLASS_TAG = 1;
    private final static int MAIN_CLASS_TAG = 2;
    private final static int INT_CLASS_TAG = 3;
    private final static int BOOL_CLASS_TAG = 4;
    private final static int STRING_CLASS_TAG = 5;
    private static int CURR_CLASS_TAG = 3; //strange hack since it starts at 3 more than what this says

    //this nodes class tag
    private int tag;


    /** Constructs a new CgenNode to represent class "c".
     * @param c the class
     * @param basic_status is this class basic or not
     * @param table the class table
     * */
    CgenNode(Class_ c, int basic_status, CgenClassTable table) {
        super(0, c.getName(), c.getParent(), c.getFeatures(), c.getFilename());
        this.parent = null;
        this.children = new Vector();
        this.basic_status = basic_status;
        this.methodMap = new HashMap<AbstractSymbol, Integer>(); // added
        AbstractTable.stringtable.addString(name.getString());
        if(c.getName().toString().equals(TreeConstants.Object_.toString())){
            this.tag = OBJECT_CLASS_TAG;
        } else if (c.getName().toString().equals(TreeConstants.IO.toString())){
            this.tag = IO_CLASS_TAG;
        } else if (c.getName().toString().equals(TreeConstants.Main.toString())){
            this.tag = MAIN_CLASS_TAG;
        } else if (c.getName().toString().equals(TreeConstants.Int.toString())){
            this.tag = INT_CLASS_TAG;
        } else if (c.getName().toString().equals(TreeConstants.Bool.toString())){
            this.tag = BOOL_CLASS_TAG;
        } else if (c.getName().toString().equals(TreeConstants.Str.toString())){
            this.tag = STRING_CLASS_TAG;
        } else {
            this.tag = CURR_CLASS_TAG;
            CURR_CLASS_TAG++;
        }
    }

    void addChild(CgenNode child) {
        children.addElement(child);
    }

    /** Gets the children of this class
     * @return the children
     * */
    Enumeration getChildren() {
        return children.elements(); 
    }

    /** Sets the parent of this class.
     * @param parent the parent
     * */
    void setParentNd(CgenNode parent) {
        if (this.parent != null) {
            Utilities.fatalError("parent already set in CgenNode.setParent()");
        }
        if (parent == null) {
            Utilities.fatalError("null parent in CgenNode.setParent()");
        }
        this.parent = parent;
    }    


    /** Gets the parent of this class
     * @return the parent
     * */
    CgenNode getParentNd() {
        return parent; 
    }

    /** Returns true is this is a basic class.
     * @return true or false
     * */
    boolean basic() { 
        return basic_status == Basic; 
    }

    // functions I added
    //
    //

    /** emits Class Object Table
     *
     **/
    public void codeClassObjTab(PrintStream str) {
        str.println(CgenSupport.WORD + this.getName() + CgenSupport.PROTOBJ_SUFFIX);
        str.println(CgenSupport.WORD + this.getName() + CgenSupport.CLASSINIT_SUFFIX);
    }

    /***
     * Wonder if WE NEED TO PRINT 'class_parentTab' and
     * 'class_attrTabTab'
     * **/
    public void codeParentTables(PrintStream str) {
    }
    public void codeAttrTables(PrintStream str) {
    }

    /** emits prototype objects
     *
     **/
    public void codeProtObj(PrintStream str) {
        str.print(this.getName()+CgenSupport.PROTOBJ_SUFFIX+CgenSupport.LABEL);
        Stack<attr> attrStack = new Stack<attr>();

        CgenNode curr = this;
        while(curr != null) {
            List<attr> tmp = new ArrayList<attr>();
            for(Enumeration e = curr.getFeatures().getElements(); e.hasMoreElements();){
                Feature feat = (Feature) e.nextElement();
                if (feat instanceof  attr){
                    tmp.add((attr) feat);
                }
            }
            for(int i = tmp.size()-1;i >= 0; i--){
                attrStack.push(tmp.get(i));
            }
            curr = curr.getParentNd();
        }

        List<attr> attrList = new LinkedList<attr>();
        while(!attrStack.empty()){
            attrList.add(attrStack.pop());
        }

        // emit class tag id
        str.println(CgenSupport.WORD + this.tag);

        // emit class size = # attribute + 3
        str.println(CgenSupport.WORD + Integer.toString(3 + attrList.size()));

        // dispatch tables 
        str.println(CgenSupport.WORD + this.getName() + CgenSupport.DISPTAB_SUFFIX);
        
        // set up initial values for each attribute
        //
        //
        //IntSymbol defaultVal = (IntSymbol) AbstractTable.inttable.lookup("0");
        //String nodeName = getName().toString();
        //DELETE ABOVE TWO LINES
        for(int i = 0;i < attrList.size(); i++) {
            attr at = attrList.get(i);
            AbstractSymbol attrType = at.type_decl;
            if(attrType.equals(TreeConstants.Object_)){
                str.println(CgenSupport.WORD + 0);
            } else if (attrType.equals(TreeConstants.IO)){
                str.println(CgenSupport.WORD + 0);
            } else if (attrType.equals(TreeConstants.Main)){
                str.println(CgenSupport.WORD + 0);
            } else if (attrType.equals(TreeConstants.Int)){   //default value of int is 0
                str.print(CgenSupport.WORD);
                IntSymbol defaultVal = (IntSymbol) AbstractTable.inttable.lookup("0");
                defaultVal.codeRef(str);
                str.println();
            } else if (attrType.equals(TreeConstants.Bool)){  //default for boolean is false bool const
                str.print(CgenSupport.WORD);
                BoolConst defaultVal = new BoolConst(false);
                defaultVal.codeRef(str);
                str.println();
            } else if (attrType.equals(TreeConstants.Str)){  //default for string is empty string
                str.print(CgenSupport.WORD);
                StringSymbol defaultVal = (StringSymbol) AbstractTable.stringtable.lookup("") ;
                defaultVal.codeRef(str);
                str.println();
            } else {        //all other objects are void for default
                str.println(CgenSupport.WORD + 0);
            }
        }
    }
    
    /**
     * emits dispatch tables
     **/
    public void codeDispatchTables(PrintStream str) {
        str.print(this.getName()+CgenSupport.DISPTAB_SUFFIX+CgenSupport.LABEL);
        buildMethodMap(str, new HashSet<AbstractSymbol>()); 
    }

    /**
     * Helper function for codeDispatchTables()
     * Recursively goes up to the Object
     * We collect the set of method names, so that it does not emit the decendant's
     * overriden methods.
     * @param str PrintStream to the code
     * @Param oldMethodSet set of method names seen so far
     **/
    private void buildMethodMap(PrintStream str, Set<AbstractSymbol> oldMethodSet) {
        Set<AbstractSymbol> newMethodSet = new HashSet<AbstractSymbol>(oldMethodSet);
        List<method> methodList = new LinkedList<method>();  
        for(Enumeration e = getFeatures().getElements() ; e.hasMoreElements() ; ) {
            Feature feat = (Feature) e.nextElement();
            if (feat instanceof method) {
                method met = (method) feat;
                methodList.add(met);
                newMethodSet.add(met.name);
            }
        }
        
        if(getParentNd() != null) {
            getParentNd().buildMethodMap(str, newMethodSet);
        }
        for(method met : methodList) {
            if(!oldMethodSet.contains(met.name)) {
                str.println(CgenSupport.WORD + this.getName()+CgenSupport.METHOD_SEP+met.name);
                methodMap.put(met.name, methodMap.size());
            }
        }
    }

    /**
    * Helper function for codeProtObj
    * Recursively goes up to the Object
    **/
//    private void build

    /**
     * emits name tab
     **/
    public void codeNameTab(PrintStream str) {
        str.print(CgenSupport.WORD);
        String name = this.getName().toString();
        StringSymbol s = (StringSymbol) AbstractTable.stringtable.lookup(name);
        s.codeRef(str);
        str.println();
    }

    /**
     * Helper Function that Pushes the Stack Frame
     * used in object init and class methods
     **/
    private void pushStackFrame(PrintStream str) {
        int offset = 3;
        // addiu $sp $sp -12
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, -offset * 4, str);
        // sw $fp 12($sp)
        CgenSupport.emitStore(CgenSupport.FP, offset--, CgenSupport.SP, str);
        // sw $s0 8($sp)
        CgenSupport.emitStore(CgenSupport.SELF, offset--, CgenSupport.SP, str);
        // sw $ra 4($sp)
        CgenSupport.emitStore(CgenSupport.RA, offset--, CgenSupport.SP, str);
        // addiu $fp $sp 16
        CgenSupport.emitAddiu(CgenSupport.FP, CgenSupport.SP, 16, str);
        // move $s0 $a0
        CgenSupport.emitMove(CgenSupport.SELF, CgenSupport.ACC, str);
    }
    /**
     * Helper Function that pops the Stack Frame
     * used in object init and class methods
     **/
    private void popStackFrame(PrintStream str) {
        int offset = 3;
        // lw $fp 12($sp)
        CgenSupport.emitLoad(CgenSupport.FP, offset--, CgenSupport.SP, str);
        // lw $S0 8($sp)
        CgenSupport.emitLoad(CgenSupport.SELF, offset--, CgenSupport.SP, str);
        // lw $ra 4($sp)
        CgenSupport.emitLoad(CgenSupport.RA, offset, CgenSupport.SP, str);
        // addiu $sp $sp 12
        CgenSupport.emitAddiu(CgenSupport.SP, CgenSupport.SP, 12, str);
        // jr $ra
        CgenSupport.emitReturn(str);
    }
    /**
     * emits code for object initializer
     */
    public void codeObjInit(PrintStream str) {
        str.print(this.getName() + CgenSupport.CLASSINIT_SUFFIX + CgenSupport.LABEL);

        pushStackFrame(str);

        if (! getName().equals(TreeConstants.Object_) ) {
            CgenSupport.emitJal(getParentNd().getName()
                    + CgenSupport.CLASSINIT_SUFFIX,
                    str);
        }


        for(Enumeration e = getFeatures().getElements() ; e.hasMoreElements() ; ) {
            Feature feat = (Feature) e.nextElement();
            if (feat instanceof attr) {
                attr at = (attr) feat;
                String addr = "WRONG"; // I NEED THE RIGHT OFFSET
                CgenSupport.emitLoadAddress(CgenSupport.ACC, addr, str);
                int offset  = -9999; // I NEED THE RIGHT ADDRESS
                CgenSupport.emitStore(CgenSupport.ACC, offset, CgenSupport.SELF, str);
            }
        }

        // move $a0 $s0
        CgenSupport.emitMove(CgenSupport.ACC, CgenSupport.SELF, str);
        popStackFrame(str);
    }


    /**
     * emits code for class methods 
     */
    public void codeClassMethods(PrintStream str) {

        for(Enumeration e = getFeatures().getElements() ; e.hasMoreElements() ; ) {
            Feature feat = (Feature) e.nextElement();
            if (feat instanceof method) {
                method met = (method) feat;
                str.print(this.getName()+CgenSupport.METHOD_SEP+met.name+CgenSupport.LABEL);
                pushStackFrame(str);
                // lw $fp 16($sp)
                CgenSupport.emitLoad(CgenSupport.FP, 4, CgenSupport.SP, str);
                popStackFrame(str);
            }
        }
    }
}



