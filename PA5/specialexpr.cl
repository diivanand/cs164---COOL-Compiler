class Main {
    num : Int <- 10;
    one_let(n : Int) : Int {
      let a:Int <- 0 in if true then n else n+1111 fi
    };
    two_let_case() : String {
      let dummy:Int<-4,dummer:Int<-dummy+1, c:Int <- case dummer of
           c1 : Object => 1;
           c2 : Bool => 2;
           c3 : String => 3;
           c4 : Int => 4;
           esac
        in "let body expression"
    };
    main() : String {
      {
        one_let(num);    
        two_let_case();
      }
    };
};

