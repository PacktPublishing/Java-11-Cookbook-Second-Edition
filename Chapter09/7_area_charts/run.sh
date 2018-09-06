javac -d mods --module-source-path src $(find src -name *.java)
cp src/gui/com/packt/crude-oil mods/gui/com/packt/
cp src/gui/com/packt/brent-oil mods/gui/com/packt/
java -p mods -m gui/com.packt.AreaChartDemo
