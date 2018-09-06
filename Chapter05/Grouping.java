package com.packt.cookbook.ch05_streams;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grouping {

    public static void main(String... args) {
        group();
        groupAndDownstream();
        groupAndDownstreamWithContainer();
        groupingByConcurrent();
        partitioningBy();
    }

    private static Stream<Person> getStreamPerson() {
        return Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(20, "John"));
    }

    private static Stream<Person2> getStreamPerson2(){
        return Stream.of(new Person2(30, "John", "Denver"), new Person2(20, "Jill", "Seattle"), new Person2(20, "John", "Denver"),
                new Person2(30, "John", "Seattle"), new Person2(20, "Jill", "Chicago"), new Person2(20, "John", "Chicago"));
    }

    private static Stream<Person3> getStreamPerson3(){
        return Stream.of(new Person3(30, "John", City.Denver), new Person3(20, "Jill", City.Seattle), new Person3(20, "John", City.Denver),
                new Person3(30, "John", City.Seattle), new Person3(20, "Jill", City.Chicago), new Person3(20, "John", City.Chicago));
    }

    private static void partitioningBy() {

        Map<Boolean, List<Person>> map1 = getStreamPerson()
                .collect(Collectors.partitioningBy(p-> p.getName().contains("i")));
        System.out.println(map1);  //prints: {false=[Person1{name:John,age:30}, Person1{name:John,age:20}], true=[Person1{name:Jill,age:20}]}

        Map<Boolean, List<Person>> map2 = getStreamPerson()
                .collect(Collectors.groupingBy(p-> p.getName().contains("i")));
        System.out.println(map2);  //prints: {false=[Person1{name:John,age:30}, Person1{name:John,age:20}], true=[Person1{name:Jill,age:20}]}


        Map<Boolean, Long> map3 = getStreamPerson()
                .collect(Collectors.partitioningBy(p-> p.getName().contains("i"),  Collectors.counting()));
        System.out.println(map3);  //prints: {false=2, true=1}

        Map<Boolean, Long> map4 = getStreamPerson()
                .collect(Collectors.groupingBy(p-> p.getName().contains("i"),  Collectors.counting()));
        System.out.println(map4);  //prints: {false=2, true=1}
    }

    private static void groupingByConcurrent() {

        ConcurrentMap<String, List<Person>> map1 = getStreamPerson().parallel()
                .collect(Collectors.groupingByConcurrent(Person::getName));
        System.out.println(map1);  //prints: {John=[Person1{name:John,age:30}, Person1{name:John,age:20}], Jill=[Person1{name:Jill,age:20}]}

        ConcurrentMap<String, Double> map2 = getStreamPerson().parallel()
                .collect(Collectors.groupingByConcurrent(Person::getName, Collectors.averagingInt(Person::getAge)));
        System.out.println(map2);  //prints: {John=25.0, Jill=20.0}

        ConcurrentSkipListMap<String, Long> map3 = getStreamPerson().parallel()
                .collect(Collectors.groupingByConcurrent(Person::getName, ConcurrentSkipListMap::new, Collectors.counting()));
        System.out.println(map3);  //prints: {Jill=1, John=2}

    }

    private static void groupAndDownstreamWithContainer() {

        LinkedHashMap<String, Long> map1 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getName, LinkedHashMap::new, Collectors.counting()));
        System.out.println(map1);  //prints: {John=2, Jill=1}

        TreeMap<Integer, Long> map2 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getAge, TreeMap::new, Collectors.counting()));
        System.out.println(map2);  //prints: {20=2, 30=1}

        EnumMap<City, List<Person3>> map3= getStreamPerson3()
                .collect(Collectors.groupingBy(Person3::getCity, () -> new EnumMap<>(City.class), Collectors.toList()));
        System.out.println(map3);  //prints: {Chicago=[Person1{name:Jill,age:20,city:Chicago}, Person1{name:John,age:20,city:Chicago}], Denver=[Person1{name:John,age:30,city:Denver}, Person1{name:John,age:20,city:Denver}], Seattle=[Person1{name:Jill,age:20,city:Seattle}, Person1{name:John,age:30,city:Seattle}]}

        EnumMap<City, Map<Integer, String>> map4= getStreamPerson3()
                .collect(Collectors.groupingBy(Person3::getCity, () -> new EnumMap<>(City.class), Collectors.groupingBy(Person3::getAge, Collectors.mapping(Person3::getName, Collectors.joining(",")))));
        System.out.println(map4);  //prints: {Chicago={20=Jill,John}, Denver={20=John, 30=John}, Seattle={20=Jill, 30=John}}

    }

    private static void groupAndDownstream() {

        Map<String, Set<Person>> map01 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getName, Collectors.toSet()));
        System.out.println(map01);  //prints: {John=[Person1{name:John,age:30}, Person1{name:John,age:20}], Jill=[Person1{name:Jill,age:20}]}

        Map<String, Set<Person>> map02 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(20, "John"), new Person(20, "John"))
                .collect(Collectors.groupingBy(Person::getName, Collectors.toSet()));
        System.out.println(map02);  //prints: {John=[Person1{name:John,age:30}, Person1{name:John,age:20}], Jill=[Person1{name:Jill,age:20}]}

        Map<String, Long> map1 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getName, Collectors.counting()));
        System.out.println(map1);  //prints: {John=2, Jill=1}

        Map<Integer, Long> map2 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.counting()));
        System.out.println(map2);  //prints: {20=2, 30=1}

        Map<TwoStrings, Long> map3 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(30, "John"))
                .collect(Collectors.groupingBy(p -> new TwoStrings(String.valueOf(p.getAge()), p.getName()), Collectors.counting()));
        System.out.println(map3);  //prints: {(20,Jill)=1, (30,John)=2}

        //Function.identity() returns the input unchanged
        Stream.of("a","b","c").map(s -> Function.identity().apply(s)).forEach(System.out::print);  //prints: abc
        System.out.println();

        Map<Person, Long> map4 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(30, "John"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(map4);  //prints: {Person1{name:Jill,age:20}=1, Person1{name:John,age:30}=2}

        Map<String, Double> map5 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));
        System.out.println(map5);  //prints: {John=25.0, Jill=20.0}

        Map<String, List<Integer>> map6 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getName, Collectors.mapping(Person::getAge, Collectors.toList())));
        System.out.println(map6);  //prints: {John=[30, 20], Jill=[20]}

        Map<Integer, String> map7 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.joining(","))));
        System.out.println(map7);  //prints: {20=Jill,John, 30=John}

        Map<Integer, Map<String, String>> map8 = getStreamPerson2()
                .collect(Collectors.groupingBy(Person2::getAge, Collectors.groupingBy(Person2::getName, Collectors.mapping(Person2::getCity, Collectors.joining(",")))));
        System.out.println(map8);  //prints: {20={John=Denver,Chicago, Jill=Seattle,Chicago}, 30={John=Denver,Seattle}}

    }



    private static void group() {

        Map<String, List<Person>> map1 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getName));
        System.out.println(map1);  //prints: {John=[Person1{name:John,age:30}, Person1{name:John,age:20}], Jill=[Person1{name:Jill,age:20}]}

        Map<Integer, List<Person>> map2 = getStreamPerson()
                .collect(Collectors.groupingBy(Person::getAge));
        System.out.println(map2);  //prints: {20=[Person1{name:Jill,age:20}, Person1{name:John,age:20}], 30=[Person1{name:John,age:30}]}

        Map<TwoStrings, List<Person2>> map3 = getStreamPerson2()
                .collect(Collectors.groupingBy(p -> new TwoStrings(String.valueOf(p.getAge()), p.getName())));
        System.out.println(map3);  //prints: {(20,Jill)=[Person1{name:Jill,age:20,city:Seattle}, Person1{name:Jill,age:20,city:Chicago}], (20,John)=[Person1{name:John,age:20,city:Denver}, Person1{name:John,age:20,city:Chicago}], (30,John)=[Person1{name:John,age:30,city:Denver}, Person1{name:John,age:30,city:Seattle}]}
    }

    private static class TwoStrings {
        private String one, two;
        public TwoStrings(String one, String two) {
            this.one = one;
            this.two = two;
        }
        public String getOne() { return this.one; }
        public String getTwo() { return this.two; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TwoStrings)) return false;
            TwoStrings twoStrings = (TwoStrings) o;
            return Objects.equals(getOne(), twoStrings.getOne()) &&
                    Objects.equals(getTwo(), twoStrings.getTwo());
        }
        @Override
        public int hashCode() {
            return Objects.hash(getOne(), getTwo());
        }
        @Override
        public String toString() {
            return "(" + this.one + "," + this.two + ")";
        }
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
            return "Person1{name:" + this.name + ",age:" + this.age + "}";
        }
    }

    private static class Person2 {
        private int age;
        private String name, city;

        public Person2(int age, String name, String city) {
            this.age = age;
            this.name = name;
            this.city = city;
        }
        public int getAge() { return this.age; }
        public String getName() { return this.name; }
        public String getCity() { return this.city; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person2 person = (Person2) o;
            return getAge() == person.getAge() &&
                    Objects.equals(getName(), person.getName()) &&
             Objects.equals(getCity(), person.getCity());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getName(), getAge(), getCity());
        }

        @Override
        public String toString() {
            return "Person1{name:" + this.name + ",age:" + this.age  + ",city:" + this.city + "}";
        }
    }

    private static enum City{
        Chicago, Denver, Seattle
    }

    private static class Person3 {
        private int age;
        private String name;
        private City city;

        public Person3(int age, String name, City city) {
            this.age = age;
            this.name = name;
            this.city = city;
        }
        public int getAge() { return this.age; }
        public String getName() { return this.name; }
        public City getCity() { return this.city; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person)) return false;
            Person3 person = (Person3) o;
            return getAge() == person.getAge() &&
                    Objects.equals(getName(), person.getName()) &&
                    Objects.equals(getCity(), person.getCity());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getName(), getAge(), getCity());
        }

        @Override
        public String toString() {
            return "Person1{name:" + this.name + ",age:" + this.age  + ",city:" + this.city + "}";
        }
    }

}
