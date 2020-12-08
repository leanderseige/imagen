#!/bin/sh
cd ../../

./dimagen > ./test.out
rm ./test.out

cd ./html/cgi-bin/

echo -e "Content-Type: image/gif\n"
cat ../genima.gif 
