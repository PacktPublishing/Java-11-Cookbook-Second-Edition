package com.packt.cookbook.ch05_streams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProduceCollection {

    public static void main(String... args) {
        combineToListOrSet();
        toCollection();
        toUnmodifiableCollection();
    }

    private static void toUnmodifiableCollection() {

        List<Person> list1 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.toUnmodifiableList());
        System.out.println(list1);  //[Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        //list1.add(new Person1(30, "Bob"));  //UnsupportedOperationException
        //list1.set(1, new Person1(15, "Bob")); //UnsupportedOperationException
        //list1.remove(new Person1(30, "John")); //UnsupportedOperationException

        Set<Person> set1 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.toUnmodifiableSet());
        System.out.println(set1);  //[Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        Set<Person> set2 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(30, "John"))
                .collect(Collectors.toUnmodifiableSet());
        System.out.println(set2);  //[Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        //set2.add(new Person1(30, "Bob"));  //UnsupportedOperationException

        Predicate<Person> filter = p -> set2.contains(p);
    }

    private static void toCollection() {

        List<Person> list1 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.toList());
        System.out.println(list1);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        list1.add(new Person(30, "Bob"));
        System.out.println(list1);  //[Person1{name:John,age:30}, Person1{name:Jill,age:20}, Person1{name:Bob,age:30}]

        list1.set(1, new Person(15, "Bob"));
        System.out.println(list1);  //prints: [Person1{name:John,age:30}, Person1{name:Bob,age:15}, Person1{name:Bob,age:30}]

        Set<Person> set1 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.toSet());
        System.out.println(set1);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        Set<Person> set2 = Stream.of(new Person(30, "John"), new Person(20, "Jill"), new Person(30, "John"))
                .collect(Collectors.toSet());
        System.out.println(set2);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        set2.add(new Person(30, "Bob"));
        System.out.println(set2);  //[Person1{name:John,age:30}, Person1{name:Jill,age:20}, Person1{name:Bob,age:30}]


        LinkedList<Person> list2 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println(list2);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        LinkedHashSet<Person> set3 = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println(set3);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        set3.add(new Person(30, "Bob"));
        System.out.println(set3);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}, Person1{name:Bob,age:30}]

    }

    private static void combineToListOrSet() {

        List<Person> list = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                //.parallel()
                .collect(ArrayList::new,
                        List::add,         // same as: (a,p)-> a.add(p),
                        List::addAll       // same as: (r, r1)-> r.addAll(r1)
                );
        System.out.println(list);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        list.add(new Person(30, "Bob"));
        System.out.println(list);  //[Person1{name:John,age:30}, Person1{name:Jill,age:20}, Person1{name:Bob,age:30}]

        list.set(1, new Person(15, "Bob"));
        System.out.println(list);  //prints: [Person1{name:John,age:30}, Person1{name:Bob,age:15}, Person1{name:Bob,age:30}]

        //List<Person1>
                list = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                //.parallel()
                .collect(ArrayList::new,
                        ArrayList::add,
                        (r, r1)-> {
                            System.out.println("Combining...");
                            r.addAll(r1);
                        }
                );
        System.out.println(list);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        Set<Person> set = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                //.parallel()
                .collect(HashSet::new,
                        Set::add,         // same as: (a,p)-> a.add(p),
                        Set::addAll       // same as: (r, r1)-> r.addAll(r1)
                );
        System.out.println(set);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}]

        set.add(new Person(30, "Bob"));
        System.out.println(set);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}, Person1{name:Bob,age:30}]

        //Set<Person1>
        set = Stream.of(new Person(30, "John"), new Person(20, "Jill"))
                //.parallel()
                .collect(HashSet::new,
                        HashSet::add,
                        (r, r1)-> {
                            System.out.println("Combining...");
                            r.addAll(r1);
                        }
                );
        System.out.println(list);  //prints: [Person1{name:John,age:30}, Person1{name:Jill,age:20}]

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
