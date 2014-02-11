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
		57,
		70,
		94
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
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NOT_ACCEPT,
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
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NOT_ACCEPT,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NOT_ACCEPT,
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
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NO_ANCHOR,
		/* 190 */ YY_NO_ANCHOR,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"14,13:7,4,3,1,7,5,6,13:18,2,13,12,13:5,9,11,10,58,62,8,60,59,19:10,64,63,18" +
",16,17,13,65,45,46,47,48,40,26,46,49,50,46:2,41,46,51,52,44,46,53,39,31,54," +
"55,56,46,43,46,13,15,13:2,42,13,21,57,20,34,23,25,57,29,27,57:2,24,57,28,33" +
",35,57,30,22,37,38,32,36,57:3,67,13,66,61,13,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,193,
"0,1:2,2,3,4,5,6,7,8,9,10,1:3,11,12,13,14,15,1:16,16:2,17,16:9,18,16:7,18,19" +
",1:8,20,1:2,21,22,23,24,18:2,25,18:8,16,18:5,26,27,20,1,28,29,30,31,32,20,3" +
"3,34,21,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,5" +
"7,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,8" +
"2,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,10" +
"5,106,107,108,109,110,111,112,16,113,114,115,116,18,117,118,119,120,121,122")[0];

	private int yy_nxt[][] = unpackFromString(123,68,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14:3,15,14,16,17,18,181,183,185,133,71,19,95," +
"135,181:2,72,181,100,181,187,189,191,181,182,184,134,14,186,188,186:2,190,1" +
"86:2,96,136,101,186:3,192,181,20,21,22,23,24,25,26,27,28,29,-1:70,3,-1:68,4" +
",-1:68,5,-1:68,6,-1:68,7,-1:68,8,-1:68,30,-1:69,31,-1:68,32,-1:73,33,-1:58," +
"34,-1:7,35,-1:70,17,-1:67,181:2,137,181:2,139,181:16,139,181:3,137,181:12,-" +
"1:29,186:8,73,186:22,73,186:7,-1:29,181:39,-1:29,181:10,167,181:19,167,181:" +
"8,-1:29,186:39,-1:10,1,58,90:66,-1,66,99:10,67,99:55,-1,68,102:6,-1:60,1,59" +
",60:4,-1,60:2,91,97,60:57,-1:19,181:2,149,181:5,36,181:17,149,181:4,36,181:" +
"7,-1:29,186:10,138,186:19,138,186:8,-1:29,186:10,164,186:19,164,186:8,-1:12" +
",90:66,-1:10,61,-1:57,1,63,64:4,-1,64:5,65,64,92,98,64:52,-1:19,181:3,151,1" +
"81:2,37:2,181,38,181:10,151,181:11,38,181:6,-1:29,186:3,154,186:2,74:2,186," +
"75,186:10,154,186:11,75,186:6,-1:21,62,-1:57,68,69:4,102,69,93:60,-1:19,181" +
":6,39:2,181:31,-1:29,186:6,76:2,186:31,-1:29,181:12,40,181:5,40,181:20,-1:2" +
"9,186:12,77,186:5,77,186:20,-1:29,181:17,41,181:19,41,181,-1:29,186:17,78,1" +
"86:19,78,186,-1:29,181:12,42,181:5,42,181:20,-1:29,186:12,79,186:5,79,186:2" +
"0,-1:29,181:4,43,181:16,43,181:17,-1:29,186:9,48,186:22,48,186:6,-1:29,181:" +
"6,44,181:32,-1:29,186,81,186:26,81,186:10,-1:29,181,45,181:26,45,181:10,-1:" +
"29,186:4,82,186:16,82,186:17,-1:29,181:4,46,181:16,46,181:17,-1:29,186:16,8" +
"3,186:8,83,186:13,-1:29,181:16,47,181:8,47,181:13,-1:29,186:5,85,186:16,85," +
"186:16,-1:29,181:5,49,181:16,49,181:16,-1:29,186:4,80,186:16,80,186:17,-1:2" +
"9,181:9,84,181:22,84,181:6,-1:29,186:3,86,186:16,86,186:18,-1:29,181:4,50,1" +
"81:16,50,181:17,-1:29,186:4,87,186:16,87,186:17,-1:29,181:3,51,181:16,51,18" +
"1:18,-1:29,186:15,88,186:13,88,186:9,-1:29,181:4,52,181:16,52,181:17,-1:29," +
"186:3,89,186:16,89,186:18,-1:29,181:4,53,181:16,53,181:17,-1:29,186:21,56,1" +
"86:17,-1:29,181:15,54,181:13,54,181:9,-1:29,181:3,55,181:16,55,181:18,-1:29" +
",181:4,103,181:9,147,181:6,103,181:11,147,181:5,-1:29,186:4,104,186:9,146,1" +
"86:6,104,186:11,146,186:5,-1:29,181:4,105,181:9,107,181:6,105,181:11,107,18" +
"1:5,-1:29,186:4,106,186:9,108,186:6,106,186:11,108,186:5,-1:29,181:3,109,18" +
"1:16,109,181:18,-1:29,186:4,110,186:16,110,186:17,-1:29,181:2,161,181:23,16" +
"1,181:12,-1:29,186:22,158,186:16,-1:29,181:5,111,181:33,-1:29,186:2,112,186" +
":23,112,186:12,-1:29,181:2,113,181:23,113,181:12,-1:29,186:3,114,186:16,114" +
",186:18,-1:29,181:3,115,181:16,115,181:18,-1:29,186:14,116,186:18,116,186:5" +
",-1:29,181:14,117,181:18,117,181:5,-1:29,186:14,118,186:18,118,186:5,-1:29," +
"181:5,163,181:16,163,181:16,-1:29,186:3,120,186:16,120,186:18,-1:29,181:13," +
"165,181:22,165,181:2,-1:29,186:2,160,186:23,160,186:12,-1:29,181:14,119,181" +
":18,119,181:5,-1:29,186:13,162,186:22,162,186:2,-1:29,181:8,169,181:22,169," +
"181:7,-1:29,186:8,166,186:22,166,186:7,-1:29,181:4,121,181:16,121,181:17,-1" +
":29,186:7,168,186:31,-1:29,181:19,123,181:15,123,181:3,-1:29,186:3,122,186:" +
"16,122,186:18,-1:29,181:3,125,181:16,125,181:18,-1:29,186:14,170,186:18,170" +
",186:5,-1:29,181:3,127,181:16,127,181:18,-1:29,186:4,172,186:16,172,186:17," +
"-1:29,181:14,171,181:18,171,181:5,-1:29,186:5,124,186:16,124,186:16,-1:29,1" +
"81:4,173,181:16,173,181:17,-1:29,186:23,174,186:15,-1:29,181:5,129,181:16,1" +
"29,181:16,-1:29,186:8,126,186:22,126,186:7,-1:29,181:8,131,181:22,131,181:7" +
",-1:29,186:11,176,186:22,176,186:4,-1:29,181:11,175,181:22,175,181:4,-1:29," +
"186:12,178,186:26,-1:29,181:8,177,181:22,177,181:7,-1:29,186:8,179,186:22,1" +
"79,186:7,-1:29,181:12,132,181:5,132,181:20,-1:29,186:24,180,186:14,-1:29,18" +
"6:12,128,186:5,128,186:20,-1:29,186:25,130,186:13,-1:29,186:21,140,186:17,-" +
"1:29,181:4,141,181:34,-1:29,186:3,142,186,144,186:14,142,186,144,186:16,-1:" +
"29,181:3,143,181,145,181:14,143,181,145,181:16,-1:29,181:14,153,181:18,153," +
"181:5,-1:29,186:14,148,186:18,148,186:5,-1:29,181:10,155,181:19,155,181:8,-" +
"1:29,186:2,150,186:2,152,186:16,152,186:3,150,186:12,-1:29,181:10,157,159,1" +
"81:18,157,181:3,159,181:4,-1:29,186:10,156,186:19,156,186:8,-1:10");

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
						{ return new Symbol(TokenConstants.RPAREN); }
					case -13:
						break;
					case 13:
						{ yybegin(STRING_MODE);}
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
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -19:
						break;
					case 19:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
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
						{ return new Symbol(TokenConstants.ERROR, "Unmatched *)");}
					case -33:
						break;
					case 33:
						{ return new Symbol(TokenConstants.DARROW); }
					case -34:
						break;
					case 34:
						{ return new Symbol(TokenConstants.ASSIGN); }
					case -35:
						break;
					case 35:
						{ return new Symbol(TokenConstants.LE); }
					case -36:
						break;
					case 36:
						{ return new Symbol(TokenConstants.FI); }
					case -37:
						break;
					case 37:
						{ return new Symbol(TokenConstants.IF); }
					case -38:
						break;
					case 38:
						{ return new Symbol(TokenConstants.IN); }
					case -39:
						break;
					case 39:
						{ return new Symbol(TokenConstants.OF); }
					case -40:
						break;
					case 40:
						{ return new Symbol(TokenConstants.LET); }
					case -41:
						break;
					case 41:
						{ return new Symbol(TokenConstants.NEW); }
					case -42:
						break;
					case 42:
						{ return new Symbol(TokenConstants.NOT); }
					case -43:
						break;
					case 43:
						{ return new Symbol(TokenConstants.CASE); }
					case -44:
						break;
					case 44:
						{return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext()));}
					case -45:
						break;
					case 45:
						{ return new Symbol(TokenConstants.ESAC); }
					case -46:
						break;
					case 46:
						{ return new Symbol(TokenConstants.ELSE); }
					case -47:
						break;
					case 47:
						{ return new Symbol(TokenConstants.LOOP); }
					case -48:
						break;
					case 48:
						{ return new Symbol(TokenConstants.THEN); }
					case -49:
						break;
					case 49:
						{ return new Symbol(TokenConstants.POOL); }
					case -50:
						break;
					case 50:
						{ return new Symbol(TokenConstants.BOOL_CONST, Boolean.TRUE); }
					case -51:
						break;
					case 51:
						{ return new Symbol(TokenConstants.CLASS); }
					case -52:
						break;
					case 52:
						{ return new Symbol(TokenConstants.BOOL_CONST, Boolean.FALSE); }
					case -53:
						break;
					case 53:
						{ return new Symbol(TokenConstants.WHILE); }
					case -54:
						break;
					case 54:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -55:
						break;
					case 55:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -56:
						break;
					case 56:
						{return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -57:
						break;
					case 57:
						{ /* do nothing eat it up anything that's not a newline */ }
					case -58:
						break;
					case 58:
						{ curr_lineno++; yybegin(YYINITIAL); }
					case -59:
						break;
					case 59:
						{ curr_lineno++; }
					case -60:
						break;
					case 60:
						{ /* do nothing eat it up not doing .* because maximal munch will mess things up so one at a time instead */ }
					case -61:
						break;
					case 61:
						{/*handle potential nesting by keeping count*/ paren_len++;}
					case -62:
						break;
					case 62:
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
					case -63:
						break;
					case 63:
						{ 
                  yybegin(YYINITIAL);
                  string_buf = new StringBuffer();
                  curr_lineno++;
                  return new Symbol(TokenConstants.ERROR, "Unterminated string constant"); 
                }
					case -64:
						break;
					case 64:
						{ 
                  string_buf = string_buf.append(yytext().charAt(0));
                }
					case -65:
						break;
					case 65:
						{
                            yybegin(YYINITIAL);
//                  System.out.println("quote matched");
                            if(string_buf.length() > MAX_STR_CONST) {
                              return new Symbol(TokenConstants.ERROR, "String constant too long");
                            }else {
                              String s = string_buf.toString();
                              string_buf = new StringBuffer();
 //                             System.out.println("string:"+s);
                              return new Symbol(TokenConstants.STR_CONST, AbstractTable.stringtable.addString(s));
                            }
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
						{ //eat up all characters after null and stop at ending quote (eat it too)
                            yybegin(YYINITIAL);
                            string_buf = new StringBuffer();
                            return new Symbol(TokenConstants.ERROR, "String contains null character");
                          }
					case -68:
						break;
					case 68:
						{
 //                 System.out.println("whitespace matched");
                                                                            string_buf = string_buf.append('\n');
                                                                            curr_lineno++;
                                                                          }
					case -69:
						break;
					case 69:
						{ 
                    if (yytext().equals("\\n")) {
                        string_buf = string_buf.append('\n'); 
                    } else if (yytext().equals("\\b")){
                        string_buf = string_buf.append('\b'); 
                    } else if (yytext().equals("\\t")){
                        string_buf = string_buf.append('\t'); 
                    } else if (yytext().equals("\\f")){
                        string_buf = string_buf.append('\f'); 
                    //} else if (yytext().equals("\\r")){
                    //    string_buf = string_buf.append('\r'); 
                    //} else if (yytext().equals("\\\'")) {
                    //    string_buf = string_buf.append('\''); 
                    //} else if (yytext().equals("\\\"")){
                    //    string_buf = string_buf.append('\"'); 
                    } else {
                        string_buf = string_buf.append(yytext().charAt(1));
                    }
                }
					case -70:
						break;
					case 71:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -71:
						break;
					case 72:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -72:
						break;
					case 73:
						{ return new Symbol(TokenConstants.FI); }
					case -73:
						break;
					case 74:
						{ return new Symbol(TokenConstants.IF); }
					case -74:
						break;
					case 75:
						{ return new Symbol(TokenConstants.IN); }
					case -75:
						break;
					case 76:
						{ return new Symbol(TokenConstants.OF); }
					case -76:
						break;
					case 77:
						{ return new Symbol(TokenConstants.LET); }
					case -77:
						break;
					case 78:
						{ return new Symbol(TokenConstants.NEW); }
					case -78:
						break;
					case 79:
						{ return new Symbol(TokenConstants.NOT); }
					case -79:
						break;
					case 80:
						{ return new Symbol(TokenConstants.CASE); }
					case -80:
						break;
					case 81:
						{ return new Symbol(TokenConstants.ESAC); }
					case -81:
						break;
					case 82:
						{ return new Symbol(TokenConstants.ELSE); }
					case -82:
						break;
					case 83:
						{ return new Symbol(TokenConstants.LOOP); }
					case -83:
						break;
					case 84:
						{ return new Symbol(TokenConstants.THEN); }
					case -84:
						break;
					case 85:
						{ return new Symbol(TokenConstants.POOL); }
					case -85:
						break;
					case 86:
						{ return new Symbol(TokenConstants.CLASS); }
					case -86:
						break;
					case 87:
						{ return new Symbol(TokenConstants.WHILE); }
					case -87:
						break;
					case 88:
						{ return new Symbol(TokenConstants.ISVOID); }
					case -88:
						break;
					case 89:
						{ return new Symbol(TokenConstants.INHERITS); }
					case -89:
						break;
					case 90:
						{ /* do nothing eat it up anything that's not a newline */ }
					case -90:
						break;
					case 91:
						{ /* do nothing eat it up not doing .* because maximal munch will mess things up so one at a time instead */ }
					case -91:
						break;
					case 92:
						{ 
                  string_buf = string_buf.append(yytext().charAt(0));
                }
					case -92:
						break;
					case 93:
						{ 
                    if (yytext().equals("\\n")) {
                        string_buf = string_buf.append('\n'); 
                    } else if (yytext().equals("\\b")){
                        string_buf = string_buf.append('\b'); 
                    } else if (yytext().equals("\\t")){
                        string_buf = string_buf.append('\t'); 
                    } else if (yytext().equals("\\f")){
                        string_buf = string_buf.append('\f'); 
                    //} else if (yytext().equals("\\r")){
                    //    string_buf = string_buf.append('\r'); 
                    //} else if (yytext().equals("\\\'")) {
                    //    string_buf = string_buf.append('\''); 
                    //} else if (yytext().equals("\\\"")){
                    //    string_buf = string_buf.append('\"'); 
                    } else {
                        string_buf = string_buf.append(yytext().charAt(1));
                    }
                }
					case -93:
						break;
					case 95:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -94:
						break;
					case 96:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -95:
						break;
					case 97:
						{ /* do nothing eat it up not doing .* because maximal munch will mess things up so one at a time instead */ }
					case -96:
						break;
					case 98:
						{ 
                  string_buf = string_buf.append(yytext().charAt(0));
                }
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
					case 103:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -100:
						break;
					case 104:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -101:
						break;
					case 105:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -102:
						break;
					case 106:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -103:
						break;
					case 107:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -104:
						break;
					case 108:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -105:
						break;
					case 109:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -106:
						break;
					case 110:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -107:
						break;
					case 111:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -108:
						break;
					case 112:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -109:
						break;
					case 113:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -110:
						break;
					case 114:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -111:
						break;
					case 115:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -112:
						break;
					case 116:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -113:
						break;
					case 117:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -114:
						break;
					case 118:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -115:
						break;
					case 119:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -116:
						break;
					case 120:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -117:
						break;
					case 121:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -118:
						break;
					case 122:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -119:
						break;
					case 123:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -120:
						break;
					case 124:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -121:
						break;
					case 125:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -122:
						break;
					case 126:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -123:
						break;
					case 127:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -124:
						break;
					case 128:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -125:
						break;
					case 129:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -126:
						break;
					case 130:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -127:
						break;
					case 131:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -128:
						break;
					case 132:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -129:
						break;
					case 133:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -130:
						break;
					case 134:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -131:
						break;
					case 135:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -132:
						break;
					case 136:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -133:
						break;
					case 137:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -134:
						break;
					case 138:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -135:
						break;
					case 139:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -136:
						break;
					case 140:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -137:
						break;
					case 141:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -138:
						break;
					case 142:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -139:
						break;
					case 143:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -140:
						break;
					case 144:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -141:
						break;
					case 145:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -142:
						break;
					case 146:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -143:
						break;
					case 147:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -144:
						break;
					case 148:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -145:
						break;
					case 149:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -146:
						break;
					case 150:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -147:
						break;
					case 151:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -148:
						break;
					case 152:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -149:
						break;
					case 153:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -150:
						break;
					case 154:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -151:
						break;
					case 155:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -152:
						break;
					case 156:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -153:
						break;
					case 157:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -154:
						break;
					case 158:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -155:
						break;
					case 159:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -156:
						break;
					case 160:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -157:
						break;
					case 161:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -158:
						break;
					case 162:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -159:
						break;
					case 163:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -160:
						break;
					case 164:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -161:
						break;
					case 165:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -162:
						break;
					case 166:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -163:
						break;
					case 167:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -164:
						break;
					case 168:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -165:
						break;
					case 169:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -166:
						break;
					case 170:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -167:
						break;
					case 171:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -168:
						break;
					case 172:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -169:
						break;
					case 173:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -170:
						break;
					case 174:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -171:
						break;
					case 175:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -172:
						break;
					case 176:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -173:
						break;
					case 177:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -174:
						break;
					case 178:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -175:
						break;
					case 179:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -176:
						break;
					case 180:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -177:
						break;
					case 181:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -178:
						break;
					case 182:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -179:
						break;
					case 183:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -180:
						break;
					case 184:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -181:
						break;
					case 185:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -182:
						break;
					case 186:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -183:
						break;
					case 187:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -184:
						break;
					case 188:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -185:
						break;
					case 189:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -186:
						break;
					case 190:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -187:
						break;
					case 191:
						{ /*Object Identifyer*/ return new Symbol(TokenConstants.OBJECTID, AbstractTable.stringtable.addString(yytext())); }
					case -188:
						break;
					case 192:
						{ /*Type Identifyer*/ return new Symbol(TokenConstants.TYPEID, AbstractTable.stringtable.addString(yytext())); }
					case -189:
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
