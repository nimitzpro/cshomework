from tiny_to_tac_compiler import *
from tac_engine import *


def init(source):
    fpath, ftype = source[:-4:], source[-3:len(source):1]
    if ftype == "pkl":
        tc = TinyCompiler(source, False, False)
        tc.translate()
    else:
        tc = TinyCompiler(source, True, False)
        tc.translate()

    tr = TacReader(f"{fpath}.tac")
    p = tr.program
    p.show()
    p.execute()

init("trees/factorial_pt_kh.pkl")

init("tny_files/ifelse.tny")