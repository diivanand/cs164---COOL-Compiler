class Main inherits IO {
    no_add():Int {
        1
    };
    no_add2(a : Int):Int {
        a
    };
    simple_add(a:Int, b:Int):Int {
        a+b
    };
    hard_add(a:Int, b:Int, c:Int, d:Int):Int {
        a+b+c+d
    };
    hard_add2(a:Int, b:Int, c:Int, d:Int):Int {
      {
        a<-b+c + 1111;
        a - 1111 + c + d;
      }  
    };
    simple_str(s: String):String {
        s
    };
    main() : Int {
      {
        no_add();
        no_add2(1);
        simple_str("simple");
        simple_add(1,2);
        hard_add(1,2,3,4);
      }
    };
};
