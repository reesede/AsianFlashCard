#!/bin/sh

#
# Execute this file to launch AsianFlashCard on MacOS or Linux
#

# Find where AsianFlashCard is installed, dereferencing symlinks
INSTALL_DIR=$(cd "$(dirname "$0")"; pwd)

# Launch VASSSAL
java -Dfile.encoding=UTF-8 -jar $INSTALL_DIR/AsianFlashCard.jar
