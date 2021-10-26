#!/bin/sh
# java -cp target edu.school21.logic.app.Program [WHILE COLOR] [ANOTHER COLOR] [PATH TO IMAGE.BMP]

rm -rf target

mkdir target
javac -sourcepath src/java `find . -name "*.java"` -d target
java -cp target edu.school21.logic.app.Program . 0 /Users/ssoraka/Desktop/piscine/day04/ex00/ImagesToChar/it.bmp
