#!/bin/bash

files=$(find -L -xtype l -samefile $1)

mv $1 $2

abs_path=$(readlink -f $2)
echo "$abs_path"

for file in $files
do
	fpath=$(echo $file | sed 's|\(.*\)/.*|\1|')
	path=$(realpath --relative-to=$fpath $abs_path)
	echo "$file : $path"
	ln -sfn $path $file
done

exit 0
