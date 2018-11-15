this program runs on java 1.8

in order to not create issues with the current version of java installed on ther computer this program will 
be executed on, it is recommended to:

1) name the executable jar file created from this project "EMMInstaller.jar"

2) put "EMMInstaller.jar" in a folder with a copy of the java jdk 1.8 called "java1.8"

3) created a file called "EMMInstaller.bat" with the contents:

@echo off
cd "%cd%\java1.8\bin"
java -jar ..\..\EMMInstaller.jar

4) zip this folder and send to desired computer, and click "EMMInstaller.bat" to run


** running "EMMInstaller.jar" by clicking on it is possible if the JAVA_HOME variable is associated with java jdk 1.8+ **