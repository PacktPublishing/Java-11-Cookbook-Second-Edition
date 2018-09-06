@echo off
"%JAVA8_HOME%"\bin\javac -version
rmdir /q /s classes
mkdir classes
"%JAVA8_HOME%"\bin\javac -cp lib\*;classes -d classes src\com\packt\*.java src\com\packt\model\*.java

if %errorlevel% == 1 goto failedCompilation

:runCode
jar cvfm sample.jar manifest.mf -C classes .
"%JAVA8_HOME%"\bin\java -jar sample.jar
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'