javac -d mods -p mods --module-source-path src src\banking.demo\*.java src\banking.demo\com\packt\demo\*.java
java --module-path mods -m banking.demo/com.packt.demo.BankingDemo