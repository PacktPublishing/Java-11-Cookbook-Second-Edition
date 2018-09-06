package com.packt.cookbook.ch04_functional.a;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Chapter04Functional {

    public static void main(String... args) {
/*
        standardFunctionalInterfaces();
        useStandardFunctionalInterfaces1();
*/
        useStandardFunctionalInterfaces2();
/*
        identity();
        useCustomFunctionalInterface1();
        compose1();
        moreChaining();
        thisInLambdas();
        useStandardFunctionalInterfaces3();
        compose2();
        lambdas();
*/
    }

    private static void standardFunctionalInterfaces() {

        Function<Integer, Double> ourFunc = new Function<Integer, Double>(){
            public Double apply(Integer i){
                return i * 10.0;
            }
        };
        System.out.println(ourFunc.apply(1)); //prints: 10.0


        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                System.out.println("The " + s + " is consumed.");
            }
        };
        consumer.accept("Hello!");   //prints: The Hello! is consumed.

        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                String res = "Success";
                //Do something and return result – Success or Error.
                return res;
            }
        };
        System.out.println(supplier.get());  //prints: Success

        Predicate<Double> pred = new Predicate<Double>() {
            public boolean test(Double num) {
                System.out.println("Test if " + num + " is smaller than 20");
                return num < 20;
            }
        };
        System.out.println(pred.test(10.0) ? "10 is smaller": "10 is bigger");
                                      //prints: Test if 10.0 is smaller than 20
                                      //        10 is smaller

        IntFunction<String> ifunc = new IntFunction<String>() {
            public String apply(int i) {
                return String.valueOf(i * 10);
            }
        };
        System.out.println(ifunc.apply(1));   //prints: 10

        BiFunction<String, Integer, Double> bifunc =
                new BiFunction<String, Integer, Double >() {
                    public Double apply(String s, Integer i) {
                        return (s.length() * 10d) / i;
                    }
                };

        System.out.println(bifunc.apply("abc",2));         //prints: 15.0
        System.out.println(bifunc.andThen(x -> x + 10).apply("abc",2));  //prints: 25.0

        BinaryOperator<Integer> binfunc = new BinaryOperator<Integer>(){
            public Integer apply(Integer i, Integer j) {
                return i >= j ? i : j;
            }
        };
        System.out.println(binfunc.apply(1,2));  //prints: 2

        IntBinaryOperator intBiFunc = new IntBinaryOperator(){
            public int applyAsInt(int i, int j) {
                return i >= j ? i : j;
            }
        };
        System.out.println(intBiFunc.applyAsInt(1,2));  //prints: 2
    }

    private static void calculate(Supplier<Integer> source, Function<Integer,
                                  Double> process, Predicate<Double> condition,
                                  Consumer<Double> success, Consumer<Double> failure){
        int i = source.get();
        double res = process.apply(i);
        if(condition.test(res)){
            success.accept(res);
        } else {
            failure.accept(res);
        }
    }

    private static void useStandardFunctionalInterfaces1() {
        Supplier<Integer> source = new Supplier<Integer>() {
            public Integer get() {
                Integer res = 42;
                //Do something and return result value
                return res;
            }
        };
        Function<Integer, Double> process = new Function<Integer, Double>(){
            public Double apply(Integer i){
                return i * 10.0;
            }
        };
        Predicate<Double> condition = new Predicate<Double>() {
            public boolean test(Double num) {
                System.out.println("Test if " + num + " is smaller than " + 20);
                return num < 20;
            }
        };
        Consumer<Double> success = new Consumer<Double>() {
            public void accept(Double d) {
                System.out.println("Success: " + d);
            }
        };
        Consumer<Double> failure = new Consumer<Double>() {
            public void accept(Double d) {
                System.out.println("Failure: " + d);
            }
        };
        calculate(source, process, condition, success, failure);
        testSourceAndCondition(10, 20);
        testSourceAndCondition(1, 20);
        testSourceAndCondition(10, 200);
    }

    private static void testSourceAndCondition(int src, double limit) {
        Supplier<Integer> source = new Supplier<Integer>() {
            public Integer get() {
                Integer res = src;
                //Do something and return result value
                return res;
            }
        };
        Function<Integer, Double> process = new Function<Integer, Double>(){
            public Double apply(Integer i){
                return i * 10.0;
            }
        };
        Predicate<Double> cond = new Predicate<Double>() {
            public boolean test(Double num) {
                System.out.println("Test if " + num + " is smaller than " + limit);
                return num < limit;
            }
        };
        Consumer<Double> success = new Consumer<Double>() {
            public void accept(Double d) {
                System.out.println("Success: " + d);
            }
        };
        Consumer<Double> failure = new Consumer<Double>() {
            public void accept(Double d) {
                System.out.println("Failure: " + d);
            }
        };
        calculate(source, process, cond, success, failure);
    }

    private static void useStandardFunctionalInterfaces2() {
        Supplier<Integer> source = new Supplier<Integer>() {
            public Integer get() {
                Integer res = 42;
                //Do something and return result value
                return res;
            }
        };
        Function<Integer, Double> before = new Function<Integer, Double>(){
            public Double apply(Integer i){
                return i * 10.0;
            }
        };
        Function<Double, Double> after = new Function<Double, Double>(){
            public Double apply(Double d){
                return d + 10.0;
            }
        };
        //Function<Integer, Double> process = before.andThen(after);
        Function<Integer, Double> process = after.compose(before);

        Predicate<Double> condition = new Predicate<Double>() {
            public boolean test(Double num) {
                System.out.println("Test if " + num + " is smaller than " + 20);
                return num < 20;
            }
        };
        Consumer<Double> success = new Consumer<Double>() {
            public void accept(Double d) {
                System.out.println("Success: " + d);
            }
        };
        Consumer<Double> failure = new Consumer<Double>() {
            public void accept(Double d) {
                System.out.println("Failure: " + d);
            }
        };
        calculate(source, process, condition, success, failure);
    }

    private static void identity(){
        Function<Integer, Integer> id = Function.identity();
        System.out.println(id.apply(4));  //prints: 4

    }

    @FunctionalInterface
    interface A{
        void m1();
    }

    @FunctionalInterface
    interface B extends A{
        default void m2(){};
    }

    //@FunctionalInterface
    interface C extends B{
        void m3();
    }

    private interface SpeedModel {
        double getSpeedMph(double timeSec, int weightPounds, int horsePower);
 //       void m1();
    }

    private static class SpeedModelImpl implements SpeedModel{
        public double getSpeedMph(double timeSec, int weightPounds, int horsePower){
            double v = 2.0 * horsePower * 746 * timeSec * 32.17 / weightPounds;
            return (double) Math.round(Math.sqrt(v) * 0.68);
        }
    }
    private interface Vehicle {
        void setSpeedModel(SpeedModel speedModel);
        double getSpeedMph(double timeSec);
    }

    private static class VehicleImpl implements Vehicle {
        private SpeedModel speedModel;
        private int weightPounds, hoursePower;
        public VehicleImpl(int weightPounds, int hoursePower){
            this.weightPounds = weightPounds;
            this.hoursePower = hoursePower;
        }

        public void setSpeedModel(SpeedModel speedModel){
            this.speedModel = speedModel;
        }

        public double getSpeedMph(double timeSec){
            return this.speedModel.getSpeedMph(timeSec, this.weightPounds, this.hoursePower);
        };
    }

    private static void useCustomFunctionalInterface1() {
        Vehicle vehicle = new VehicleImpl(3000, 200);
        SpeedModel speedModel = new SpeedModelImpl();
        vehicle.setSpeedModel(speedModel);
        System.out.println(vehicle.getSpeedMph(10.)); //prints: 122.0

        SpeedModel speedModel1 =  (t, wp, hp) -> {
            double v = 2.0 * hp * 746 * t * 32.17 / wp;
            return (double) Math.round(Math.sqrt(v) * 0.68);
        };

        speedModel = new SpeedModel(){
            public double getSpeedMph(double timeSec, int weightPounds, int horsePower){
                double v = 2.0 * horsePower * 746 * timeSec * 32.17 / weightPounds;
                return (double) Math.round(Math.sqrt(v) * 0.68);
            }
            public void m1(){}
            public void m2(){}
        };

        vehicle.setSpeedModel(speedModel);
        System.out.println(vehicle.getSpeedMph(10.)); //prints: 122.0
    }

    private static void useCustomFunctionalInterface2() {
        Vehicle vehicle = new VehicleImpl(3000, 200);
        SpeedModel speedModel = new SpeedModelImpl();
        vehicle.setSpeedModel(speedModel);
        System.out.println(vehicle.getSpeedMph(10.)); //prints: 122.0

        SpeedModel speedModel1 =  (t, wp, hp) -> {
            double v = 2.0 * hp * 746 * t * 32.17 / wp;
            return (double) Math.round(Math.sqrt(v) * 0.68);
        };

        speedModel = new SpeedModel(){
            public double getSpeedMph(double timeSec, int weightPounds, int horsePower){
                double v = 2.0 * horsePower * 746 * timeSec * 32.17 / weightPounds;
                return (double) Math.round(Math.sqrt(v) * 0.68);
            }
            public void m1(){}
            public void m2(){}
        };

        vehicle.setSpeedModel(speedModel);
        System.out.println(vehicle.getSpeedMph(10.)); //prints: 122.0

        Func<Double, Integer, Integer, Double> speedModel2 = (t, wp, hp) -> {
            double v = 2.0 * hp * 746 * t * 32.17 / wp;
            return (double) Math.round(Math.sqrt(v) * 0.68);
        };
    }

    @FunctionalInterface
    interface Func<T1,T2,T3,R>{ R apply(T1 t1, T2 t2, T3 t3);}

    private interface Vehicle1 {
        void setSpeedModel(Func<Double, Integer, Integer, Double> speedModel);
        double getSpeedMph(double timeSec);
    }
    private class VehicleImpl1  implements Vehicle1 {
        private Func<Double, Integer, Integer, Double> speedModel;
        private int weightPounds, hoursePower;
        public VehicleImpl1(int weightPounds, int hoursePower){
            this.weightPounds = weightPounds;
            this.hoursePower = hoursePower;
        }
        public void setSpeedModel(Func<Double, Integer, Integer, Double> speedModel){
            this.speedModel = speedModel;
        }
        public double getSpeedMph(double timeSec){
            return this.speedModel.apply(timeSec,
                    this.weightPounds, this.hoursePower);
        };
    }

    private static class AClass{
        public AClass(){}
        public AClass(int i, double d, String s){ }
        public String get(int i, double d){ return ""; }
        public String get(int i, double d, String s){ return ""; }
    }

    private static void useCustomFunctionalInterface3() {
        Func<Integer, Double, String, AClass> func1 = (i,d,s) -> new AClass();

        AClass obj1 = func1.apply(1, 2d, "abc");
        Func<Integer, Double, String, String> func2 = (i,d,s) -> obj1.get(i,d,s);
        String res1 = func2.apply(42, 42., "42");

        Func<AClass, Integer, Double, String> func3 = (a,i,d) -> a.get(i,d);
        String res2 = func3.apply(obj1, 42, 42.);


        //Method reference
        Func<Integer, Double, String, AClass> func11 = AClass::new;

        AClass obj2 = func11.apply(1, 2d, "abc");
        Func<Integer, Double, String, String> func21 = obj2::get;
        String res11 = func2.apply(42, 42., "42");

        Func<AClass, Integer, Double, String> func31 = AClass::get;
        String res21 = func31.apply(obj2, 42, 42.);
    }

    private static Function<Integer, Double> createMultiplyBy(double num){
        Function<Integer, Double> ourFunc = new Function<Integer, Double>(){
            public Double apply(Integer i){
                return i * num;
            }
        };
        return ourFunc;
    }

    private static Function<Double, Double> createSubtract(double num){
        Function<Double, Double> ourFunc = new Function<Double, Double>(){
            public Double apply(Double dbl){
                return dbl - num;
            }
        };
        return ourFunc;
    }

    private static Predicate<Double> createIsSmallerThan(double limit){
        Predicate<Double> pred = new Predicate<Double>() {
            public boolean test(Double num) {
                System.out.println("Test if " + num + " is smaller than " + limit);
                return num < limit;
            }
        };
        return pred;
    }

    private static void compose1() {

        Function<Integer, Double> multiplyBy30 = createMultiplyBy(30d);
        System.out.println(multiplyBy30.apply(1)); //prints: 30

        Function<Double,Double> subtract7 = createSubtract(7.0);
        System.out.println(subtract7.apply(10.0));  //prints: 3.0

        Function<Integer, Double> multiplyBy30AndSubtract7 = subtract7.compose(multiplyBy30);
        System.out.println(multiplyBy30AndSubtract7.apply(1)); //prints: 23.0

        multiplyBy30AndSubtract7 = multiplyBy30.andThen(subtract7);
        System.out.println(multiplyBy30AndSubtract7.apply(1)); //prints: 23.0

        Function<Integer, Double> multiplyBy30Subtract7Twice = subtract7.compose(multiplyBy30).andThen(subtract7);
        System.out.println(multiplyBy30Subtract7Twice.apply(1));  //prints: 16

    }

    private static void moreChaining() {
        Function<Integer, Double> multiplyBy10 = createMultiplyBy(10d);
        System.out.println(multiplyBy10.apply(1));

        Function<Integer, Double> multiplyBy30 = createMultiplyBy(30d);
        System.out.println(multiplyBy30.apply(1));

        Function<Double,Double> subtract7 = createSubtract(7.0);
        System.out.println(subtract7.apply(10.0));

        Consumer<String> sayHappyToSee = createTalker("Happy to see you again!");
        sayHappyToSee.accept("Hello!");

        Supplier<String> successOrFailure = createResultSupplier();
        System.out.println(successOrFailure.get());

        Predicate<Double> isSmallerThan20 = createIsSmallerThan(20d);
        System.out.println(isSmallerThan20.test(10d));

        Predicate<Double> isBiggerThan18 = createIsBiggerThan(18d);
        System.out.println(isBiggerThan18.test(10d));

        //We can pass the created functions as parameters:
        Supplier<String> compare1By10And20 = applyCompareAndSay(1, multiplyBy10, isSmallerThan20);
        System.out.println(compare1By10And20.get());

        Supplier<String> compare1By30And20 = applyCompareAndSay(1, multiplyBy30, isSmallerThan20);
        System.out.println(compare1By30And20.get());

        //And we can chain functions using the default methods of some Functional Interfaces:

        Supplier<String> compare1By30Less7To20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7), isSmallerThan20);
        System.out.println(compare1By30Less7To20.get());

        Supplier<String> compare1By30Less7TwiceTo20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7).andThen(subtract7), isSmallerThan20);
        System.out.println(compare1By30Less7TwiceTo20.get());

        Supplier<String> compare1By30Less7To20_ = applyCompareAndSay(1, subtract7.compose(multiplyBy30), isSmallerThan20);
        System.out.println(compare1By30Less7To20_.get());

        Supplier<String> compare1By30Less7TwiceTo20_ = applyCompareAndSay(1, subtract7.compose(multiplyBy30).andThen(subtract7), isSmallerThan20);
        System.out.println(compare1By30Less7TwiceTo20_.get());

        Consumer<String> askHowAreYou = createTalker("How are you?");
        sayHappyToSee.andThen(askHowAreYou).accept("Hello!");

        Supplier<String> compare1By30Less7TwiceTo18And20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7).andThen(subtract7),
                isSmallerThan20.and(isBiggerThan18), "between 18 and 20");
        System.out.println(compare1By30Less7TwiceTo18And20.get());

    }

    private static Consumer<String> createTalker(String value){
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                System.out.println(s + value);
            }
        };
        return consumer;
    }

    private static Supplier<String> createResultSupplier(){
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                String res = "Success";
                //Do something and return result – Success or Error.
                return res;
            }
        };
        return supplier;
    }

    private static Predicate<Double> createIsBiggerThan(double limit){
        Predicate<Double> pred = new Predicate<Double>() {
            public boolean test(Double num) {
                System.out.println("Test if " + num + " is bigger than " + limit);
                return num > limit;
            }
        };
        return pred;
    }
    private static Supplier<String> applyCompareAndSay(int i, Function<Integer, Double> func, Predicate<Double> isSmaller){
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                double v = func.apply(i);
                return isSmaller.test(v)? v + " is smaller" : v + " is bigger";
            }
        };
        return supplier;
    }

    private static Supplier<String> applyCompareAndSay(int i, Function<Integer, Double> func, Predicate<Double> compare, String message){
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                double v = func.apply(i);
                return (compare.test(v)? v + " is " : v + " is not ") + message;
            }
        };
        return supplier;
    }

    private static Supplier<String> applyCompareAndSayLambda(int i, Function<Integer, Double> func, Predicate<Double> isSmaller){
        return () -> {
            double v = func.apply(i);
            return isSmaller.test(v)? v + " is smaller" : v + " is bigger";
        };
    }

    private static Supplier<String> applyCompareAndSayLambda(int i, Function<Integer, Double> func, Predicate<Double> compare, String message){
        return () -> {
            double v = func.apply(i);
            return (compare.test(v)? v + " is " : v + " is not ") + message;
        };
    }

    private static void thisInLambdas() {
        Demo d = new Demo();
        d.method();
    }

    private static class Demo{
        private String prop = "DemoProperty";
        public void method(){
            Consumer<String> consumer = s -> {
                System.out.println("Lambda accept(" + s + "): this.expensiveInitClass=" + this.prop);
            };
            consumer.accept(this.prop);

            consumer = new Consumer<>() {
                private String prop = "ConsumerProperty";
                public void accept(String s) {
                    System.out.println("Anonymous accept(" + s + "): this.expensiveInitClass=" + this.prop);
                }
            };
            consumer.accept(this.prop);
        }
    }

    private static void useStandardFunctionalInterfaces3() {
        Supplier<Integer> source = () -> 4;
        Function<Integer, Double> before = i -> i * 10.0;
        Function<Double, Double> after = d -> d + 10.0;
        Function<Integer, Double> process = before.andThen(after);
        Predicate<Double> condition = num -> num < 100;
        Consumer<Double> success = d -> System.out.println("Success: " + d);
        Consumer<Double> failure = d-> System.out.println("Failure: " + d);
        calculate(source, process, condition, success, failure);
    }

    public static Supplier<Integer> source(){ return () -> 4;}
    public static Function<Double, Double> after(){ return d -> d + 10.0; }
    public static Function<Integer, Double> before(){return i -> i * 10.0; }
    public static Function<Integer, Double> process(){ return before().andThen(after()); }
    public static Predicate<Double> condition(){ return num -> num < 100.; }
    public static Consumer<Double> success(){ return d -> System.out.println("Success: " + d); }
    public static Consumer<Double> failure(){ return d-> System.out.println("Failure: " + d); }

    private static void useStandardFunctionalInterfaces4() {
        calculate(source(), process(), condition(), success(), failure());
    }
    private static void compose2() {
        //Function<Integer, Double> multiplyBy30 = i -> i * 30d;
        Function<Integer, Double> multiplyBy30 = createMultiplyBy(30d);
        System.out.println(multiplyBy30.apply(1)); //prints: 30

        //Function<Double,Double> subtract7 = d-> d - 7.;
        Function<Double,Double> subtract7 = createSubtract(7.0);
        System.out.println(subtract7.apply(10.0));  //prints: 3.0

        Function<Integer, Double> multiplyBy30AndSubtract7 = subtract7.compose(multiplyBy30);
        System.out.println(multiplyBy30AndSubtract7.apply(1)); //prints: 23.0

        multiplyBy30AndSubtract7 = multiplyBy30.andThen(subtract7);
        System.out.println(multiplyBy30AndSubtract7.apply(1)); //prints: 23.0

        Function<Integer, Double> multiplyBy30Subtract7Twice = subtract7.compose(multiplyBy30).andThen(subtract7);
        System.out.println(multiplyBy30Subtract7Twice.apply(1));  //prints: 16

    }

    private static void lambdas() {

        //Introducing Lambdas:
        Function<Integer, Double> ourFunc = i -> i * 10.0;
        System.out.println(ourFunc.apply(1));

        Consumer<String> consumer = s -> System.out.println("The " + s + " is consumed.");
        consumer.accept("Hello!");

        Supplier<String> supplier = () -> {
            String res = "Success";
            //Do something and return result – Success or Error.
            return res;
        };
        System.out.println(supplier.get());

        Predicate<Double> pred = num -> {
            System.out.println("Test if " + num + " is smaller than 20");
            return num < 20;
        };
        System.out.println(pred.test(10.0) ? "10 is smaller": "10 is bigger");

        Function<Integer, Double> multiplyBy10 = i -> i * 10.0;
        System.out.println("1 * 10.0 => " + multiplyBy10.apply(1));

        Function<Integer, Double> multiplyBy30 = i -> i * 30.0;
        System.out.println("1 * 30.0 => " + multiplyBy30.apply(1));

        Function<Double,Double> subtract7 = x -> x - 7.0;
        System.out.println("10.0 - 7.0 => " + subtract7.apply(10.0));

        Consumer<String> sayHappyToSee = s -> System.out.println(s + " Happy to see you again!");
        sayHappyToSee.accept("Hello!");

        Predicate<Double> isSmallerThan20 = x -> x < 20d;
        System.out.println("10.0 is smaller than 20.0 => " + isSmallerThan20.test(10d));

        Predicate<Double> isBiggerThan18 = x -> x > 18d;
        System.out.println("10.0 is smaller than 18.0 => " + isBiggerThan18.test(10d));

        BiFunction<Integer, String, Double> demo = (x,y) -> x * 10d + Double.parseDouble(y);
        System.out.println(demo.apply(1, "100"));

        demo = (x,y) -> {
            double v = x * 10d;
            return v + Double.parseDouble(y);
        };
        System.out.println(demo.apply(1, "100"));

        //We can pass the created functions as parameters:

        Supplier<String> compare1By10And20 = applyCompareAndSay(1, multiplyBy10, isSmallerThan20);
        System.out.println("Compare (1 * 10) and 20 => " + compare1By10And20.get());

        Supplier<String> compare1By30And20 = applyCompareAndSay(1, multiplyBy30, isSmallerThan20);
        System.out.println("Compare (1 * 30) and 20 => " + compare1By30And20.get());

        Supplier<String> compare1By30Less7To20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7), isSmallerThan20);
        System.out.println("Compare (1 * 30 - 7) and 20 => " + compare1By30Less7To20.get());

        Supplier<String> compare1By30Less7TwiceTo20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7).andThen(subtract7), isSmallerThan20);
        System.out.println("Compare (1 * 30 - 7 - 7) and 20 => " + compare1By30Less7TwiceTo20.get());

        Supplier<String> compare1By30Less7TwiceTo18And20 = applyCompareAndSay(1, multiplyBy30.andThen(subtract7).andThen(subtract7),
                isSmallerThan20.and(isBiggerThan18), "between 18 and 20");
        System.out.println("Compare (1 * 30 - 7 - 7) and the range 18 to 20 => " + compare1By30Less7TwiceTo18And20.get());


        //Or we can create functions on the fly:


        Supplier<String> compare1By10And20Lambda = applyCompareAndSay(1, x -> x * 10d, x -> x < 20);
        System.out.println("Compare (1 * 10) and 20 => " + compare1By10And20Lambda.get());

        Supplier<String> compare1By30And20Lambda = applyCompareAndSay(1, x -> x * 30d, x -> x < 20);
        System.out.println("Compare (1 * 30) and 20 => " + compare1By30And20Lambda.get());

        //And we can re-write factory methods using Lambda:
        Supplier<String> compare1By30Less7TwiceTo20Lambda = applyCompareAndSayLambda(1, x -> x*30.0-7.0-7.0, x -> x < 20) ;
        System.out.println("Compare (1 * 30 - 7 - 7) and 20 => " + compare1By30Less7TwiceTo20Lambda.get());

        Supplier<String> compare1By30Less7TwiceTo18And20Lambda = applyCompareAndSayLambda(1, x -> x*30.0-7.0-7.0, x -> x < 20 && x > 18, "betwen 18 and 20") ;
        System.out.println("Compare (1 * 30 - 7 - 7) and the range 18 to 20 => " + compare1By30Less7TwiceTo18And20Lambda.get());

    }

}
