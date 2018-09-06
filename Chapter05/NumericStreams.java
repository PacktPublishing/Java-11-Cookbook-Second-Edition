package com.packt.cookbook.ch05_streams;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class NumericStreams {
    public static void main(String... args) {
        range();
        arrays();
        mapToObjBoxed();
        asAnotherType();
        sumAndAverage();
        summary();
        customSummary();
    }

    private static void customSummary() {

        IntSummaryStatistics iss = Stream.of(3, 1)
                .collect(IntSummaryStatistics::new,
                        IntSummaryStatistics::accept,
                        IntSummaryStatistics::combine
                );
        System.out.println(iss);  //IntSummaryStatistics{count=2, sum=4, min=1, average=2.000000, max=3}

        //IntSummaryStatistics
        iss = Stream.of(3, 1)
                .collect(IntSummaryStatistics::new,
                        IntSummaryStatistics::accept,
                        (r, r1) -> {
                            System.out.println("Combining...");
                            r.combine(r1);
                        }
                );
        System.out.println(iss);  //IntSummaryStatistics{count=2, sum=4, min=1, average=2.000000, max=3}

        //IntSummaryStatistics
        iss = Stream.of(3, 1)
                .parallel()
                .collect(IntSummaryStatistics::new,
                        IntSummaryStatistics::accept,
                        (r, r1) -> {
                            System.out.println("Combining...");
                            r.combine(r1);
                        }
                );
        System.out.println(iss);  //IntSummaryStatistics{count=2, sum=4, min=1, average=2.000000, max=3}

        //IntSummaryStatistics
        iss = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.summarizingInt(Person::getAge));
        System.out.println(iss);  //IntSummaryStatistics{count=2, sum=50, min=20, average=25.000000, max=30}

    }

    private static void summary() {

        IntSummaryStatistics iss = IntStream.empty().summaryStatistics();
        System.out.println(iss);  //IntSummaryStatistics{count=0, sum=0, min=2147483647, average=0.000000, max=-2147483648}
        iss = IntStream.range(1, 3).summaryStatistics();
        System.out.println(iss);  //IntSummaryStatistics{count=2, sum=3, min=1, average=1.500000, max=2}

        LongSummaryStatistics lss = LongStream.empty().summaryStatistics();
        System.out.println(lss);  //LongSummaryStatistics{count=0, sum=0, min=9223372036854775807, average=0.000000, max=-9223372036854775808}
        lss = LongStream.range(1, 3).summaryStatistics();
        System.out.println(lss);  //LongSummaryStatistics{count=2, sum=3, min=1, average=1.500000, max=2}

        DoubleSummaryStatistics dss = DoubleStream.empty().summaryStatistics();
        System.out.println(dss);  //DoubleSummaryStatistics{count=0, sum=0.000000, min=Infinity, average=0.000000, max=-Infinity}
        dss = DoubleStream.of(1, 2).summaryStatistics();
        System.out.println(dss);  //DoubleSummaryStatistics{count=2, sum=3.000000, min=1.000000, average=1.500000, max=2.000000}

        System.out.println(Integer.MAX_VALUE);  //prints:  2147483647
        System.out.println(Integer.MIN_VALUE);  //prints: -2147483648
        System.out.println(Long.MAX_VALUE);  //prints:  9223372036854775807
        System.out.println(Long.MIN_VALUE);  //prints: -9223372036854775808
        System.out.println(Double.MAX_VALUE);  //prints: 1.7976931348623157E308
        System.out.println(Double.MIN_VALUE);  //prints: 4.9E-324
    }

    private static void sumAndAverage() {

        System.out.println();
        int sum = IntStream.empty().sum();
        System.out.println(sum);  //prints: 0
        sum = IntStream.range(1, 3).sum();
        System.out.println(sum);  //prints: 3
        double av = IntStream.empty().average().orElse(0);
        System.out.println(av);   //prints: 0.0
        av = IntStream.range(1, 3).average().orElse(0);
        System.out.println(av);   //prints: 1.5

        long suml = LongStream.range(1, 3).sum();
        System.out.println(suml);  //prints: 3
        double avl = LongStream.range(1, 3).average().orElse(0);
        System.out.println(avl);   //prints: 1.5

        double sumd = DoubleStream.of(1, 2).sum();
        System.out.println(sumd);  //prints: 3.0
        double avd = DoubleStream.of(1, 2).average().orElse(0);
        System.out.println(avd);   //prints: 1.5

    }

    private static void asAnotherType() {
        System.out.println();
        IntStream.range(1, 3).asLongStream().forEach(System.out::print);                       //prints: 12
        System.out.println();
        IntStream.range(1, 3).asDoubleStream().forEach(d -> System.out.print(d + " "));      //prints: 1.0 2.0
        System.out.println();
        LongStream.range(1, 3).asDoubleStream().forEach(d -> System.out.print(d + " "));      //prints: 1.0 2.0
        System.out.println();
    }

    private static void mapToObjBoxed() {

        System.out.println();
        IntStream.range(1, 3).mapToObj(Integer::valueOf).map(Integer::shortValue).forEach(System.out::print);  //prints: 12
        System.out.println();
        IntStream.range(1, 3).boxed().map(Integer::shortValue).forEach(System.out::print);  //prints: 12
        System.out.println();
        IntStream.of(42).mapToObj(i -> new Person(i, "John")).forEach(System.out::print);  //prints: Person1{name:John,age:42}
        System.out.println();
        LongStream.range(1, 3).mapToObj(Long::valueOf).map(Long::shortValue).forEach(System.out::print);  //prints: 12
        System.out.println();
        LongStream.range(1, 3).boxed().map(Long::shortValue).forEach(System.out::print);  //prints: 12
        System.out.println();
        DoubleStream.of(1).mapToObj(Double::valueOf).map(Double::shortValue).forEach(System.out::print);  //prints: 1
        System.out.println();
        DoubleStream.of(1).boxed().map(Double::shortValue).forEach(System.out::print);  //prints: 1
        System.out.println();
    }

    private static void arrays() {

        int[] ai = {2, 3, 1, 5, 4};
        Arrays.stream(ai).forEach(System.out::print);  //prints: 23154
        System.out.println();
        Arrays.stream(ai, 1, 3).forEach(System.out::print);  //prints: 31
        //Arrays.stream(ai, 3, 1).forEach(System.out::print);  //ArrayIndexOutOfBoundsException
        System.out.println();

        long[] al = {2, 3, 1, 5, 4};
        Arrays.stream(al).forEach(System.out::print);  //prints: 23154
        System.out.println();
        Arrays.stream(al, 1, 3).forEach(System.out::print);  //prints: 31
        //Arrays.stream(al, 3, 1).forEach(System.out::print);  //ArrayIndexOutOfBoundsException
        System.out.println();

        double[] ad = {2., 3., 1., 5., 4.};
        Arrays.stream(ad).forEach(System.out::print);  //prints: 2.03.01.05.04.0
        System.out.println();
        String res = Arrays.stream(ad).mapToObj(String::valueOf).collect(Collectors.joining(" "));
        System.out.println(res);    //prints: 2.0 3.0 1.0 5.0 4.0
        res = Arrays.stream(ad).boxed().map(Object::toString).collect(Collectors.joining(" "));
        System.out.println(res);    //prints: 2.0 3.0 1.0 5.0 4.0
        Arrays.stream(ad, 1, 3).forEach(System.out::print);  //prints: 3.01.0
        System.out.println();
        res = Arrays.stream(ad, 1, 3).mapToObj(String::valueOf).collect(Collectors.joining(" "));
        System.out.println(res);   //prints: 3.0 1.0
        res = Arrays.stream(ad, 1, 3).boxed().map(Object::toString).collect(Collectors.joining(" "));
        //Arrays.stream(ad, 3, 1).forEach(System.out::print);  //ArrayIndexOutOfBoundsException
        System.out.println(res);    //prints: 3.0 1.0
    }

    private static void range() {

        System.out.println();
        IntStream.range(1, 3).forEach(System.out::print);  //prints: 12
        System.out.println();
        LongStream.range(1, 3).forEach(System.out::print);  //prints: 12
        System.out.println();
        IntStream.rangeClosed(1, 3).forEach(System.out::print);  //prints: 123
        System.out.println();
        LongStream.rangeClosed(1, 3).forEach(System.out::print);  //prints: 123
        System.out.println();

        IntStream.range(3, 3).forEach(System.out::print);  //prints:
        System.out.println();
        LongStream.range(3, 3).forEach(System.out::print);  //prints:
        System.out.println();
        IntStream.rangeClosed(3, 3).forEach(System.out::print);  //prints: 3
        System.out.println();
        LongStream.rangeClosed(3, 3).forEach(System.out::print);  //prints: 3
        System.out.println();

        IntStream.range(3, 1).forEach(System.out::print);  //prints:
        System.out.println();
        LongStream.range(3, 1).forEach(System.out::print);  //prints:
        System.out.println();
        IntStream.rangeClosed(3, 1).forEach(System.out::print);  //prints:
        System.out.println();
        LongStream.rangeClosed(3, 1).forEach(System.out::print);  //prints:
        System.out.println();


    }

    private static class Person {
        private String name;
        private int age;

        public Person(int age, String name) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return this.name; }

        public int getAge() {
            return this.age;
        }

        @Override
        public String toString() {
            return "Person1{name:" + this.name + ",age:" + age + "}";
        }
    }

}
