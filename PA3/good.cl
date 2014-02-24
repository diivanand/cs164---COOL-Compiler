class A {
};

Class BB__ inherits A {
};

Class C inherits IO {
   numone : Int <- 3;
   numtwo : Int;
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
};
