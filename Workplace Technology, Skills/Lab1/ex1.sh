#!/bin/sh

a=(`find . -perm -100 | wc -l`)
b=(`find . -perm -200 | wc -l`)
c=(`find . -perm -400 | wc -l`)

echo $c $b $a