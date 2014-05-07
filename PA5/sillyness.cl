Class A{
    a: String <- "a string";
    a_method(): String {
        a
    };
};

Class B inherits A{
    b: String <- "b string";
    a_method(): String {
        b
    };
    b_method(): String {
        b
    };
};

Class Main inherits IO{
    aObj : A <- new A;
    bObj: B <- new B;
    main(): SELF_TYPE{


        out_string(bObj.a_method())

    };
};

