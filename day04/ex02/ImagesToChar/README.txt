#!/bin/sh
#1. Make target directory:
rm -rf target
rm -rf lib
mkdir target
mkdir lib

touch lib/jcommander-1.78.jar
curl -o lib/jcommander-1.78.jar https://repo1.maven.org/maven2/com/beust/jcommander/1.78/jcommander-1.78.jar
touch lib/JCDP-2.0.3.1.jar
curl -o lib/JCDP-2.0.3.1.jar https://repo1.maven.org/maven2/com/diogonunes/JCDP/2.0.3.1/JCDP-2.0.3.1.jar

#2. Compilation from folder ImageToChar:
javac -d target -sourcepath src/java -cp lib/JCDP-2.0.3.1.jar:lib/jcommander-1.78.jar:. src/java/edu/school21/printer/*/*.java

#3. Copy resources file into target folder:
cp -a src/resources target

#4. Copy class-file from jar
jar -xf lib/jcommander-1.78.jar
jar -xf lib/JCDP-2.0.3.1.jar
rm -rf META-INF
mv com target

#4. Arhivation:
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target/ .

#5 Run program:
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN