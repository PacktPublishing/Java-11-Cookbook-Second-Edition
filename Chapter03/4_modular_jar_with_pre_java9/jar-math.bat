javac -d mods --module-source-path . math.util\*.java math.util\com\packt\math\*.java
mkdir mlib
jar --create --file=mlib/math.util@1.0.jar --module-version 1.0 -C mods/math.util .