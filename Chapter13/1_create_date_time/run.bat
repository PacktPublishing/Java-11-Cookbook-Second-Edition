@echo off
javac -d mods --module-source-path src src\datedemo\com\packt\*.java src\datedemo\module-info.java

if %errorlevel% == 1 goto failedCompilation

:runCode
java -p mods -m datedemo/com.packt.Main
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'
