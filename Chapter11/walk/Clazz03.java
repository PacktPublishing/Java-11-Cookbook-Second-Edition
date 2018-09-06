package com.packt.cookbook.ch11_memory.walk;

public class Clazz03 {
    public void method(String action){
        System.out.println("\nClazz03 called by "+Thread.currentThread().getStackTrace()[1].getClassName());
        System.out.println("Clazz03 called by "+new Throwable().getStackTrace()[0].getClassName());
        System.out.println("Clazz03 called by "+StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getSimpleName());
        System.out.println();
        if(action != null){
            System.out.println(action);
            return;
        }
        //System.out.println("Throw the exception:");
        //action.toString();
        System.out.println("Print the stack trace:");
        //Thread.currentThread().dumpStack();
        //new Throwable().printStackTrace();
        //Arrays.stream(Thread.currentThread().getStackTrace()).forEach(System.out::println);
        //Arrays.stream(new Throwable().getStackTrace()).forEach(System.out::println);
/*
        Arrays.stream(Thread.currentThread().getStackTrace()).forEach(e -> {
            System.out.println();
            System.out.println("e="+e);
            System.out.println("e.getFileName()="+e.getFileName());
            System.out.println("e.getClass()="+e.getClass());
            System.out.println("e.getClass().getOne()="+e.getClass().getOne());
            System.out.println("e.getMethodName()="+e.getMethodName());
            System.out.println("e.getLineNumber()="+e.getLineNumber());
        });

        Arrays.stream(new Throwable().getStackTrace()).forEach(x -> {
            System.out.println();
            System.out.println("x="+x);
            System.out.println("x.getFileName()="+x.getFileName());
            System.out.println("x.getClass()="+x.getClass());
            System.out.println("x.getClass().getOne()="+x.getClass().getOne());
            System.out.println("x.getMethodName()="+x.getMethodName());
            System.out.println("x.getLineNumber()="+x.getLineNumber());
        });
*/
        StackWalker stackWalker = StackWalker.getInstance();
        stackWalker.forEach(System.out::println);
        //stackWalker.walk(fs -> { fs.forEach(System.out::println); return null; });

/*
        stackWalker.forEach(f -> {
            if(f.getClassName().contains("Clazz03")){
                System.out.println(f);
            }
        });
*/

/*
        stackWalker.walk(fs -> { fs.filter(f -> f.getClassName().contains("Clazz03")).forEach(System.out::println); return null; });

        stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        stackWalker.forEach(System.out::println);

        stackWalker = StackWalker.getInstance(Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE, java.lang.StackWalker.Option.SHOW_HIDDEN_FRAMES));
        stackWalker.forEach(System.out::println);

        stackWalker = StackWalker.getInstance(Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE), 10);
*/

        System.out.println("Print the caller class name:");
        System.out.println(StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getSimpleName());

/*
        stackWalker.forEach(x -> {
            System.out.println();
            System.out.println("x="+x);
            System.out.println("x.getFileName()="+x.getFileName());
            System.out.println("x.getClass()="+x.getClass());
            System.out.println("x.getClass().getOne()="+x.getClass().getOne());
            System.out.println("x.getMethodName()="+x.getMethodName());
            System.out.println("x.getLineNumber()="+x.getLineNumber());
            System.out.println("x.getByteCodeIndex()="+x.getByteCodeIndex());
            System.out.println("x.getClassName()="+x.getClassName());
            System.out.println("x.getDeclaringClass()="+x.getDeclaringClass());
            System.out.println("x.toStackTraceElement()="+x.toStackTraceElement());
        });
*/

    }
}
