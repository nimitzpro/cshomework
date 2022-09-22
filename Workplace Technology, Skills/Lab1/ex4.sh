#!/bin/sh

exec awk 'BEGIN {FS=":"; ORS="\n\n"; } {l=split($4,x," "); sum=0; for(i=0;i<l;i++) sum += x[i]} {sum=int(sum/l)}{print $1"\n"$2" "$3"\n"$4"\n"sum;}' $1
