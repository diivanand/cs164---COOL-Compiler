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
        c <- 164 + 35; -- 189
        b <- "new b string";
      }
    };
    b_method(): String {
        b
    };
    c_method(): Int {
        c <- c + 30 -- 194 
    };
};

Class Main inherits IO{
    aObj : A <- new A;
    bObj: B <- new B;
    main(): SELF_TYPE{


        out_string(bObj.a_method())

    };
};

