#!/bin/sh

gcc -c list.c

gcc -c main.c
gcc -o main main.o

./main

# Todo : add parameter checks

exit 0;