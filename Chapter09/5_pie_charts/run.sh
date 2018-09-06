javac -p mlib/student.processor.jar -d mods --module-source-path src $(find src -name *.java)
java -p mods:mlib/student.processor.jar -m gui/com.packt.PieChartDemo
