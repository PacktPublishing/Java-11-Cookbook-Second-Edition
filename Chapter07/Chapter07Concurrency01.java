package com.packt.cookbook.ch07_concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Chapter07Concurrency01 {
    public static void main(String... args) {

        demo1_thread();
/*
        demo2_runnable1();
        demo3_lambda1();
        demo3_lambda2();
        demo2_runnable2();
        demo4_synchronize1();
        demo4_synchronize2();
        demo4_synchronize3();
*/

    }

    private static void demo1_thread() {

        Thread thr1 = new AThread(1, 4);
        thr1.start();

        Thread thr2 = new AThread(11, 14);
        thr2.start();

        IntStream.range(21, 24).peek(Chapter07Concurrency01::doSomething).forEach(System.out::println);

    }

    private static int doSomething(int i) {
        IntStream.range(i, 100000).asDoubleStream().map(Math::sqrt).average();
        return i;
    }

    private static class AThread extends Thread {
        int i1, i2;

        AThread(int i1, int i2) {
            this.i1 = i1;
            this.i2 = i2;
        }

        public void run() {
            IntStream.range(i1, i2).peek(Chapter07Concurrency01::doSomething).forEach(System.out::println);
        }
    }

    private static void demo2_runnable1() {

        Thread thr1 = new Thread(new ARunnable(1, 4));
        thr1.start();

        Thread thr2 = new Thread(new ARunnable(11, 14));
        thr2.start();

        IntStream.range(21, 24).peek(Chapter07Concurrency01::doSomething).forEach(System.out::println);

    }

    private static class ARunnable implements Runnable {
        int i1, i2;

        ARunnable(int i1, int i2) {
            this.i1 = i1;
            this.i2 = i2;
        }

        public void run() {
            IntStream.range(i1, i2).peek(Chapter07Concurrency01::doSomething).forEach(System.out::println);
        }
    }

    private static class BRunnable implements Runnable {
        int i1, result;

        BRunnable(int i1) {
            this.i1 = i1;
        }

        public int getCurrentResult() {
            return this.result;
        }

        public void run() {
            for (int i = i1; i < i1 + 6; i++) {
                this.result = i;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    private static void demo2_runnable2() {
        System.out.println();

        BRunnable r1 = new BRunnable(1);
        Thread thr1 = new Thread(r1);
        thr1.start();

        IntStream.range(21, 29)
                .peek(i -> thr1.interrupt())
                .filter(i -> {
                    int res = r1.getCurrentResult();
                    System.out.print(res + " => ");
                    return res % 2 == 0;
                })
                .forEach(System.out::println);

    }

    private static void demo3_lambda1() {
        System.out.println();

        Thread thr1 = new Thread(() -> IntStream.range(1, 4).peek(Chapter07Concurrency01::doSomething).forEach(System.out::println));
        thr1.start();

        Thread thr2 = new Thread(() -> IntStream.range(11, 14).peek(Chapter07Concurrency01::doSomething).forEach(System.out::println));
        thr2.start();

        IntStream.range(21, 24).peek(Chapter07Concurrency01::doSomething).forEach(System.out::println);

    }

    private static void runImpl(int i1, int i2) {
        IntStream.range(i1, i2).peek(Chapter07Concurrency01::doSomething).forEach(System.out::println);
    }

    private static void demo3_lambda2() {

        Thread thr1 = new Thread(() -> runImpl(1, 4), "First Thread");
        thr1.start();

        Thread thr2 = new Thread(() -> runImpl(11, 14), "Second Thread");
        thr2.start();

        runImpl(21, 24);

    }

    private static class Calculator {
        private double prop;
        private Object calculateLock = new Object();

        public  double calculate(int i) {
            DoubleStream.generate(new Random()::nextDouble).limit(50);
            //synchronized (calculateLock) {
                this.prop = 2.0 * i;
                DoubleStream.generate(new Random()::nextDouble).limit(100);
                return Math.sqrt(this.prop);
            //}
        }
    }

    private static void demo4_synchronize1() {
        System.out.println();

        Calculator c = new Calculator();
        Runnable runnable = () -> System.out.println(IntStream.range(1, 40)
                .mapToDouble(c::calculate).sum());

        Thread thr1 = new Thread(runnable);
        thr1.start();

        Thread thr2 = new Thread(runnable);
        thr2.start();
    }

    private static void demo4_synchronize2() {
        System.out.println();

        Runnable runnable = () -> {
            Calculator c = new Calculator();
            System.out.println(IntStream.range(1, 40)
                    .mapToDouble(c::calculate).sum());
        };

        Thread thr1 = new Thread(runnable);
        thr1.start();

        Thread thr2 = new Thread(runnable);
        thr2.start();
    }

    private static void demo4_synchronize3() {
        System.out.println();
        List<String> l = Collections.synchronizedList(new ArrayList<>());
        l.add("first");
        //... code that adds more elements to the list
        int i = l.size();
        //... some other code
        l.add(i, "last");
        System.out.println(l);
    }

    private static class MutableClass {
        private int prop;

        public MutableClass(int prop) {
            this.prop = prop;
        }

        public int getProp() {
            return this.prop;
        }

        public void setProp(int prop) {
            this.prop = prop;
        }
    }

    private static final class ImmutableClass {
        private final double prop;
        private final MutableClass mutableClass;

        public ImmutableClass(double prop, MutableClass mc) {
            this.prop = prop;
            this.mutableClass = new MutableClass(mc.getProp());
        }

        public double getProp() {
            return this.prop;
        }

        public MutableClass getMutableClass() {
            return new MutableClass(mutableClass.getProp());
        }
    }

}


