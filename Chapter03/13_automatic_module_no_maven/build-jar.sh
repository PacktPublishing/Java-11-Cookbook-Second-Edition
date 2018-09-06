javac -d classes -sourcepath src $(find src -name *.java)
jar cvfm banking-1.0.jar manifest.mf -C classes .
