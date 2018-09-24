@echo off
javac -p G:/openjfx11/javafx-sdk-11/lib -d mods --module-source-path src src\gui\com\packt\*.java src\gui\module-info.java

if %errorlevel% == 1 goto failedCompilation

:runCode
java -p "G:/openjfx11/javafx-sdk-11/lib;mods" -m gui/com.packt.EmbedAudioVideoDemo
goto end

:failedCompilation
echo 'Compilation failed'

:end
echo 'Bye!!'
