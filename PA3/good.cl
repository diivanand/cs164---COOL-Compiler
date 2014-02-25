class A {
}; 
Class BB__ inherits A {
};

Class C inherits IO {
   b1 : Bool; -- default false
   b2 : Bool <- true;
   numone : Int <- 3;
   numtwo : Int <- numone + 5 * 3;
   numthr : Int <- (numtwo + 1) * 2;
   a(num1 : Int, str2: String) : Int {
   	out_string("hello world")
   };
   b(num1 : Int) : String {
   	if isvoid numtwo then out_string("is void") else out_string("is not void") fi
   };
   c() : String {
   	if not isvoid numone then out_string("is not void") else out_string("is void") fi
   };
   d() : SELF_TYPE {
   	while ~numone < 0 loop self pool
   };
   e() : Object {
    case 1=2 of
        b1 : Bool => true;
    esac
    };
   f() : Object {
    case 1=2 of
        b1 : Bool => true;
        b2 : Bool => true;
    esac
    };
   g() : Object {
       { out_string("hi");}
   };
   h() : Object {
       {
        out_string("hi");
        out_string("and bye");
       }
   };
   let1() : Object {
       let n1 : Int in {
            numone <- n1 + 3;
       }
   };
   let2() : Object {
       let n1 : Int <- 3 in {
            numone <- n1 + 4;
       }
   };
   let3() : Object {
       let n1 : Int <- 3,
           n2 : Int <- 4 in {
            numone <- n1 + n2;
       }
   };
   let4() : Object {
       let n1 : Int,
           n2 : Int in {
            numone <- n1 + n2;
       }
   };
};
