package com.packt.cookbook.ch04_functional.b;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Chapter04Functional {

    public static void main(String... args) {
        //demo1_Food();
        demo2_GrandApi();
    }

    public static void demo1_Food() {

        //Static unbound method reference
        Supplier<String> supp = () -> Food.getFavorite();
        Supplier<String> supplier = Food::getFavorite;
        System.out.println("supplier.get() => " + supplier.get());

        Function<Integer, String> fnc = i-> Food.getFavorite(i);
        Function<Integer, String> func = Food::getFavorite;
        System.out.println("func.getFavorite(1) => " + func.apply(1));
        System.out.println("func.getFavorite(2) => " + func.apply(2));


        //Non-static bound method reference
        Food food1 = new Food();
        Food food2 = new Food("Carrot");
        Food food3 = new Food("Carrot and Broccoli");
        Supplier<String> supp1 = () -> food1.sayFavorite();
        Supplier<String> supplier1 = food1::sayFavorite;
        Supplier<String> supplier2 = food2::sayFavorite;
        Supplier<String> supplier3 = food3::sayFavorite;
        System.out.println("new Food().sayFavorite() => " + supplier1.get());
        System.out.println("new Food(Carrot).sayFavorite() => " + supplier2.get());
        System.out.println("new Food(Carrot,Broccoli).sayFavorite() => " + supplier3.get());

        UnaryOperator<String> op = s -> food1.sayFavorite(s);
        UnaryOperator<String> op1 = food1::sayFavorite;
        UnaryOperator<String> op2 = food2::sayFavorite;
        UnaryOperator<String> op3 = food3::sayFavorite;
        System.out.println("new Food().sayFavorite(Carrot) => " + op1.apply("Carrot"));
        System.out.println("new Food(Carrot).sayFavorite(Broccoli) => " + op2.apply("Broccoli"));
        System.out.println("new Food(Carrot,Broccoli).sayFavorite(Donuts) => " + op3.apply("Donuts"));


        //Non-static unbound method reference
        Function<Food, String> fnc1 = f -> f.sayFavorite();
        Function<Food, String> func1 = Food::sayFavorite;
        System.out.println("new Food().sayFavorite() => " + func1.apply(food1));
        System.out.println("new Food(Carrot).sayFavorite() => " + func1.apply(food2));
        System.out.println("new Food(Carrot,Broccoli).sayFavorite() => " + func1.apply(food3));

        BiFunction<Food, String, String> fnc2 = (f,s) -> f.sayFavorite(s);
        BiFunction<Food, String, String> func2 = Food::sayFavorite;
        System.out.println("new Food().sayFavorite(Carrot) => " + func2.apply(food1, "Carrot"));
        System.out.println("new Food(Carrot).sayFavorite(Broccoli) => " + func2.apply(food2, "Broccoli"));
        System.out.println("new Food(Carrot,Broccoli).sayFavorite(Donuts) => " + func2.apply(food3, "Donuts"));


        //Constructor method reference
        Supplier<Food> foodSuppl = () -> new Food();
        Supplier<Food> foodSupplier = Food::new;
        Supplier<String> suppl = foodSupplier.get()::sayFavorite;
        System.out.println("new Food().sayFavorite() => " + suppl.get());

        Function<String, Food> foodFunction = Food::new;
        UnaryOperator<String> f = s -> foodFunction.apply(s).sayFavorite();
        System.out.println("new Food(Donuts).sayFavorite() => " + f.apply("Donuts"));
        System.out.println("new Food(Carrot).sayFavorite() => " + f.apply("Carrot"));

        BiFunction<String, String, Food> foodFunction2 = Food::new;
        BinaryOperator<String> f2 = (s1,s2) -> foodFunction2.apply(s1,s2).sayFavorite();
        System.out.println("new Food(Donuts,Carrot).sayFavorite() => " + f2.apply("Donuts", "Carrot"));
        System.out.println("new Food(Donuts,Carrot).sayFavorite() => " + f2.apply("Carrot", "Broccoli"));

        System.out.println();
        //Other examples
        Function<String,  Integer> strLength = String::length;
        System.out.println(strLength.apply("3"));  //prints: 1

        Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println(parseInt.apply("3")); //prints: 3

        Consumer<String> consumer = System.out::println;
        consumer.accept("Hello!");                //prints: Hello!

        Function<Integer, String[]> createArray = String[]::new;
        String[] arr  = createArray.apply(3);
        System.out.println("Array length=" + arr.length); //prints: Array length=3

        int i = 0;
        for(String s: arr){
            arr[i++] = String.valueOf(i);
        }

        Function<String[], List<String>> toList = Arrays::<String>asList;
        List<String> l = toList.apply(arr);
        System.out.println("List size=" + l.size());
        for(String s: l){
            System.out.println(s);
        }
        System.out.println();
    }

    private static class Food{
        private String name;
        public Food(){ this.name = "Donut"; }
        public Food(String name){ this.name = name; }
        public Food(String name, String anotherName) {
            this.name = name + " and " + anotherName;
        }
        public static String getFavorite(){ return "Donut!"; }
        public static String getFavorite(int num){
            return num > 1 ? String.valueOf(num) + " donuts!" : "Donut!";
        }
        public String sayFavorite(){
            return this.name + (this.name.toLowerCase().contains("donut")?"? Yes!" : "? D'oh!");
        }
        public String sayFavorite(String name){
            this.name = this.name + " and " + name;
            return sayFavorite();
        }
   }

    public static void demo2_GrandApi() {
        GrandApi api = new GrandApiImpl();

        Calculator calc = new CalcImpl(20, 10d);
        double res = api.doSomething(calc, "abc", 2);
        System.out.println(res);     //prints: 403

        res = api.doSomething(new Calculator() {
            public double calculateSomething() {
                return 20 * 10d;
            }
        }, "abc", 2);
        System.out.println(res);     //prints: 403.0

        res = api.doSomething(() -> 20 * 10d, "abc", 2);
        System.out.println(res);     //prints: 403.0

        AnyClass anyClass = new AnyClass();
        res = api.doSomething(anyClass::doIt, "abc", 2);
        System.out.println(res);

        res = api.doSomething(() -> 20 * 10d, "abc", 2);
        System.out.println(res);     //prints: 403.0

        Supplier<Double> supp = () -> 20 * 10d;
        res = api.doSomething1(supp, "abc", 2);
        System.out.println(res);
        res = api.doSomething1(() -> 20 * 10d, "abc", 2);
        System.out.println(res);
        res = api.doSomething1(anyClass::doIt, "abc", 2);
        System.out.println(res);

        res = api.doSomething2(x -> x * 10, 2d);
        System.out.println(res);

        //api.doSomething2(System.out::println, 2d);
        api.doSomething2((Consumer<String>)System.out::println, 2d);
        Consumer<String> consumer = System.out::println;
        api.doSomething2(consumer, 2d);

        int[] arr = new int[1];
        arr[0] = 1;
        res = api.doSomething(() -> 20 * 10d + arr[0]++, "abc", 2);
        System.out.println(res);
        res = api.doSomething(() -> 20 * 10d + arr[0]++, "abc", 2);
        System.out.println(res);
        res = api.doSomething(() -> 20 * 10d + arr[0]++, "abc", 2);
        System.out.println(res);

        res = api.doSomething2((Integer i) -> { return i * 10; }, 2d);
        res = api.doSomething2((i) -> i * 10, 2d);
    }

    public static class GrandApiImpl implements GrandApi{
        public double doSomething(Calculator calc, String str, int i){
            return calc.calculateSomething() * i + str.length();
        }
        public double doSomething1(Supplier<Double> supp, String str, int i){
            return supp.get() * i + str.length();
        }
        public double doSomething2(Function<Integer, Integer> function, double num) {
            return num + function.apply(1);
        }
        public void doSomething2(Consumer<String> consumer, double num){
            consumer.accept(String.valueOf(num));
        }
    }
    public interface GrandApi{
        double doSomething(Calculator calc, String str, int i);
        double doSomething1(Supplier<Double> supp, String str, int i);
        double doSomething2(Function<Integer, Integer> function, double num);
        void doSomething2(Consumer<String> consumer, double num);
    }
    public static class AnyClass {
        public double doIt(){
            return 1d;
        }
        public double doSomethingElse(){
            return 100d;
        }
    }

    public static class CalcImpl implements Calculator{
        private int par1;
        private double par2;
        public CalcImpl(int par1, double par2){
            this.par1 = par1;
            this.par2 = par2;
        }
        public double calculateSomething(){
            return par1 * par2;
        }
        public int calcSomethingElse(){ return 1; }
    }
    public interface Calculator {
        double calculateSomething();
        //int calcSomethingElse();
    }

    public interface MySupplier<Double> extends Supplier<Double>{
        void m();
    }
}


