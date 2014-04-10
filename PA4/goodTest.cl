Class A{
    a: Int;
    add() : Int {
       a + a
    };
    selfie() : SELF_TYPE {self};
};
Class B inherits A{
    b: Int;
    add(x : Int) : Int{
    	a + b + x
    };
    checky(x : Int, y : Bool) : Int{
    	if y then x else ~x fi
    };
    selfie() : SELF_TYPE {self};
};
Class C inherits B{
    c: Int <- 4;
    bClone: B <- new B;
    getClone() : B {
    	bClone
    };
};
Class D inherits A{
    d: String;
    accessMe() : String {
    	d
    };
    copy() : SELF_TYPE {new SELF_TYPE};
};
Class E inherits D{
    e: String;
    accessMe() : String {
        e
    };
    copy() : SELF_TYPE {new SELF_TYPE};
};
Class F inherits B{
    f: Int <- 5;
    bClone: B;
    getClone() : B {
    	bClone
    };
};
Class Main inherits IO{
  main() : Int {
    3
  };
};
