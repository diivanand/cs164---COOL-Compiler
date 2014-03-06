
(*
 *  execute "coolc bad.cl" to see the error messages that the coolc parser
 *  generates
 *
 *  execute "./myparser bad.cl" to see the error messages that your parser
 *  generates
 *)

(* no error *)
class A {
};

(* error:  b is not a type identifier *)
Class b inherits A {
};

(* error:  a is not a type identifier *)
Class C inherits a {
};

(* error:  keyword inherits is misspelled *)
Class D inherts A {
};

(* error: feature error *)
Class F inherits A {
	NUM : Int;
};

(* error: another feature error *)
Class F inherits A {
	num : Int;
	FEATUREERROR : Int;
};

(* error: another feature error *)
Class F inherits A {
   Save_some_$
};

(* error: another feature error *)
Class F inherits A {
	num : Int;
	num2 : Int <- FEATUREERROR2;
};

(* error: block error *)
Class F inherits A {
	b(num1 : Int) : Int {
   	{
   		BLOCKERROR;
   	}
   };
};

(* error: another block error *)
Class F inherits A {
	b(num1 : Int) : Int {
   	{
   		3;
   		BLOCKERRORBETWEEN;
   		4;
   	}
   };
};

(* error: let error *)
Class G inherits A {
	let3() : Object {
       let n1 : Int <- LETBIGERROR,
           n2 : Int <- 4 in {
            numone <- n1 + n2;
       }
   };
};

(* error : no semicolon after featuren *)
Class MY inherits IO {
   nnn : Int <- 456
};


--(* error : invalid expr for a method *)
--Class C1 {
--   a() : Int out_string("hi") -- no curly braces
--};
------------- I guess this is not problemetic?

(* error: invalid argument expr *)
Class C2 {
   b(feat : Int <-3 ; fea : Int <- 2) : Object {}; -- invalid argument expr
};

(* error: wrong method call *)
Class C3 {
   c() : Object {
      out_string(ERROR_METHOD_CALL);
   };
};

(* error: case error *)
Class C4 {
   n : Int;
   
   m : Int <- n case of s:String => 3 esac;
}

(* error:  closing brace is missing *)
Class E inherits A {
;
