class Main inherits IO {
    no_add():Int {
        1
    };
    simple_add(a:Int, b:Int):Int {
        a+b
    };
    hard_add(a:Int, b:Int, c:Int, d:Int):Int {
        a+b+c+d
    };
    main() : Int {
      {
        no_add();
        simple_add(1,2);
        hard_add(1,2,3,4);
      }
    };
};
