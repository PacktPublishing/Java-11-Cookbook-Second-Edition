javac -p /g/openjfx11/javafx-sdk-11/lib -d mods --module-source-path src $(find src -name *.java)
cp src/gui/com/packt/store mods/gui/com/packt/
java -p "/g/openjfx11/javafx-sdk-11/lib:mods" -m gui/com.packt.BubbleChartDemo
