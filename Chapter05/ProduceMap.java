package com.packt.cookbook.ch05_streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProduceMap {
    public static void main(String... args) {
        combineToMap();
        collectToMap();
        collectToUnmodifiableMap();
        collectToConcurrentMap();
    }

    private static void collectToConcurrentMap(){
        ConcurrentMap<String, Integer> map1 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.toConcurrentMap(Person::getName, Person::getAge));
        System.out.println(map1);  //prints: {John=30, Jill=20}

        //Map<String, Integer> map = Stream.of(new Person1(30, "John"), new Person1(20, "Jill"), new Person1(15, "John"))
        //        .collect(Collectors.toConcurrentMap(Person1::getOne, Person1::getTwo)); //IllegalStateException: Duplicate key John

        Function<Person, List<Integer>> valueMapper = p -> {
            List<Integer> list = new ArrayList<>();
            list.add(p.getAge());
            return list;
        };
        BinaryOperator<List<Integer>> mergeFunction = (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };
        ConcurrentMap<String, List<Integer>> map2 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(Collectors.toConcurrentMap(Person::getName, valueMapper, mergeFunction));
        System.out.println(map2);  //prints: {John=[30, 15], Jill=[20]}

        Function<Person, String> valueMapper2 = person -> String.valueOf(person.getAge());
        BinaryOperator<String> mergeFunction2 = (s1, s2) -> s1 + "," + s2;
        ConcurrentMap<String, String> map3 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(Collectors.toConcurrentMap(Person::getName, valueMapper2, mergeFunction2));
        System.out.println(map3);  //prints: {John=30,15, Jill=20}

        ConcurrentSkipListMap<String, String> map4 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(Collectors.toConcurrentMap(Person::getName, valueMapper2, mergeFunction2, ConcurrentSkipListMap::new));
        System.out.println(map4);  //prints: {Jill=20, John=30,15}
    }

    private static void collectToUnmodifiableMap(){
        Map<String, Integer> map1 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.toUnmodifiableMap(Person::getName, Person::getAge));
        System.out.println(map1);  //prints: {John=30, Jill=20}

        //map1.put("Nick", new Person1(42, "Nick")); //UnsupportedOperationException
        //map1.put("John", new Person1(42, "Nick")); //UnsupportedOperationException
        //map1.remove("John");  //UnsupportedOperationException

        //Map<String, Integer> map = Stream.of(new Person1(30, "John"), new Person1(20, "Jill"), new Person1(15, "John"))
        //        .collect(Collectors.toUnmodifiableMap(Person1::getOne, Person1::getTwo)); //IllegalStateException: Duplicate key John


        Function<Person, List<Integer>> valueMapper = p -> {
            List<Integer> list = new ArrayList<>();
            list.add(p.getAge());
            return list;
        };
        BinaryOperator<List<Integer>> mergeFunction = (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };
        Map<String, List<Integer>> map2 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(Collectors.toUnmodifiableMap(Person::getName, valueMapper, mergeFunction));
        System.out.println(map2);  //prints: {John=[30, 15], Jill=[20]}

        Function<Person, String> valueMapper2 = p -> String.valueOf(p.getAge());
        BinaryOperator<String> mergeFunction2 = (s1, s2) -> s1 + "," + s2;
        Map<String, String> map3 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(Collectors.toUnmodifiableMap(Person::getName, valueMapper2, mergeFunction2));
        System.out.println(map3);  //prints: {John=30,15, Jill=20}

        //Map<String, String> map4 = Stream.of(new Person1(30, "John"), new Person1(20, "Jill"), new Person1(15, "John"))
        //        .collect(Collectors.toUnmodifiableMap(Person1::getOne, valueMapper2, mergeFunction2, HashMap::new)); //no such method
    }

    private static void collectToMap(){
        Map<String, Integer> map1 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println(map1);  //prints: {John=30, Jill=20}

        //Map<String, Integer> map = Stream.of(new Person1(30, "John"), new Person1(20, "Jill"), new Person1(15, "John"))
        //       .collect(Collectors.toMap(Person1::getOne, Person1::getTwo)); //IllegalStateException: Duplicate key John

        Function<Person, List<Integer>> valueMapper = p -> {
            List<Integer> list = new ArrayList<>();
            list.add(p.getAge());
            return list;
        };
        BinaryOperator<List<Integer>> mergeFunction = (l1, l2) -> {
            l1.addAll(l2);
            return l1;
        };
        Map<String, List<Integer>> map2 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(Collectors.toMap(Person::getName, valueMapper, mergeFunction));
        System.out.println(map2);  //prints: {John=[30, 15], Jill=[20]}

        Function<Person, String> valueMapper2 = p -> String.valueOf(p.getAge());
        BinaryOperator<String> mergeFunction2 = (s1, s2) -> s1 + "," + s2;
        Map<String, String> map3 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(Collectors.toMap(Person::getName, valueMapper2, mergeFunction2));
        System.out.println(map3);  //prints: {John=30,15, Jill=20}

        LinkedHashMap<String, String> map4 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(Collectors.toMap(Person::getName, valueMapper2, mergeFunction2, LinkedHashMap::new));
        System.out.println(map4);  //prints: {John=30,15, Jill=20}
    }

    private static void combineToMap() {

        Map<String, Person> map1 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(HashMap::new,
                        (m,p) -> m.put(p.getName(), p),
                        Map::putAll
                );
        System.out.println(map1);  //prints: {John=Person1{name:John,age:30}, Jill=Person1{name:Jill,age:20}}

        Map<String, Integer> map2 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(HashMap::new,
                        (m,p) -> m.put(p.getName(), p.getAge()),
                        Map::putAll
                );
        System.out.println(map2);  //prints: {John=30, Jill=20}

        Map<String, Integer> map3 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                //.parallel()
                .collect(HashMap::new,
                        (m,p) -> m.put(p.getName(), p.getAge()),
                        (m,m1) -> {
                            System.out.println("Combining...");
                            m.putAll(m1);
                        }
                );
        System.out.println(map3);  //prints: {John=30, Jill=20}

        Map<String, Integer> map4 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(HashMap::new,
                        (m,p) -> m.put(p.getName(), p.getAge()),
                        Map::putAll
                );
        System.out.println(map4);  //prints: {John=15, Jill=20}

        BiConsumer<Map<String, List<Integer>>, Person> consumer1 = (m, p) -> {
            List<Integer> list = m.get(p.getName());
            if(list == null) {
                list = new ArrayList<>();
                m.put(p.getName(), list);
            }
            list.add(p.getAge());
        };

        Map<String, List<Integer>> map5 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(HashMap::new,
                        consumer1,
                        Map::putAll
                );
        System.out.println(map5);  //prints: {John=[30, 15], Jill=[20]}

        BiConsumer<Map<String, String>, Person> consumer2 = (m,p) -> {
            if(m.keySet().contains(p.getName())) {
                m.put(p.getName(), m.get(p.getName()) + "," + p.getAge());
            } else {
                m.put(p.getName(), String.valueOf(p.getAge()));
            }
        };

        Map<String, String> map6 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(15, "John"))
                .collect(HashMap::new,
                        consumer2,
                        Map::putAll
                );
        System.out.println(map6);  //prints: {John=30,15, Jill=20}

    }

    private static class Person {
        private int age;
        private String name;

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }
        public int getAge() { return this.age; }
        public String getName() { return this.name; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person person = (Person) o;
            return getAge() == person.getAge() &&
                    Objects.equals(getName(), person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getName(), getAge());
        }

        @Override
        public String toString() {
            return "Person1{name:" + this.name + ",age:" + age + "}";
        }
    }

}

