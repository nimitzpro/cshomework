import sys

def factLoop(i):
    if i == 1:
        return i
    return i*factLoop(i-1)

def fact(n):
    sys.setrecursionlimit(n*10)
    return factLoop(n)

print(fact(10))