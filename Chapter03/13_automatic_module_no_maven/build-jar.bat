javac -d classes src/com/packt/banking/*.java
jar cvfm banking-1.0.jar manifest.mf -C classes .
