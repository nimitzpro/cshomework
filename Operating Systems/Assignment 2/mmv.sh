#!/bin/bash

files=$(find -L -xtype l -samefile $1) # Find all appropriate link files

mv $1 $2 # Move file to destinaation

abs_path=$(readlink -f $2) # Get absolute path of destination
# echo "$abs_path"

for file in $files # Loop through all link files, setting correct relative paths for each
do
	fpath=$(echo $file | sed 's|\(.*\)/.*|\1|')
	path=$(realpath --relative-to=$fpath $abs_path)
#	echo "$file : $path"
	ln -sfn $path $file
done

exit 0
