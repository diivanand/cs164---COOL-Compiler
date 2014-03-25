class A {
    foo(a:Int, b: B, c:A, d:B) : SELF_TYPE {
       self
    };
  
};

class B inherits A {

    moo() : SELF_TYPE {
       let b:C <- new C in
         foo(4, b, b, b)
    };  
};

class C inherits B {

   foo:Int;
};
class Main { main () : Int { 6 }; };
