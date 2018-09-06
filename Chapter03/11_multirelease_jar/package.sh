javac -d mods  --release 8 $(find src/8 -name "*.java")
javac -d mods9  --release 9 $(find src/9 -name "*.java")

jar --create --file mr.jar --main-class=com.packt.FactoryDemo -C mods . --release 9 -C mods9 .