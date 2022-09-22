#!/bin/sh

# Check for existence of parameter
if [ -z "$1" ]; then
  echo "Error, please enter number of task (+location of classes for 1/2)"

# No. 1 - Describes classes
else if [ "$1" == "1" ]; then
  echo "Main creates a demo Car, while Tester runs tests on Car. Location : " + $2

# No. 2 - Runs unit tests
else if [ "$1" == "2" ]; then
  exec "cd classes | java -cp .:lib/ -d classes/Tester"

# No. 3 - Compile Main and Tester
else if [ "$1" == "3" ]; then
  exec "ant compile-main"
  exec "ant compile-tests"

# No. 4 - Run the main method from the class implemented in Task 2
else if [ "$1" == "4" ]; then
  exec "java -cp classes Main"

fi

exit 0