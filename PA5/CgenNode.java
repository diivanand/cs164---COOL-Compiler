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
    private static int CURR_CLASS_TAG = 6;

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

        // emit class tag id
        //str.println(CgenSupport.WORD + this.tag);

        // emit class size = # attribute + 3

        // dispatch tables 
        str.println(CgenSupport.WORD + this.getName() + CgenSupport.DISPTAB_SUFFIX);
        
        // set up initial values for each attribute
    }
    
    /**
     * emits dispatch tables
     **/
    public void codeDispatchTables(PrintStream str) {
        str.print(this.getName()+CgenSupport.DISPTAB_SUFFIX+CgenSupport.LABEL);
        buildMethodMap(str); 
    }

    /**
     * Helper function for codeDispatchTables()
     * Recursively goes up to the Object
     **/
    private void buildMethodMap(PrintStream str) {
        if(getParentNd() != null) {
            getParentNd().buildMethodMap(str);
        }

        for(Enumeration e = getFeatures().getElements() ; e.hasMoreElements() ; ) {
            Feature feat = (Feature) e.nextElement();
            if (feat instanceof method) {
                method met = (method) feat;
                str.println(CgenSupport.WORD + this.getName()+"."+ met.name);
                methodMap.put(met.name, methodMap.size());
            }
        }
        //System.out.println(this.getName());
        //for(AbstractSymbol met : methodMap.keySet()) {
        //    System.out.println("Method "+met.getString() +" : " + methodMap.get(met).toString());
        //}
    }

    /**
    * Helper function for codeProtObj
    * Recursively goes up to the Object
    **/
    private void build

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
     * emits code for object initializer
     */
    public void codeObjInit(PrintStream str) {
        str.println(this + CgenSupport.CLASSINIT_SUFFIX + CgenSupport.LABEL);
        // push stack frame

        // if the object has parent, return (jal) to the parent
        
        // for each attribute, emitStore

    }
}



