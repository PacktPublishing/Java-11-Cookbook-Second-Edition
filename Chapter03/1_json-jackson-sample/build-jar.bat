"%JAVA8_HOME%"\bin\javac -cp lib\*;classes -d classes src\com\packt\*.java src\com\packt\model\*.java
jar cvfm sample.jar manifest.mf -C classes .
