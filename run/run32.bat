cd ..
set PATH_TO_BIN=%cd%\bin
javac -d %PATH_TO_BIN% src\hellofx\*.java

java -cp %PATH_TO_BIN% hellofx.Main
