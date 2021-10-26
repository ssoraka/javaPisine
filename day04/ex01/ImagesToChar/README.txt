#!/bin/sh
# java -cp target edu.school21.logic.app.Program [WHILE COLOR] [ANOTHER COLOR] [PATH TO IMAGE.BMP]

rm -rf target

mkdir target
javac -sourcepath src/java `find . -name "*.java"` -d target
cp -R src/resources target
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target/ .

java -jar target/images-to-chars-printer.jar . 0
