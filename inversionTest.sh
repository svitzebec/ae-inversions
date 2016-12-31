#!/bin/bash

javac InversionsTest.java

tinySeq=$(seq 10 5 150)
smallSeq=$(seq 10000 5000 50000)
largeSeq=$(seq 100000 100000 1000000)

seqType="inc dec rnd"
algSlow="bf is1 is2"
algFast="ms1 ms2 ms3"
algAll="$algSlow $algFast"
algAllAndHybrid="$algAll hybrid"

for sequence in $seqType 
do
	for algorithm in $algAllAndHybrid
	do
		for n in $tinySeq 
		do
			java InversionsTest $algorithm $n $sequence 10000
		done
	done
	for algorithm in $algAll 
	do
		for n in $smallSeq
		do
			java InversionsTest $algorithm $n $sequence 10
		done
	done
	for algorithm in $algFast 
	do
		for n in $largeSeq
		do
			java InversionsTest $algorithm $(printf "%.0f" $n) $sequence 10
		done
	#done
done



rm InversionsTest.java
find . -name \*.class -delete