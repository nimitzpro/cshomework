import os
import sys

A=list("ABCDEF")
B="FFAAXXEDXXABEDDAAACDAECFABECCCEC"
b=list(B)
possible_keys = []
for i in range(len(A)):
    b[4]=A[i];
    for j in range(len(A)):
        b[5] =A[j];
        for k in range(len(A)):
            b[8] =A[k];
            for n in range(len(A)):
                b[9] =A[n];
                m=''.join('%s' %id for id in b)
                output = os.system('openssl aes-128-ecb -in 6.aes -out 6.txt -K ' + m + ' -d')
                if (output==0):
                    possible_keys.append(m)

print(possible_keys)
for i in range(len(possible_keys)):
    os.system("openssl aes-128-ecb -in 6.aes -out test" + str(i) + ".txt -K " + possible_keys[i] + " -d")
