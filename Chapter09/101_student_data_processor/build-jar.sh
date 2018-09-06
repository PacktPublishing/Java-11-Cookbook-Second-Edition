javac -d mods --module-source-path src $(find src -name *.java)
cp src/student.processor/com/packt/processor/students mods/student.processor/com/packt/processor
mkdir -p mlib
jar --create --file=mlib/student.processor.jar -C mods/student.processor .
