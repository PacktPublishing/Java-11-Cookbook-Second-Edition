package com.packt.cookbook.ch11_memory.walk;

public class Clazz01 {

    public void method(){
        System.out.println("\nClazz01 called by "+StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getSimpleName());
        new Clazz03().method("Do something");
        new Clazz02().method();
    }
}
