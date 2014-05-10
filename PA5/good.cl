class A_Class {
    a : Int <- 1111;
    a_method() : Int {
        a
    };
};

class B_Class inherits A_Class{
    b : Int <- 2222;
    a_method() : Int {
        b
    };
};

class Main inherits IO {
    dummy_return : Int <- 999;
    num_a : Int <- 5;
    while_var:Int <- 2;
    b_var : A_Class <- new B_Class;
    recite( num : Int ) : Int {
        {
            out_int(num);
            num;
        }
    };
    test_while() : Object {
        while 0 < while_var loop {
            out_string("expect two of these!");
            while_var <- while_var - 1;
        } pool
    };
    --test_let_and_case() : SELF_TYPE {
    --  let dummy:Int<-4,dummer:Int<-dummy+1, smart:String <- case dummer of
    --       c1 : Object => "Wrong Case 1";
    --       c2 : Bool => "Wrong Case 2";
    --       c3 : String => "Wrong Case 3";
    --       c4 : Int => "Correct Case";
    --       esac
    --  in out_string(smart)
    --};
    main() : Int {
        {
            test_while();
    --        test_let_and_case();
            recite(~5);
            if recite(0+1) < recite(4-2) then
                out_string("should be printed\n") else
                out_string("should not be printed\n") fi;
            if recite(1*3) < recite(12/3) then
                out_string("should be printed\n") else
                out_string("should not be printed\n") fi;
            if 1+2 = 3 then
                out_string("should be printed\n") else
                out_string("should not be printed\n") fi;
            --if 1+2 = recite(3) then
            --    out_string("should be printed\n") else
            --    out_string("should not be printed\n") fi;
            if 4 <= recite(4) then
                out_string("should be printed\n") else
                out_string("should not be printed\n") fi;
            if 3 = recite(5) then
                out_string("should not be printed\n") else
                out_string("should be printed\n") fi;
            if recite(6) < 6 then
                out_string("should not be printed\n") else
                out_string("should be printed\n") fi;
           -- if -recite(6) = -6 then
           --     out_string("should be printed\n") else
           --     out_string("should not be printed\n") fi;
            recite(b_var.a_method());
           -- recite(b_var@A_Class.a_method());
            dummy_return;        
        }
    };
};
