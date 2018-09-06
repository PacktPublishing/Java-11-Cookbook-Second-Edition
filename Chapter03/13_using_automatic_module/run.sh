javac -p mods -d mods --module-source-path src $(find . -name "*.java")
java --module-path mods -m banking.demo/com.packt.demo.BankingDemo