import matplotlib.pyplot as plt
import math
import random
import sys

def calc_collision(k):
    if k == 1:
        return 1
    else:
        return ((2**32 - (k-1)) / (2**32)) * calc_collision(k-1)

def calc_collisions(k):
    l = [1]
    for i in range(2, k+1):
        l.append( ((2**32 - (i-1)) / (2**32)) * l[-1] )

    return l
    
def collision_chance(N):
    data = [k for k in range(1, N+1)], calc_collisions(N)
    # print(data)
    plt.plot(data[0], data[1])
    # plt.yticks(range(0, 1))
    # plt.xticks(range(1, N+1))
    plt.xlabel('k')
    plt.ylabel('Probability')
    plt.show()

collision_chance(1000000)