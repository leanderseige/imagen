#!/bin/sh

	cd ../../

if test -z $5
then

	if test -z $1; then let i=16 ; else let i=$1; fi 
	if test -z $2; then let j=1  ; else let j=$2; fi 

	if test -z $3   ; then let n=1; else let n=$3; fi
	if test $n -eq 3; then let n=3; else let n=1 ; fi

	if test                   -z $4; then let cc=1; else let cc=$4; fi
	if test $cc -lt 2 -o $cc -gt 5 ; then let cc=1; fi
	if test              $cc -eq 4 ; then let cc=1; fi

	if test $i -gt 256; then let i=256; fi
	if test $i -lt 2  ; then let i=2  ; fi
	if test $j -gt 8  ; then let j=8  ; fi
	if test $j -lt 1  ; then let j=1  ; fi

	let m=$i*$i
	let k=$i*$i*$j 
	if test $k -gt 65536; then let j=65536/$m; fi     

	let k=$i*$i*$j
	if test $n -eq 3 -a $k -gt 16384; then let i=$i/2; fi

	let ci=$i*2
	# echo -e "\nS: $i x $i Pixel bei $j x $n {1=Mono|3=Color} Bit Farbtiefe "

	cp ./data/seed ./data/seed.s 
	./stool

	if test $n -eq 1; then ./conv $i $j $cc $n; else ./conv $ci $j $cc $n; fi

	echo -e "Content-Type: image/gif\n"

	if test $n -eq 1 ; then /usr/X11R6/bin/rawtopgm $i $i ./data/genima.raw | /usr/X11R6/bin/pgmtoppm rgb:FF/FF/FF | /usr/X11R6/bin/ppmtogif ;
	else                    /usr/X11R6/bin/rawtoppm $i $i ./data/genima.raw | /usr/X11R6/bin/ppmtogif ; fi

else
	if test $5 -eq 1
	then
		cp ./data/seed ./data/seed.s 
		./stool

		if test -z $2; then let j=1  ; else let j=$2; fi 
		if test $j -gt 8  ; then let j=8  ; fi
		if test $j -lt 1  ; then let j=1  ; fi	

		./conv 90 $j 1 1

		cd ./data
		echo -e "Content-Type: audio/basic\n"
	#	echo -e "Content-Length: 6776\n"

		sox -t .raw -r8000 -s -b -c1 genima.raw -t au -U -
	#	cat ./genima.au
	#	echo -e "\004"
 	
	else
		if test $5 -gt 10 -a $5 -lt 14
		then
			cp ./data/seed ./data/seed.s 
			./stool
			echo -e "Content-Type: text/html\n"
			./idump $5
		fi
	fi
fi
