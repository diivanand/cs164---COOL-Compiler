/*
 * CS164: Spring 2014
 * Programming Assignment 2
 *
 * The scanner definition for Cool.
 *
 * Author: Diivanand Ramalingam
 * Co-Author: Min Yoon Jung
 */
import java_cup.runtime.Symbol;


class CoolLexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

    // Max size of string constants
    static int MAX_STR_CONST = 1024;
    // For assembling string constants
    StringBuffer string_buf = new StringBuffer();
    // For line numbers
    private int curr_lineno = 1;
    int get_curr_lineno() {
	return curr_lineno;
    }
    private AbstractSymbol filename;
    void set_filename(String fname) {
	filename = AbstractTable.stringtable.addString(fname);
    }
    AbstractSymbol curr_filename() {
	return filename;
    }
    /*
     * Add extra field and methods here.
     */
    // For nested comment parenthesis balancing
    private int paren_len = 0;
    int get_paren_len() {
    return paren_len;
    }
    void reset_paren_len() {
        paren_len = 0;
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	CoolLexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	CoolLexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CoolLexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

    // empty for now
	}

	private boolean yy_eof_done = false;
	private final int BLOCK_COMMENT = 2;
	private final int LINE_COMMENT = 1;
	private final int YYINITIAL = 0;
	private final int STRING_MODE = 3;
	private final int yy_state_dtrans[] = {
		0,
		56,
		68,
		91
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NOT_ACCEPT,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NO_ANCHOR,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NO_ANCHOR,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NO_ANCHOR,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"15,14:7,4,3,1,7,5,12,14:18,2,14,11,14:5,9,13,10,58,62,8,60,59,20:10,64,63,1" +
"9,17,18,14,65,46,47,21,48,41,27,47,49,50,47:2,42,47,51,52,45,47,53,40,32,54" +
",55,56,47,44,47,14,16,14:2,43,14,22,57,6,35,24,26,57,30,28,57:2,25,57,29,34" +
",36,57,31,23,38,39,33,37,57:3,67,14,66,61,14,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,189,
"0,1:2,2,3,4,5,6,7,8,9,1:4,10,11,12,13,14,1:15,14:2,15,14:9,16,14:7,16,17,1:" +
"8,18,1:2,19,20,21,16:2,22,16:8,14,16:5,23,24,18,25,26,27,28,29,18,30,31,32," +
"33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57," +
"58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82," +
"83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105," +
"106,107,108,16,109,110,111,112,113,114,115,116,117,118,119,120")[0];

	private int yy_nxt[][] = unpackFromString(121,68,
"1,2,3,4,5,6,7,8,9,10,11,12,-1,13,14:3,15,14,16,17,18,19,171,173,130,70,69,9" +
"3,132,19:2,170,19,98,19,175,185,177,19,172,174,129,14,176,178,176:4,92,131," +
"97,176:3,180,19,20,21,22,23,24,25,26,27,28,29,-1:70,3,-1:68,4,-1:68,5,-1:68" +
",6,-1:68,169,-1:13,19:2,134,19:2,179,19:16,179,19:3,134,19:11,-1:17,8,-1:68" +
",30,-1:69,31,-1:75,32,-1:57,33,-1:8,34,-1:70,17,-1:53,176,-1:13,176:2,133,1" +
"76:2,135,176:16,135,176:3,133,176:11,-1:16,19,-1:13,19:38,-1:16,19,-1:13,19" +
":10,188,19:18,188,19:8,-1:16,176,-1:13,176:38,-1:10,1,57,88:66,-1,65,96:9,6" +
"6,96:56,1,58,59:7,89,94,59,-1,59:55,-1:6,176,-1:13,176:8,71,176:21,71,176:7" +
",-1:16,19,-1:13,19:2,181,19:5,35,19:17,181,19:3,35,19:7,-1:16,176,-1:13,176" +
":10,159,176:18,159,176:8,-1:12,88:66,-1:10,60,-1:57,1,62,63:9,64,-1,63:2,90" +
",95,63:51,-1:6,176,-1:13,176:3,149,176:2,72:2,176,73,176:10,149,176:10,73,1" +
"76:6,-1:16,19,-1:13,19:3,186,19:2,36:2,19,37,19:10,186,19:10,37,19:6,-1:23," +
"61,-1:55,67,-1:72,176,-1:13,176:6,74:2,176:30,-1:16,19,-1:13,19:6,38:2,19:3" +
"0,-1:16,176,-1:13,176:12,75,176:5,75,176:19,-1:16,19,-1:13,19:12,39,19:5,39" +
",19:19,-1:16,176,-1:13,176:17,76,176:18,76,176,-1:16,19,-1:13,19:17,40,19:1" +
"8,40,19,-1:16,176,-1:13,176:12,77,176:5,77,176:19,-1:16,19,-1:13,19:12,41,1" +
"9:5,41,19:19,-1:16,176,-1:13,176:4,78,176:16,78,176:16,-1:16,19,-1:13,19:4," +
"42,19:16,42,19:16,-1:16,176,-1:13,176:9,47,176:21,47,176:6,-1:16,19,-1:13,1" +
"9:6,43,19:31,-1:16,79,-1:13,176,79,176:36,-1:16,44,-1:13,19,44,19:36,-1:16," +
"176,-1:13,176:4,80,176:16,80,176:16,-1:16,19,-1:13,19:4,45,19:16,45,19:16,-" +
"1:16,176,-1:13,176:16,81,176:8,81,176:12,-1:16,19,-1:13,19:16,46,19:8,46,19" +
":12,-1:16,176,-1:13,176:5,83,176:16,83,176:15,-1:16,19,-1:13,19:5,48,19:16," +
"48,19:15,-1:16,176,-1:13,176:3,84,176:16,84,176:17,-1:16,19,-1:13,19:9,82,1" +
"9:21,82,19:6,-1:16,176,-1:13,176:4,85,176:16,85,176:16,-1:16,19,-1:13,19:4," +
"49,19:16,49,19:16,-1:16,176,-1:13,176:15,86,176:12,86,176:9,-1:16,19,-1:13," +
"19:3,50,19:16,50,19:17,-1:16,176,-1:13,176:3,87,176:16,87,176:17,-1:16,19,-" +
"1:13,19:4,51,19:16,51,19:16,-1:16,176,-1:13,176:21,55,176:16,-1:16,19,-1:13" +
",19:4,52,19:16,52,19:16,-1:16,19,-1:13,19:15,53,19:12,53,19:9,-1:16,19,-1:1" +
"3,19:3,54,19:16,54,19:17,-1:16,176,-1:13,176:4,99,176:9,145,176:6,99,176:10" +
",145,176:5,-1:16,19,-1:13,19:4,100,19:9,142,19:6,100,19:10,142,19:5,-1:16,1" +
"76,-1:13,176:4,101,176:9,103,176:6,101,176:10,103,176:5,-1:16,19,-1:13,19:4" +
",102,19:9,104,19:6,102,19:10,104,19:5,-1:16,176,-1:13,176:3,105,176:16,105," +
"176:17,-1:16,19,-1:13,19:3,106,19:16,106,19:17,-1:16,176,-1:13,176:2,153,17" +
"6:23,153,176:11,-1:16,19,-1:13,19:5,108,19:32,-1:16,176,-1:13,176:4,107,176" +
":16,107,176:16,-1:16,19,-1:13,19:2,110,19:23,110,19:11,-1:16,176,-1:13,176:" +
"22,155,176:15,-1:16,19,-1:13,19:3,112,19:16,112,19:17,-1:16,176,-1:13,176:2" +
",109,176:23,109,176:11,-1:16,19,-1:13,19:14,114,19:17,114,19:5,-1:16,176,-1" +
":13,176:3,111,176:16,111,176:17,-1:16,19,-1:13,19:14,116,19:17,116,19:5,-1:" +
"16,176,-1:13,176:14,113,176:17,113,176:5,-1:16,19,-1:13,19:4,118,19:16,118," +
"19:16,-1:16,176,-1:13,176:14,115,176:17,115,176:5,-1:16,19,-1:13,19:19,120," +
"19:14,120,19:3,-1:16,176,-1:13,176:13,157,176:21,157,176:2,-1:16,19,-1:13,1" +
"9:3,122,19:16,122,19:17,-1:16,176,-1:13,176:8,160,176:21,160,176:7,-1:16,19" +
",-1:13,19:3,124,19:16,124,19:17,-1:16,176,-1:13,176:3,117,176:16,117,176:17" +
",-1:16,19,-1:13,19:5,126,19:16,126,19:15,-1:16,176,-1:13,176:7,161,176:30,-" +
"1:16,19,-1:13,19:8,127,19:21,127,19:7,-1:16,176,-1:13,176:14,162,176:17,162" +
",176:5,-1:16,19,-1:13,19:12,128,19:5,128,19:19,-1:16,176,-1:13,176:4,163,17" +
"6:16,163,176:16,-1:16,176,-1:13,176:5,119,176:16,119,176:15,-1:16,176,-1:13" +
",176:23,164,176:14,-1:16,176,-1:13,176:8,121,176:21,121,176:7,-1:16,176,-1:" +
"13,176:11,165,176:21,165,176:4,-1:16,176,-1:13,176:12,166,176:25,-1:16,176," +
"-1:13,176:8,167,176:21,167,176:7,-1:16,176,-1:13,176:24,168,176:13,-1:16,17" +
"6,-1:13,176:12,123,176:5,123,176:19,-1:16,176,-1:13,176:25,125,176:12,-1:16" +
",169,-1:13,19:38,-1:16,176,-1:13,176:10,137,176:18,137,176:8,-1:16,19,-1:13" +
",19:4,136,19:33,-1:16,176,-1:13,176:21,139,176:16,-1:16,19,-1:13,19:3,138,1" +
"9,140,19:14,138,19,140,19:15,-1:16,176,-1:13,176:3,141,176,143,176:14,141,1" +
"76,143,176:15,-1:16,19,-1:13,19:14,144,19:17,144,19:5,-1:16,19,-1:13,19:10," +
"146,148,19:17,146,19:3,148,19:4,-1:16,176,-1:13,176:14,147,176:17,147,176:5" +
",-1:16,19,-1:13,19:2,150,19:23,150,19:11,-1:16,176,-1:13,176:10,151,176:18," +
"151,176:8,-1:16,19,-1:13,19:5,152,19:16,152,19:15,-1:16,19,-1:13,19:8,154,1" +
"9:21,154,19:7,-1:16,19,-1:13,19:14,156,19:17,156,19:5,-1:16,19,-1:13,19:8,1" +
"58,19:21,158,19:7,-1:16,19,-1:13,19:10,182,19:18,182,19:8,-1:16,19,-1:13,19" +
":13,183,19:21,183,19:2,-1:16,19,-1:13,19:11,184,19:21,184,19:4,-1:16,19,-1:" +
"13,19:4,187,19:16,187,19:16,-1:10");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

    switch(yy_lexical_state) {
    case YYINITIAL:
      return new Symbol(TokenConstants.EOF);
/* If necessary, add code for other states here, e.g: */
    case LINE_COMMENT:
	   yybegin(YYINITIAL);
	   break;
    case BLOCK_COMMENT:
       yybegin(YYINITIAL);
       return new Symbol(TokenConstants.ERROR, "EOF in comment");
    case STRING_MODE:
       yybegin(YYINITIAL);
       string_buf = new StringBuffer();
       return new Symbol(TokenConstants.ERROR, "EOF in string constant");
    }
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ curr_lineno++; }
					case -3:
						break;
					case 3:
						{ /* do nothing just eat it up */ }
					case -4:
						break;
					case 4:
						{ /* do nothing just eat it up */ }
					case -5:
						break;
					case 5:
						{ /* do nothing just eat it up */ }
					case -6:
						break;
					case 6:
						{ /* do nothing just eat it up */ }
					case -7:
						break;
					case 7:
						{ /* do nothing just eat it up */ }
					case -8:
						break;
					case 8:
						{ /* do nothing just eat it up */ }
					case -9:
						break;
					case 9:
						{ return new Symbol(TokenConstants.MINUS); }
					case -10:
						break;
					case 10:
						{ return new Symbol(TokenConstants.LPAREN); }
					case -11:
						break;
					case 11:
						{ return new Symbol(TokenConstants.MULT); }
					case -12:
						break;
					case 12:
						{ yybegin(STRING_MODE);}
					case -13:
						break;
					case 13:
						{ return new Symbol(TokenConstants.RPAREN); }
					case -14:
						break;
					case 14:
						{ /*
                    *  This should be the very last rule and will match
                    *  everything not matched by other lexical rules.
                    */
                   return new Symbol(TokenConstants.ERROR, yytext());
                   //System.err.println("LEXER BUG - UNMATCHED: " + yytext()); 
                 }
					case -15:
						break;
					case 15:
						{ return new Symbol(TokenConstants.EQ); }
					case -16:
						break;
					case 16:
						{ return new Symbol(TokenConstants.LT); }
					case -17:
						break;
					case 17:
						{ /* Integers */
                          return new Symbol(TokenConstants.INT_CONST,
					    AbstractTable.inttable.addString(yytext())); }
					case -18:
						break;
					case 18:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -19:
						break;
					case 19:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -20:
						break;
					case 20:
						{ return new Symbol(TokenConstants.PLUS); }
					case -21:
						break;
					case 21:
						{ return new Symbol(TokenConstants.DIV); }
					case -22:
						break;
					case 22:
						{ return new Symbol(TokenConstants.DOT); }
					case -23:
						break;
					case 23:
						{ return new Symbol(TokenConstants.NEG); }
					case -24:
						break;
					case 24:
						{ return new Symbol(TokenConstants.COMMA); }
					case -25:
						break;
					case 25:
						{ return new Symbol(TokenConstants.SEMI); }
					case -26:
						break;
					case 26:
						{ return new Symbol(TokenConstants.COLON); }
					case -27:
						break;
					case 27:
						{ return new Symbol(TokenConstants.AT); }
					case -28:
						break;
					case 28:
						{ return new Symbol(TokenConstants.RBRACE); }
					case -29:
						break;
					case 29:
						{ return new Symbol(TokenConstants.LBRACE); }
					case -30:
						break;
					case 30:
						{ yybegin(LINE_COMMENT); }
					case -31:
						break;
					case 31:
						{ paren_len++; yybegin(BLOCK_COMMENT);}
					case -32:
						break;
					case 32:
						{ return new Symbol(TokenConstants.DARROW); }
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.LE); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.FI); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.IF); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.IN); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.OF); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.LET); }
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.NEW); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.NOT); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.CASE); }
					case -43:
						break;
					case 43:
						{return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext()));}
					case -44:
						break;
					case 44:
						{ return new Symbol(TokenConstants.ESAC); }
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.ELSE); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.LOOP); }
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.THEN); }
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.POOL); }
					case -49:
						break;
					case 49:
						{ return new Symbol(TokenConstants.BOOL_CONST, Boolean.TRUE); }
					case -50:
						break;
					case 50:
						{ return new Symbol(TokenConstants.CLASS); }
					case -51:
						break;
					case 51:
						{ return new Symbol(TokenConstants.BOOL_CONST, Boolean.FALSE); }
					case -52:
						break;
					case 52:
						{ return new Symbol(TokenConstants.WHILE); }
					case -53:
						break;
					case 53:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -54:
						break;
					case 54:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -55:
						break;
					case 55:
						{return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -56:
						break;
					case 56:
						{ /* do nothing eat it up anything that's not a newline */ }
					case -57:
						break;
					case 57:
						{ curr_lineno++; yybegin(YYINITIAL); }
					case -58:
						break;
					case 58:
						{ curr_lineno++; }
					case -59:
						break;
					case 59:
						{ /* do nothing eat it up not doing .* because maximal munch will mess things up so one at a time instead */ }
					case -60:
						break;
					case 60:
						{/*handle potential nesting by keeping count*/ paren_len++;}
					case -61:
						break;
					case 61:
						{ 
                            paren_len--;
                            if(paren_len < 0) {
                                return new Symbol(TokenConstants.ERROR, "Unmatched *)");
                            }else if (paren_len == 0) {
                                /*the nesting was balanced and we are outside block comment*/
                                yybegin(YYINITIAL);
                            }else {
                                /*do nothing eat up character still inside nesting*/
                            }
                        }
					case -62:
						break;
					case 62:
						{ 
                  yybegin(YYINITIAL);
                  string_buf = new StringBuffer();
                  curr_lineno++;
                  return new Symbol(TokenConstants.ERROR, "Unterminated string constant"); 
                }
					case -63:
						break;
					case 63:
						{ 
                  string_buf = string_buf.append(yytext().charAt(0));
                }
					case -64:
						break;
					case 64:
						{
                            yybegin(YYINITIAL);
                            if(string_buf.length() > MAX_STR_CONST) {
                              return new Symbol(TokenConstants.ERROR, "String constant too long");
                            }else {
                              String s = string_buf.toString();
                              string_buf = new StringBuffer();
                              return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(s));
                            }
                          }
					case -65:
						break;
					case 65:
						{ //eat up all characters after null and stop at ending quote (eat it too)
                            yybegin(YYINITIAL);
                            string_buf = new StringBuffer();
                            return new Symbol(TokenConstants.ERROR, "String contains null character");
                          }
					case -66:
						break;
					case 66:
						{ //eat up all characters after null and stop at ending quote (eat it too)
                            yybegin(YYINITIAL);
                            string_buf = new StringBuffer();
                            return new Symbol(TokenConstants.ERROR, "String contains null character");
                          }
					case -67:
						break;
					case 67:
						{
                  string_buf = string_buf.append('\n');
                  curr_lineno++;
                }
					case -68:
						break;
					case 69:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -69:
						break;
					case 70:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -70:
						break;
					case 71:
						{ return new Symbol(TokenConstants.FI); }
					case -71:
						break;
					case 72:
						{ return new Symbol(TokenConstants.IF); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.IN); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.OF); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.LET); }
					case -75:
						break;
					case 76:
						{ return new Symbol(TokenConstants.NEW); }
					case -76:
						break;
					case 77:
						{ return new Symbol(TokenConstants.NOT); }
					case -77:
						break;
					case 78:
						{ return new Symbol(TokenConstants.CASE); }
					case -78:
						break;
					case 79:
						{ return new Symbol(TokenConstants.ESAC); }
					case -79:
						break;
					case 80:
						{ return new Symbol(TokenConstants.ELSE); }
					case -80:
						break;
					case 81:
						{ return new Symbol(TokenConstants.LOOP); }
					case -81:
						break;
					case 82:
						{ return new Symbol(TokenConstants.THEN); }
					case -82:
						break;
					case 83:
						{ return new Symbol(TokenConstants.POOL); }
					case -83:
						break;
					case 84:
						{ return new Symbol(TokenConstants.CLASS); }
					case -84:
						break;
					case 85:
						{ return new Symbol(TokenConstants.WHILE); }
					case -85:
						break;
					case 86:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -86:
						break;
					case 87:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -87:
						break;
					case 88:
						{ /* do nothing eat it up anything that's not a newline */ }
					case -88:
						break;
					case 89:
						{ /* do nothing eat it up not doing .* because maximal munch will mess things up so one at a time instead */ }
					case -89:
						break;
					case 90:
						{ 
                  string_buf = string_buf.append(yytext().charAt(0));
                }
					case -90:
						break;
					case 92:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -91:
						break;
					case 93:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -92:
						break;
					case 94:
						{ /* do nothing eat it up not doing .* because maximal munch will mess things up so one at a time instead */ }
					case -93:
						break;
					case 95:
						{ 
                  string_buf = string_buf.append(yytext().charAt(0));
                }
					case -94:
						break;
					case 97:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -95:
						break;
					case 98:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -96:
						break;
					case 99:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -97:
						break;
					case 100:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -98:
						break;
					case 101:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -99:
						break;
					case 102:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -100:
						break;
					case 103:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -101:
						break;
					case 104:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -102:
						break;
					case 105:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -103:
						break;
					case 106:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -104:
						break;
					case 107:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -105:
						break;
					case 108:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -106:
						break;
					case 109:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -107:
						break;
					case 110:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -108:
						break;
					case 111:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -109:
						break;
					case 112:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -110:
						break;
					case 113:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -111:
						break;
					case 114:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -112:
						break;
					case 115:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -113:
						break;
					case 116:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -114:
						break;
					case 117:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -115:
						break;
					case 118:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -116:
						break;
					case 119:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -117:
						break;
					case 120:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -118:
						break;
					case 121:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -119:
						break;
					case 122:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -120:
						break;
					case 123:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -121:
						break;
					case 124:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -122:
						break;
					case 125:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -123:
						break;
					case 126:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -124:
						break;
					case 127:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -125:
						break;
					case 128:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -126:
						break;
					case 129:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -127:
						break;
					case 130:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -128:
						break;
					case 131:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -129:
						break;
					case 132:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -130:
						break;
					case 133:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -131:
						break;
					case 134:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -132:
						break;
					case 135:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -133:
						break;
					case 136:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -134:
						break;
					case 137:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -135:
						break;
					case 138:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -136:
						break;
					case 139:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -137:
						break;
					case 140:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -138:
						break;
					case 141:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -139:
						break;
					case 142:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -140:
						break;
					case 143:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -141:
						break;
					case 144:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -142:
						break;
					case 145:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -143:
						break;
					case 146:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -144:
						break;
					case 147:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -145:
						break;
					case 148:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -146:
						break;
					case 149:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -147:
						break;
					case 150:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -148:
						break;
					case 151:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -149:
						break;
					case 152:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -150:
						break;
					case 153:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -151:
						break;
					case 154:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -152:
						break;
					case 155:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -153:
						break;
					case 156:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -154:
						break;
					case 157:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -155:
						break;
					case 158:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -156:
						break;
					case 159:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -157:
						break;
					case 160:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -158:
						break;
					case 161:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -159:
						break;
					case 162:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -160:
						break;
					case 163:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -161:
						break;
					case 164:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -162:
						break;
					case 165:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -163:
						break;
					case 166:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -164:
						break;
					case 167:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -165:
						break;
					case 168:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -166:
						break;
					case 169:
						{ /* do nothing just eat it up */ }
					case -167:
						break;
					case 170:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -168:
						break;
					case 171:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -169:
						break;
					case 172:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -170:
						break;
					case 173:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -171:
						break;
					case 174:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -172:
						break;
					case 175:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -173:
						break;
					case 176:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -174:
						break;
					case 177:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -175:
						break;
					case 178:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -176:
						break;
					case 179:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -177:
						break;
					case 180:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -178:
						break;
					case 181:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -179:
						break;
					case 182:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -180:
						break;
					case 183:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -181:
						break;
					case 184:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -182:
						break;
					case 185:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -183:
						break;
					case 186:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -184:
						break;
					case 187:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -185:
						break;
					case 188:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -186:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
