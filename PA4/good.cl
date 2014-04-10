class C {
	a : Int;
	b : Bool;
	init(x : Int, y : Bool) : C {
           {
		a <- x;
		b <- y;
		self;
           }
	};
};

Class Main {
	main():C {
      {
      let a:Int <- 0 in if true then 5 else "wrong" fi; -- 5
      let b:Int <- (let c : String in 5) + (if true then 5 else 5 fi) in "nothingness"; -- should be 10
      let c:Int <- case 5 of
           c1 : Object => 1;
           c2 : Bool => 2;
           c3 : String => 3;
           c4 : Int => 4;
           esac
        in "ignore this";
      let d:Bool, dummyInt:Int <- new Int in d <- isvoid dummyInt;
      let a:Int <- 1, b:Int <- 2, c:Bool<-false, d:Bool<-true, e:String<-"hi", f:String <-"bye" in {
        b <- b/a; -- 2
        a <- a+b; -- 3
        b <- a-b; -- 1
        a <- a*b; -- 3
        b <- b+2; -- 3

        c <- a<b; -- false
        d <- a<=b; -- true 
        c <- c=d; -- false
        
        c <- e=f; -- false     
        d <- not c; -- true

        b <- ~b; -- negative 3
      };
	  (new C).init(1,true);
      }
	};
};
