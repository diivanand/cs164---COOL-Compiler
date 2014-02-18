(*
 * Using Try/Finally 
 *
 * kgao@eecs
 *)

class Main inherits IO {

   make_exception() : Object {
	throw " Exception Thrown from Main\n"
   };

   print_exception(e : Object) : SELF_TYPE {
	case e of
	s : String => out_string(s);
	o : Object => out_string("Unknown Exception\n");
	esac
   };

   main() : Object {
	{
	
	out_string("1. Try Finally without exception...\n");
	try
		out_string(" No Exception Here\n")
	finally
		out_string(" Finally executes regardless!\n");

	out_string("2. Try Finally with exception...\n");
	try
		try {
			out_string(" Here comes an exception\n");
			make_exception();
		}
		finally
			out_string(" Finally executes regardless!\n")
	catch e =>
		print_exception(e);
	}
   };

   
};
