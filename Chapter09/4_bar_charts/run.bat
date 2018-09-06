@echo off
javac -p mlib\student.processor.jar -d mods --module-source-path src src\gui\com\packt\*.java src\gui\module-info.java

if %errorlevel% == 1 goto failedCompilation

:runCode
java -p mods;mlib\student.processor.jar -m gui/com.packt.BarChartDemo
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'
