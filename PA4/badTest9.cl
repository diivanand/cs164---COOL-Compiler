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
   	3
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
