javac -p mods -d mods --module-source-path src $(find . -name "*.java")
java --module-path mods -m demo/com.packt.demo.OpenModuleDemo