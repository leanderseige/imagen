#!/bin/sh

grep $REMOTE_ADDR ../temp.hst

if test $? -eq 0
then
	echo -e "Content-Type: image/jpeg\n"
	cat ../temp.jpg
else
	echo -e "Content-Type: image/jpeg\n"
	cat ../wrong.jpg
fi