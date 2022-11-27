"""
Tree-building parser for Tiny.
Alexander Stradnic 119377263
"""

from tiny_scanner import *
from pt_node import *
import sys

import pickle


class TinyParser:
    
    def __init__(self, sourcepath):
        self.__scanner = TinyScanner(sourcepath, verbose = False)
   
    def parse_program(self):
        """ Parse tokens matching following production:
        <program> -> <stmtseq>
        """
        self.__scanner.log("Parsing <program> -> <stmtseq>")
        c = self.parse_stmtseq()
        return PTNode("program", [c])

    def parse_stmtseq(self, looping=False):     
        """ Parse tokens matching following production:
        <stmtseq> -> <stmtseq> ; <statement>
           |   <statement>
        """
        self.__scanner.log("Parsing <stmtseq> -> <stmtseq> ; <statement>"
           "|   <statement>")
        
        c = self.parse_statement()
        children = [c]
        statement = 1
        while self.__scanner.current.kind in {"ID", "READ", "WRITE", "REPEAT", 
                                    "IF", "UNTIL", "SEMI"}:
            if looping:
                if self.__scanner.current.kind == "UNTIL":
                    return PTNode("stmtseq", children)

            if statement:
                if self.__scanner.current.kind == "SEMI":
                    self.__scanner.match("SEMI")
                    statement = 0
                else:
                    self.__scanner.shriek("missing ';'")
            else:
                children.append(self.parse_statement())
                statement = 1
            
        return PTNode("stmtseq", children)
    
    def parse_statement(self):     
        """ Parse tokens matching following production:
                <statement> -> 
                    <simple-expr>  |
                    | <ifstmt>|  <repeatstmt>
        """
    
        self.__scanner.log("Parsing <statement> -> " 
            "    <ifstmt> "
            "|   <repeatstmt>"
            "|   <assignstmt>"
            "|   <readstmt>"
            "|   <writestmt>")
        
        c = []
        if self.__scanner.current.kind == "IF":
            c = self.parse_ifstmt()
        elif self.__scanner.current.kind == "REPEAT":
            c = self.parse_repeatstmt()
        elif self.__scanner.current.kind == "ID":
            c = self.parse_assignstmt()
        elif self.__scanner.current.kind == "READ":
            c = self.parse_read_stmt()
        elif self.__scanner.current.kind == "WRITE":
            c = self.parse_writestmt()
        return PTNode("statement", [c])
        
    def parse_simple_expr(self):     
        """ Parse tokens matching following production:
                <simple-expr> ->
                <simple-expr> <addop> <term> 
                | <term>
        """
        self.__scanner.log("Parsing <simple-expr> ->" 
                "<simple-expr> <addop> <term> "
                "| <term>")
        
        f = self.parse_term()
        children = [f]
        while self.__scanner.current.kind in {"PLUS", "MINUS"}:
            m = self.parse_addop()
            children.append(m)
            f = self.parse_simple_expr()
            children.append(f)
        
        return PTNode("simple-expr", children)
    
    def parse_ifstmt(self):     
        """ Parse tokens matching following production:
                <ifstmt> -> 
                    IF <exp> THEN <stmtseq> END
        |   IF <exp> THEN <stmtseq> ELSE <stmtseq> END
        """
        self.__scanner.log("<ifstmt> -> "
                    "IF <exp> THEN <stmtseq> END"
        "|   IF <exp> THEN <stmtseq> ELSE <stmtseq> END")
        
        self.__scanner.match("IF")
        c = self.parse_exp()
        self.__scanner.match("THEN")
        s1 = self.parse_stmtseq()
        children = [c, s1] 
        if self.__scanner.current.kind == "ELSE":
            self.__scanner.match("ELSE")
            s2 = self.parse_stmtseq()
            children.append(s2)
            
        self.__scanner.match("END")

        return PTNode("ifstmt", children)    
    
    
    def parse_repeatstmt(self):     
        """ Parse tokens matching following production:
                <repeatstmt> -> 
                    REPEAT <stmtseq> UNTIL <condition>
        """
        self.__scanner.log("Parsing <repeatstmt> ->" 
                    " REPEAT <stmtseq> UNTIL <exp>")
        self.__scanner.match("REPEAT")
        s = self.parse_stmtseq(looping=True)
        self.__scanner.match("UNTIL")
        c = self.parse_exp()
        return PTNode("repeatstmt", [c, s])

    def parse_read_stmt(self):     
        """ Parse tokens matching following production:
                <read_stmt>  -> READ ID
        """
        self.__scanner.log("Parsing <read_stmt>  -> READ ID")
        self.__scanner.match("READ")
        varname = self.__scanner.current.value
        self.__scanner.match("ID")
        return PTNode("readstmt", [PTNode("leaf", [], value = varname)]) 
        

    def parse_writestmt(self):     
        """ Parse tokens matching following production:
                <writestmt>  -> WRITE <exp>
        """
        self.__scanner.log("Parsing <writestmt>  -> WRITE <exp>")
        self.__scanner.match("WRITE")
        e = self.parse_exp()
        return PTNode("writestmt", [e]) 

    def parse_assignstmt(self):     
        """ Parse tokens matching following production:
                <assignstmt> ->  ID ASSIGN <exp>
        """
        self.__scanner.log("Parsing <assignstmt> ->  ID ASSIGN <exp>")
        identifier = self.__scanner.match("ID")
        self.__scanner.match("ASSIGN")
        e = self.parse_exp()
        return PTNode("assignstmt", [PTNode("leaf", [], value = identifier), e])

    def parse_comp_op(self):     
        """ Parse tokens matching following production:
                <comp-op> -> LTE | LT | GT | GTE| EQ | NOTEQ
        """
        self.__scanner.log(
            "Parsing <comp-op> -> LTE | LT | GT | GTE| EQ | NOTEQ")
        return PTNode("comp-op", [PTNode("leaf", [], value = self.__scanner.current.value)])

    def parse_exp(self):     
        """ Parse tokens matching following production:
                <exp> -> <simple-expr> <comp-op> <simple-expr>
      |   <simple-expr>
        """
        self.__scanner.log("Parsing <exp> -> <simple-expr> <comp-op> <simple-expr> "
            "|   <simple-expr>")
        
        t = self.parse_simple_expr()
        children = [t]
        while self.__scanner.current.kind in {"LTE", "LT", "GT", "GTE", "EQ", "NOTEQ"}:
            t = self.parse_comp_op()
            children.append(t)
            self.__scanner.advance()
            z = self.parse_simple_expr()
            children.append(z)
        return PTNode("exp", children)        

    def parse_addop(self):     
        """ Parse tokens matching following production:
                <addop> ->   PLUS | MINUS
        """
        self.__scanner.log("Parsing <addop> ->   PLUS | MINUS")
        r = PTNode("addop",  [PTNode("leaf", [], value = self.__scanner.current.value)])
        
        if self.__scanner.current.kind in {"PLUS", "MINUS"}:
            self.__scanner.advance()
        return r

    def parse_term(self):     
        """ Parse tokens matching following production:
                <term> -> <term> <mulop> <factor> | <factor>
        """
        self.__scanner.log("Parsing <term> -> <term> <mulop> <factor> | <factor>")
    
        f = self.parse_factor()
        children = [f]
        while self.__scanner.current.kind in {"TIMES", "OVER"}:
            m = self.parse_mulop()
            children.append(m)
            f = self.parse_factor()
            children.append(f)
        return PTNode("term", children) 

    def parse_mulop(self):     
        """ Parse tokens matching following production:
                <mulop> -> TIMES| OVER
        """
        self.__scanner.log("Parsing <mulop> -> TIMES | OVER")
        r = PTNode("mulop",  [PTNode("leaf", [], value = self.__scanner.current.value)])
        if self.__scanner.current.kind in {"TIMES", "OVER"}:
            self.__scanner.advance()
        return r

    def parse_factor(self):     
        """ Parse tokens matching following production:
                <factor> -> LPAREN <exp> RPAREN | INT | ID
        """
        self.__scanner.log(
            "Parsing <factor> -> LPAREN <exp> RPAREN | INT | ID")
    
        if self.__scanner.current.kind == "LPAREN":
            self.__scanner.match("LPAREN")
            c = self.parse_exp()
            self.__scanner.match("RPAREN")
            return PTNode("factor", [c])
        elif self.__scanner.current.kind in {"ID", "INT"}:
            val = self.__scanner.current.value
            self.__scanner.advance()
            return PTNode("factor", [PTNode("leaf", [], value =  val)])
        else:
            self.__scanner.shriek("I'm lost . . .")
            
if __name__ == "__main__":

    fpath = "tny_files/ifelse.tny"
  
    parser = TinyParser(fpath)
    ptroot = parser.parse_program()

    print("Parse tree:")
    print("-" * 25)
    ptroot.dump()
    print("=" * 25)
    print()
    
   



   
   
             
