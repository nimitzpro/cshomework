#!/bin/sh

find . -type f | while read FILE;
do
	if grep -q "Galway\|Lee\|Dublin $FILE; then
		echo $FILE
	fi
done
