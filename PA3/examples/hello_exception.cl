(* Hello World with Exceptions
 * 
 * The Main class calls class E, which generates an exception
 * The message "I'm not cool", should never be printed
 * 
 * kgao@eecs
 *)

class Main inherits IO {
   main() : SELF_TYPE {
	try {
		make_exception();
		out_string("I'm not cool.\n");
	}
	catch e =>
		case e of
		s : String => out_string("Hello Exception!\n");
		o : Object => out_string("Unknown Exception\n");
		esac
   };

   make_exception() : Object {
	{
	throw "New Exception";
	out_string("I'm still not cool.\n");
	}
   };
};
