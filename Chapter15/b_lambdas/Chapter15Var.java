package com.packt.cookbook.ch15_new_way.b_lambdas;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Chapter15Var {
    public static void main(String... args) {

        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("John", 40000);
        salaries.put("Freddy", 30000);
        salaries.put("Samuel", 50000);

        salaries.replaceAll((name, oldValue) ->
                name.equals("Freddy") ? oldValue : oldValue + 10000);

        System.out.println(salaries);

        BiFunction<Double, Integer, Double> f1 = (Double x, Integer y) -> x/y;
        BiFunction<Double, Integer, Double> f2 = (x, y) -> x/y;
        System.out.println(f2.apply(3., 2));

        BiFunction<Double, Integer, Double> f3 = (var x, var y) -> x/y;
        BiFunction<Double, Integer, Double> f4 = (@NotNull var x, @NotNull var y) -> x / y;

        Double j = 3.;
        Integer i = 2;
        System.out.println(f4.apply(j, i));
    }
}