"$JAVA8_HOME"/bin/javac -cp 'lib/*' -d classes -sourcepath src $(find src -name *.java)
jar cvfm sample.jar manifest.mf -C classes .
"$JAVA8_HOME"/bin/java -jar sample.jar