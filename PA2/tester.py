import sys
import os
import subprocess

test_files = ["foo.cl", "test.cl"]


for t in test_files:
    os.system("reference-lexer "+t+" > "+t+"_out1")
    os.system("./lexer "+t+" > "+t+"_out2")
    print "* * * * Testing the file . . . . . " + t
    os.system("diff -u "+t+"_out1 "+t+"_out2")
    os.system("rm "+t+"_out1")
    os.system("rm "+t+"_out2")

test_files = ["tests/"+f[:-3] for f in os.listdir("tests") if f.endswith(".cl")]

for t in test_files:
    os.system("./lexer "+t+".cl > "+t+".myout")
    print "* * * * Testing the file . . . . . " + t
    os.system("diff -u "+t+".myout "+t+".out.reference")
    os.system("rm "+t+".myout")
    
test_files = ["./examples/"+f[:-3] for f in os.listdir("examples") if f.endswith(".cl")]

#for t in test_files:
#    os.system("./lexer "+t+".cl > "+t+".myout")
#    os.system("reference-lexer "+t+".cl > "+t+".myout2")
#    print "* * * * Testing the file . . . . . " + t
#    os.system("diff -u "+t+".myout "+t+".myout2")
#    os.system("rm "+t+".myout")
#    os.system("rm "+t+".myout2")
