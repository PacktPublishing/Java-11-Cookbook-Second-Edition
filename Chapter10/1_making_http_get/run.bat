@echo off
javac -d mods --module-source-path src src\http.client.demo\com\packt\*.java src\http.client.demo\module-info.java

if %errorlevel% == 1 goto failedCompilation

:runCode
java -p mods -m http.client.demo/com.packt.HttpGetDemo
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'
