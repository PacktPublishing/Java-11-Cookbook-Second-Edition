javac -d mods --module-source-path src $(find src -name *.java)
java -p mods -m process/com.packt.process.RedirectFileDemo