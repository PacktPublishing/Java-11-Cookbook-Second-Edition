javac -p /g/openjfx11/javafx-sdk-11/lib -d mods --module-source-path src $(find src -name *.java)
java -p "mods:/g/openjfx11/javafx-sdk-11/lib" -m gui/com.packt.CreateGuiDemo