from random import randint
from datetime import datetime

times = []
timesTotal = []

def matrices(N):
    start = datetime.now()

    for n in range(N+1):
        s = datetime.now()
        matrix(n)
        f = datetime.now()
        _ = "matrix(" + str(n) + "): " + str(f-s) + ", total time: " + str(f-start)
        times.append(f-s)
        timesTotal.append(f-start)
        print(_)
    

def matrix(n):
    
    A = [[randint(0, 99) for a2 in range(n)] for a1 in range(n)]
    B = [[randint(0, 99) for b2 in range(n)] for b1 in range(n)]

    # print(A, B, sep="\n")

    C = [[0 for c2 in range(n)] for c1 in range(n)]

    for aY in range(n):
        for bX in range(n):
            sum = 0
            for aX in range(n): # aX -> bY as both matrices are square and the same size
                sum += A[aY][aX] * B[aX][bX]

            C[aY][bX] = sum

    # print(C)


matrices(150)

times = [t.total_seconds() for t in times]
timesTotal = [t.total_seconds() for t in timesTotal]
print(times)
print(timesTotal)