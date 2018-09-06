javac -d mods --module-source-path src $(find src -name *.java)
java -p mods -m jshell/com.packt.JshellJavaApiDemo