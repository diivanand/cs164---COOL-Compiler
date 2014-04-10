Class Main{
	main(x : Int) : Int {
		3
	};
};
Class C {
   c : Int;
   fcHelper(x : Int) : Int{
   	x
   };
   fc() : Int {
   	fcHelper("hi") -- wrong function call
   };
};
Class B inherits C {
   b: Int;
   fb() : String { -- wrong return type
    {
    "this function should have a string type";
   	3;
    }
   };
};
Class D inherits B {
   d: Int <- "hi";
};

Class E inherits D {
   e : String <- fc(); -- fc() from class C should return an int
};

Class F inherits E {
   f : Bool <- "it is not boolean";
};

Class G inherits F {
   g : Int <- "it is Int";
};

Class ComplicatedIssues {
    -- int vs string of let
    a : Int <- let b : String in {
        5;
        "String Type for Int identifier";
    };
    -- string vs int
    b : String <- if true then "ignore it" else 164 fi;
    -- Bool vs String
    c : Bool <- case 5 of 
                    c1 : Int => "String";
                    c2 : Object => "another string";
                    c3 : Bool => true;
                    esac;
    -- while loop has 'Object' type
    e : Int <- let dummy : Int <- 0, d : Bool in
        d <- while dummy < 5 loop dummy <- dummy + 1 pool;
    f : Bool <- if 2=3 then "this if has object type" else 4 fi;
    --let dummyVar:Int, dummyInt:Int <- new Int in dummyVar <- isvoid dummyInt;
};
