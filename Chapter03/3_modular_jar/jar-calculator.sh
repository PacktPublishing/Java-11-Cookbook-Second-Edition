mkdir -p mlib
jar --create --file=mlib/calculator@1.0.jar --module-version 1.0 --main-class com.packt.calculator.Calculator -C mods/calculator .
