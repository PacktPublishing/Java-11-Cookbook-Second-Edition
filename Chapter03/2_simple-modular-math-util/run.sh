javac -d mods --module-source-path . $(find . -name "*.java")
java --module-path mods -m calculator/com.packt.calculator.Calculator