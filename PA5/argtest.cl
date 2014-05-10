class Main inherits IO {
    no_add():Int { -- 12 / 12
        1
    };
    no_add2():Int { -- 20 / 20
      {
        1234+5678;
        1;
      }
    };
    no_add3(a : Int):Int { -- 12 / 16
        a
    };
    decision_add ( num : Int, decision : Bool ) : Int {
        if decision then num + 321 else num fi
    };
    simple_add(a:Int, b:Int):Int { -- 20 / 28 
        a+b
    };
    hard_add(a:Int, b:Int, c:Int, d:Int):Int { -- 20 / 36
        a+b+c+d
    };
    hard_add2(a:Int, b:Int, c:Int, d:Int):Int { -- 20 / 36
      {
        a + 528491; -- we make a new object but dont store
        a<-b+c + 164; -- we store new object
        a + c + d; -- we make a object AND store it in ACC
      }  
    };
    simple_str(s: String):String { -- 12 / 16
        s
    };
    main() : Int {
      {
        no_add();
        no_add2();
        no_add3(1);
        decision_add(1, true);
        decision_add(1, false);
        simple_str("simple");
        simple_add(1,2);
        hard_add(1,2,3,4);
      }
    };
};
