package com.packt.cookbook.ch11_memory.walk;

public class Clazz02 {

    public void method(){
        //System.out.println("\nClazz02 called by "+StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getSimpleName());
        new Clazz03().method(null);
    }
}
