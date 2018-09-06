javac -d math_util/out/classes/ math_util/src/com/packt/math/*.java
jar --create --file=math_util/out/math.util.jar -C math_util/out/classes/ .
