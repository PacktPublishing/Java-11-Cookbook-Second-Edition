javac -d banking_util/out/classes/ -sourcepath banking_util/src $(find banking_util/src -name *.java)
jar --create --file=banking_util/out/banking.util.jar -C banking_util/out/classes/ .
