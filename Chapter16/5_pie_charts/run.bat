@echo off
javac -p "G:/openjfx11/javafx-sdk-11/lib;mlib\student.processor.jar" -d mods --module-source-path src src\gui\com\packt\*.java src\gui\module-info.java

if %errorlevel% == 1 goto failedCompilation

:runCode
java -p "G:/openjfx11/javafx-sdk-11/lib;mods;mlib\student.processor.jar" -m gui/com.packt.PieChartDemo
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'
