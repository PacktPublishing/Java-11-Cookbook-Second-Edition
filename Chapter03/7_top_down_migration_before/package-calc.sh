javac -cp calculator/lib/*:math_util/out/math.util.jar:banking_util/out/banking.util.jar -d calculator/out/classes/ -sourcepath calculator/src $(find calculator/src -name *.java)
jar --create --file=calculator/out/calculator.jar -C calculator/out/classes/ .
