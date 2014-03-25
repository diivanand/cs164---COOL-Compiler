class Count {
   i : Int <- 0;
   inc () : SELF_TYPE {
        {
            i <- i + 1;
            self;
        }
    };
};  

class Stock inherits Count { 
   name : String; -- name of item
   get() :String {name };
};

class Main {
  a:Stock;
main() : Object {{
  a <- (new Stock).inc (); 
    a.get(); 
 }
};
};



















