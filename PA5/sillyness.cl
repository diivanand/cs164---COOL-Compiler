Class A{
    a: String <- "a string";
    a_method(): String {
        a
    };
};

Class B inherits A{
    b: String <- "b string";
    b_method(): String {
        b
    };
};

Class Main inherits IO{

    main(): SELF_TYPE{
        out_string("hello world\n")
    };
};

