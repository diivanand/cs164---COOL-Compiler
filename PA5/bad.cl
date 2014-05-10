Class A {
    a: String <- "Hello";
    getA() : String {
        a
    };
};

Class Main inherits IO{
    a : A;
    thing: Object <- new Object;

    -- a.getA() tests dispatch on void, to see another error handle, comment out the other two you do not want
    main(): Object{
       {
        a.getA();
        b();
        c();
       }
    };

    --case on void
    b(): Object {
       case a of
               i : Int => out_string( "int\n" );
               b : Bool => out_string( "bool\n" );
       esac
    };

    --no matching case
    c(): Object{
          case thing of
                 i : Int => out_string( "int\n" );
                 b : Bool => out_string( "bool\n" );
          esac
    };
};