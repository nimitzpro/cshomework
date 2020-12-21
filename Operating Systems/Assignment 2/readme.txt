Alexander Stradnic - 119377263

mmv : 

First I found all the link files to the file in question.
Then I moved the file to the destination.
I then got the absolute path of the destination so that I could generate relative links to the file for the link files, 
which is done inside the for loop using sed and realpath.
Finally I exited with code 0.

rmv : 

I first stripped the directory off of the file to be removed.
Then I checked if there was any file with the same name already in the backup directory.

If there was, then it would stop the program at that point and print an error message.

Otherwise it would continue in a similar manner to mmv.
It would find all the link files for the file in question and generate relative links for each, using the stripped filename, 
since the file is simply put into the backup directory with no name changes or in any other folders.


I would change the rmv to indicate where the removed file was removed from, either by creating extra directories in the backup directory, 
or by appending to the filename.

Example : removing both blinker/remove_this and blinkered/remove_this

Creating extra directories :

backup_directory:

  -> blinker:
    -> remove_this

  -> blinkered:
    -> remove_this



Appending to filename : 

backup_directory:
  -> remove_this_BLINKER
  -> remove_this_BLINKERED
