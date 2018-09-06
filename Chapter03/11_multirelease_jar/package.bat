javac -d mods  --release 8 src\8\com\packt\*.java
javac -d mods9  --release 9 src\9\com\packt\*.java

jar --create --file mr.jar --main-class=com.packt.FactoryDemo -C mods . --release 9 -C mods9 .