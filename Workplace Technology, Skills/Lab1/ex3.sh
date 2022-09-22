#!/bin/sh

if [ -z "$1" ]; then
	echo $(find . -type f -print -o -name . -o -prune | wc -l)
else
	for var in "$@"
	do
		echo $(find $var -type f -print -o -name $var -o -prune | wc -l)
	done
fi