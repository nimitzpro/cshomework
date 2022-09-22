#!/bin/sh

echo $(find . -type f -exec ls -s {} + | sort -nr | head -1 | awk '{print $2}')