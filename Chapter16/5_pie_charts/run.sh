javac -p "/g/openjfx11/javafx-sdk-11/lib:mlib/student.processor.jar" -d mods --module-source-path src $(find src -name *.java)
java -p "/g/openjfx11/javafx-sdk-11/lib:mods:mlib/student.processor.jar" -m gui/com.packt.PieChartDemo
