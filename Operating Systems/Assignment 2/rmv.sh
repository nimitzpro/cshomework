#!/bin/bash

# echo "Removing $1 and all its links..."

stripped=$(echo $1 | sed 's|.*/||') # Strip the filename of any directories


# If there is already a file with the same filename in the backup directory, then stop the script
if test $(find ~/blinking_dev_directory/removed_files_4links.bak -name "$stripped" | wc -c) -gt 0
then
	echo "A file with the same name is already in the backup directory"
	exit 0
fi

# Otherwise, continue removing the file

files=$(find -L . -samefile $1) # Find all link files that link to said file

mv $1 ~/blinking_dev_directory/removed_files_4links.bak # Move the file to the backup directory


# Loop through all link files, setting their links using relative paths generated using realpath, sed and the stripped filename
for file in $files
do
	fpath=$(echo $file | sed 's|\(.*\)/.*|\1|')
	path=$(realpath --relative-to=$fpath ~/blinking_dev_directory/removed_files_4links.bak)
	ln -sfn "$path/$stripped" $file
done

exit 0

