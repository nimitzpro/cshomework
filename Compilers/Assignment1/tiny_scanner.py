#
# Simple lexical scanner for Tiny programming language
# Bare-bones implementation with no error checking.
#
# Alexander Stradnic 119377263
#
#
# Notes:
# 1) Python-style # comments only (no /* */)
# 2) variable names-- letters only
# 3) unsigned integer constants only

import re
import sys
import traceback

# Define RE to caputure Tiny comments.
COMMENT_RE = re.compile(r"{.*}")

# Define RE to capture Tiny tokens.
TOKENS_RE = re.compile(r"[a-z]+"
                       r"|[0-9]+"
                       r"|[(){}+*/;\-]"
                       r"|==|!=|<=|<|>=|>|=|:="
    )

# Define Tiny's reserved words and their labels. 'EOS' (end of source
# symbol) is treated as an honorary reserved word.
RESERVED_WORDS = {
    "read" : "READ", "write" : "WRITE", "repeat" : "REPEAT", "until" : "UNTIL",
    "if" : "IF", "then" : "THEN", "else" : "ELSE", "end" : "END", "EOS" : "EOS"
    }

# Define Tiny's repertiore of symbols and their labels.
SYMBOLS = {
    ";" : "SEMI", ":=" : "ASSIGN",
    "<=" : "LTE", "<" : "LT",  ">" : "GT",  
    ">=" : "GTE", "=" : "EQ",  "!=" : "NOTEQ",
    "+" : "PLUS",  "-" : "MINUS", "*" : "TIMES", 
    "/" : "OVER", "STATEMENT" : "STATEMENT"
    }

LOGPAD = " " * 10

class TinyToken:
    """ Implement class to represent Tiny token objects.
    """
    def __init__(self, tkn):
        """ Create a token object for 'tkn'.
        Public members:
           string: string representation of this token
           kind: label for this token
           value: numerical value (integers only)
        """
        self.string = tkn
        self.value = tkn
        if tkn.isalpha():
            self.spelling = tkn
            if tkn in RESERVED_WORDS:
                self.kind = RESERVED_WORDS[tkn]
            else:
                self.kind = "ID"
                #self.value = tkn
        elif tkn.isdigit():
            self.kind = "INT"
            self.value = int(tkn)
        elif tkn in SYMBOLS:
            self.kind = SYMBOLS[tkn]
            # print(self.kind)
        else:
            self.shriek("Illegal symbol '%s'." % tkn)
    
    def __str__(self):
        """ Return string representation of this token. """
        return ("[Token: '%s' (%s)]" 
               % (self.string, self.kind) )

class TinyScanner:
    """ Implement class to perform lexical analysis on Tiny source.
    """
   
    def __init__(self, fpath, verbose = False):
        """ Create scanner object wrapped around file at path 'fpath'.
        """
        try:
            self.__source = open(fpath, "r").read()
        except Exception:
            traceback.print_exc()
            sys.exit(-1)
            
        # Set verbose/silent output.
        self.verbose = verbose
            
        # Eliminate comments.
        self.__source = COMMENT_RE.sub("", self.__source)
        
        # Set up token sequence.
        self.__tokens = TOKENS_RE.findall(self.__source)
        
        self.__tokens.append("EOS")
         
        # Tee up the first token.
        self.current = None
        self.advance()
    
    def __next_token(self):
        """ Return the next token (or None).
        """
        if self.current != "EOS":
            tkn =  self.__tokens.pop(0)
            return TinyToken(tkn)
        else:
            return None
        
    def shriek(self, msg):
        """ Print error message 'msg' and terminate execution.
        """
        self.log("*** TinyScanner: %s" % msg, pad = False)
        sys.exit(-1)
     
    def log_nopad(self, msg):
        """ Print 'msg'. """
        if self.verbose:
            print(msg)
        
    def log(self,  msg, pad = True):
        """ Print 'msg'.  Indent if 'pad' is set. """
        if self.verbose:
            print("%s%s" % (LOGPAD if pad else "", msg)) 
        
    def has_more(self):
        """ Return True if some tokens yet unread.
        """
        return len(self.__tokens) > 0
  
    def advance(self):
        """ Advance one token forward.
        """
        if self.has_more:
            self.current = self.__next_token()
            self.log_nopad("['%s']" % self.current.string)
       
    def match(self, expected):
        """ If current token matches 'expected', then advance one token 
        forward, otherwise issue error and terminate.
        """
        val = self.current.value
        if self.current.kind != expected:
           self.shriek("Expected '%s', saw '%s'." 
                % (expected, self.current.string))
  
        self.advance()
        return val
  


def main():
    t = TinyScanner("tny_files/onetoten.tny", verbose=True)
    while t.has_more():
        t.advance()

if __name__=="__main__":
    main()