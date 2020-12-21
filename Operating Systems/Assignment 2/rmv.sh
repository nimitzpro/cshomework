#!/bin/bash

echo "Removing $1 and all its links..."

stripped=$(echo $1 | sed 's|.*/||')


if test $(find ~/blinking_dev_directory/removed_files_4links.bak -name "$stripped" | wc -c) -gt 0
then
	echo "A file with the same name is already in the backup folder"
	exit 0
fi


files=$(find -L . -samefile $1)

mv $1 ~/blinking_dev_directory/removed_files_4links.bak


for file in $files
do
	fpath=$(echo $file | sed 's|\(.*\)/.*|\1|')
	path=$(realpath --relative-to=$fpath ~/blinking_dev_directory/removed_files_4links.bak)
	ln -sfn "$path/$stripped" $file
done

exit 0

