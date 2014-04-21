(* Example from the Coolaid Reference Manual *)

class Parent {
  next() : Parent { self };
};

class Child inherits Parent {
};

class Main {
  scan(y : Child) : Object {{
    let x : Parent <- y in
      while not (isvoid x) loop
        x <- x.next ()
      pool;
  }};

  main() : Object {
    scan(new Child)
  };
};
