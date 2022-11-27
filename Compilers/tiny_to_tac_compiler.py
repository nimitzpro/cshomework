"""
Tree-walking compiler for the Tiny programming language.
Alexander Stradnic 119377263
"""

from tiny_parser import *
from pt_node import *
import sys
import pickle

class TinyCompiler:

    def __init__(self, path, tinyfile=False, testing=False):
        self.parse_tree = None
        if tinyfile:
            self.parse_tree = TinyParser(path).parse_program()
            # self.parse_tree.dump()
        else:
            tree = open(path, "rb")
            self.parse_tree = pickle.load(tree)
            tree.close()

        if not testing:
            output_file = open(path[:len(path) - (path[::-1].index("."))] + "tac", "w")
            sys.stdout = output_file

        self.__varcount = 0
        self.__labcount = 0
      
    def translate(self):
        self.__varcount, self.__labcount = 0, 0
        self.__codegen(self.parse_tree)
        print("halt;", end="")
        sys.stdout = sys.__stdout__

    def __codegen_comp_op(self, root):
        comparator = root.children[0].value
        if comparator == "=":
            comparator = "=="
        return comparator

    def __codegen_term(self, root):
        if len(root.children) > 1:
            total_var = self.__new_var()
            print("%s := 0;" % total_var)

            f1 = self.__codegen(root.children[0])
            op = root.children[1].children[0].value
            f2 = self.__codegen_factor(root.children[2])
            print("%s := %s %s %s;" % (total_var, f1, op, f2))
        else:
            total_var = self.__codegen_factor(root.children[0])

        return total_var

    def __codegen_factor(self, root):
        fval = root.children[0].value
        if type(fval) == int:
            var = self.__new_var()
            print("%s := %d;" % (var, fval))
            return var
        elif type(fval) == str:
            var = self.__new_var()
            print("%s := %s;" % (var, fval))
            return var
        else:
            exprvar = self.__codegen_exp(root.children[0])
            return exprvar

    def __codegen_read(self, root):
        varname = root.children[0].value
        print("%s := in;" % varname)

    def __codegen_write(self, root):
        exprvar = self.__codegen_exp(root.children[0])
        print("out := %s;" % exprvar)

    def __codegen_assign(self, root):
        destvar = root.children[0].value
        rhsvar = self.__codegen_exp(root.children[1])
        print("%s := %s;" % (destvar, rhsvar))

    def __codegen_ifstmt(self, root):
        skiptrue_label = self.__new_label()
        conditvar = self.__codegen_exp(root.children[0])
        print("if (%s == 0) goto %s;" % (conditvar, skiptrue_label))
        self.__codegen(root.children[1])
        
        if len(root.children) <= 2:
            print("%s:" % skiptrue_label)
        else:
            skipfalse_label = self.__new_label()
            print("goto %s;" % skipfalse_label)
            print("%s:" % skiptrue_label)
            self.__codegen(root.children[2])
            print("%s:" % skipfalse_label)

    def __new_var(self):
        self.__varcount += 1
        return "t%d" % self.__varcount

    def __new_label(self):
        self.__labcount += 1
        return "l%d" % self.__labcount
        
    def __codegen_repeat(self, root):
        top_label = self.__new_label()
        bottom_label = self.__new_label()
        print("%s:" % top_label)
        
        conditvar = self.__codegen_exp(root.children[1])
        print("if (%s != 0) goto %s;" % (conditvar, bottom_label))

        self.__codegen(root.children[0])
        
        print("goto %s;" % top_label)
        print("%s:" % bottom_label)

    def __codegen_stmtseq(self, root):
        for c in root.children:
            self.__codegen(c)

    def __codegen_exp(self, root):
        exp = self.__codegen_simple_expr(root.children[0])
        if len(root.children) > 1:
            total_var = self.__new_var()
            comp = self.__codegen_comp_op(root.children[1])
            exp2 = self.__codegen_simple_expr(root.children[2])

            print("%s := %s %s %s;" % (total_var, exp, comp, exp2))

            return total_var

        return exp


    def __codegen_simple_expr(self, root):

        if len(root.children) > 1:
            total_var = self.__new_var()
            simple_expr = self.__codegen(root.children[0])
            simple_expr += " " + root.children[1].children[0].value + " " # addop
            simple_expr += self.__codegen_term(root.children[2]) + ";"

            print(f"{total_var} := {simple_expr}")
            return total_var
        else:
            return self.__codegen_term(root.children[0])
        

    def __codegen(self, root):
        # print("processing", str(root), "...")
        label = root.label
        children = root.children
        value = root.value
        
        actions = {
            "readstmt" : self.__codegen_read,
            "writestmt" : self.__codegen_write,
            "assignstmt" : self.__codegen_assign,
            "repeatstmt" : self.__codegen_repeat,
            "stmtseq" : self.__codegen_stmtseq,
            "ifstmt" : self.__codegen_ifstmt,
            "factor" : self.__codegen_factor,
            "exp" : self.__codegen_exp,
            "simple_expr" : self.__codegen_simple_expr,
            "term" : self.__codegen_term
        }
        if label in actions:
            return actions[label](root)
        else:
            return self.__codegen(children[0])
            
if __name__ == "__main__":
    testing = False
    fpath = "trees/factorial_pt_kh.pkl"
    compiler = TinyCompiler(fpath, False)
    # fpath = "tny_files/ifelse.tny"
    # compiler = TinyCompiler(fpath, True)
    if testing:
        print("Compiler output:")
        print("-" * 25)
        compiler.translate()
        print("=" * 25)
        print()
    else:
        output_file = open(fpath[:len(fpath) - (fpath[::-1].index("."))] + "tac", "w")
        sys.stdout = output_file
        compiler.translate()
        output_file.close()
