@echo off
javac -d mods --module-source-path src src\jshell\com\packt\*.java src\jshell\module-info.java

if %errorlevel% == 1 goto failedCompilation

:runCode
java -p mods -m jshell/com.packt.JshellJavaApiDemo
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'
