javac -d math_util/out/classes/ -sourcepath math_util/src $(find math_util/src -name *.java)
jar --create --file=math_util/out/math.util.jar -C math_util/out/classes/ .
