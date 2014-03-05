
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
	NUM : Int;
};

(* error: another feature error *)
Class F inherits A {
	num : Int;
	num2 : Int <- BIG;
};

(* error: block error *)
Class F inherits A {
	b(num1 : Int) : Int {
   	{
   		BIG;
   	}
   };
};

(* error: another block error *)
Class F inherits A {
	b(num1 : Int) : Int {
   	{
   		3;
   		INT;
   		4;
   	}
   };
};

(* error:  closing brace is missing *)
Class E inherits A {
;

