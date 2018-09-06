javac -d mods -p mods --module-source-path src src\demo\*.java src\demo\com\packt\demo\*.java
java --module-path mods -m demo/com.packt.demo.OpenModuleDemo