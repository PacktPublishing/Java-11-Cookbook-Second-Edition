@echo off
javac -d mods --module-source-path src src\student.processor\com\packt\processor\*.java src\student.processor\module-info.java

if %errorlevel% == 1 goto failedCompilation

:runCode
copy src\student.processor\com\packt\processor\students mods\student.processor\com\packt\processor
mkdir mlib
jar --create --file=mlib/student.processor.jar -C mods/student.processor .
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'
