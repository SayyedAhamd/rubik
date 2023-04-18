set PATH_TO_FX="path\to\javafx-sdk-20\lib"
cd ..
set PATH_TO_BIN=%cd%\bin
javac --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml,javafx.swing -d %PATH_TO_BIN% src\hellofx\*.java

java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml,javafx.swing -cp %PATH_TO_BIN% hellofx.Main
