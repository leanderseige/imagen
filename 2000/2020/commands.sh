#!/bin/bash

echo "please cut & paste these commands manually"
cat $0 | grep screen
exit

killall screen

# run the generator
screen -d -m ./crec

# run data extraction
screen -d -m bash -c "while (true); do ./dimagen.sh > html/output.gif; sleep .5; done"


