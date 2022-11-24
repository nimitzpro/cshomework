import pickle


p2file = open("trees/factorial_pt_kh.pkl", "rb")
write17_tree2 = pickle.load(p2file)

# and you verify it using the lark pretty-printing function:
write17_tree2.dump()