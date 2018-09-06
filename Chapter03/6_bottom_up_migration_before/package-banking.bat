javac -d banking_util/out/classes/ banking_util/src/com/packt/banking/*.java
jar --create --file=banking_util/out/banking.util.jar -C banking_util/out/classes/ .
