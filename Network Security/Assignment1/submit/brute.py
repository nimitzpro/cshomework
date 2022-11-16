#!/usr/bin/env python

from Crypto.Cipher import AES
chars = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"]

import os
import sys

def test(testKey, contents_encrypted=""):
    #cipher = AES.new(testKey, AES.MODE_EAX)
    #nonce = cipher.nonce

#     plaintext = cipher.decrypt(contents_encrypted)
#     return str(plaintext)

    output = os.system("openssl aes-128-ecb -in 6.aes -out 6.txt -K " + testKey + " -d")
    if output == 0:
        return False

def inc(c, index):
    if index < len(c) - 1:
        if c[index] < 15 and c[index+1] == 15:
            return c[:index] + inc([c[index]+1] + c[index+1:], )
        else:
            return c[:index+1] + inc(c[index+1:], 0)
    else:
        return [c[index] + 1]

def crack(key, file):
    missing = [i[0] for i in enumerate(key) if i[1] == "X"]
    missingL = len(missing)
    combination = [0] * missingL
    final = [16] * missingL
    testKey = ""
    #f = open(file, 'rb')
    #contents_encrypted = f.read()
#    print(contents_encrypted)
    #f.close()
    print(missing)
    TEST = 0
    while combination != final:
        testKey = [char for char in key]
        for index, char in enumerate(combination):
            # print(char)
            char = hex(char)
            testKey[missing[index]] = str(char[2:])


        testKey = "".join(testKey)
        print(testKey)
#        print("tkey", testKey)
#         testKey = bytes(testKey, "utf-8")
        # testKey = bin(int(testKey, 16))
        t = test(testKey)
        if t:
            return "pog"
        #print(t+"\n\n")
        if combination != [15] * missingL:
            combination = inc(combination, 0)
        else:
            return

    # return complete

print(crack("FFAAXXEDXXABEDDAAACDAECFABECCCEC", "6.aes"))

y = [0,0,0,0]

def testing(z):
    p = 0
    while p < 300:
        print(z)
        z = inc(z, 0)
        p += 1

# testing(y)
