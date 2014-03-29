class Object {
    f : Int;
};

class C inherits Main {
    a : Int;
    b : Bool;
    init(x : Int, y : Bool) : C {
           {
        a <- x;
        b <- y;
        self;
           }
    };
};

Class Main inherits C {
    main():C {
      (new C).init(1,true)
    };
};


