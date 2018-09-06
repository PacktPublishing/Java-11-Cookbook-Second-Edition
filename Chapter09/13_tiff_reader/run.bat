@echo off
javac -d mods --module-source-path src src\gui\com\packt\*.java src\gui\module-info.java

if %errorlevel% == 1 goto failedCompilation

:runCode
java -p mods -m gui/com.packt.TiffReaderDemo
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'
