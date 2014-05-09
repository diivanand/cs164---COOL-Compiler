Class A{
    a: String <- "a string";
    a_method(): String {
        a
    };
};

Class B inherits A{
    b: String <- "b string";
    c: Int <- 164;
    a_method(): String {
      {
        c <- c_method(c, 25,"useless comment"); -- 189
        b <- b_method();
        b <- a;
        b <- "new b string";
      }
    };
    b_method(): String {
        b
    };
    c_method(a:Int, b:Int, c:String): Int {
        a+b
    };
};

Class Main inherits IO{
    aObj : A <- new A;
    bObj: B <- new B;
    main(): SELF_TYPE{


        out_string(bObj.a_method())

    };
};

