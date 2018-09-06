package com.packt.cookbook.ch02_oop.g_objects;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsDemo {

    public static void main(String... args){
        //compare();
        //toStringWhenNull();
        //checkIndex();
        //requireNonNull();
        //hash();
        //isNull();
        equal();
    }


    private static void equal(){

        String o1 = "o";
        String o2 = "o";
        System.out.println(Objects.equals(o1, o2));        //prints: true

        o1 = null;
        o2 = null;
        System.out.println(Objects.equals(o1, o2));        //prints: true


        Integer[] ints1 = {1,2,3};
        Integer[] ints2 = {1,2,3};
        System.out.println(Objects.equals(ints1, ints2));        //prints: false
        Integer[][] iints1 = {{1,2,3},{1,2,3}};
        Integer[][] iints2 = {{1,2,3},{1,2,3}};
        System.out.println(Objects.equals(iints1, iints2));       //prints: false


        o1 = "o";
        o2 = "o";
        System.out.println(Objects.deepEquals(o1, o2));    //prints: true
        o1 = null;
        o2 = null;
        System.out.println(Objects.deepEquals(o1, o2));    //prints: true

        //Integer[] ints1 = {1,2,3};
        //Integer[] ints2 = {1,2,3};
        System.out.println(Objects.deepEquals(ints1, ints2));    //prints: true
        //Integer[][] iints1 = {{1,2,3},{1,2,3}};
        //Integer[][] iints2 = {{1,2,3},{1,2,3}};
        System.out.println(Objects.deepEquals(iints1, iints2));   //prints: true

        Integer[][] iints3 = {{1,2,3},{1,3,2}};
        System.out.println(Objects.deepEquals(iints1, iints3));   //prints: false


        //Integer[] ints1 = {1,2,3};
        //Integer[] ints2 = {1,2,3};
        System.out.println(Arrays.equals(ints1, ints2));        //prints: true
        System.out.println(Arrays.deepEquals(ints1, ints2));    //prints: true

        System.out.println(Arrays.equals(iints1, iints2));       //prints: false
        System.out.println(Arrays.deepEquals(iints1, iints2));   //prints: true



    }

    private static void isNull(){
        String obj = null;
        System.out.println(obj == null);           //prints: true
        System.out.println(Objects.isNull(obj));   //prints: true
        obj = "";
        System.out.println(obj == null);           //prints: false
        System.out.println(Objects.isNull(obj));   //prints: false

        obj = null;
        System.out.println(obj != null);           //prints: false
        System.out.println(Objects.nonNull(obj));  //prints: false
        obj = "";
        System.out.println(obj != null);           //prints: true
        System.out.println(Objects.nonNull(obj));  //prints: true
    }

    private static void hash(){
        System.out.println(Objects.hashCode(null));   //prints: 0
        System.out.println(Objects.hashCode("abc"));   //prints: 96354


        System.out.println(Objects.hash(null));  //prints: 0
        System.out.println(Objects.hash("abc"));  //prints: 96385

        String[] arr = {"abc"};
        System.out.println(Objects.hash(arr));  //prints: 96385

        Object[] objs = {"a", 42, "c"};
        System.out.println(Objects.hash(objs));  //prints: 124409
        System.out.println(Objects.hash("a", 42, "c"));  //prints: 124409
    }

    private static void requireNonNull(){
        String obj = null;

        try {
            Objects.requireNonNull(obj);
        } catch (NullPointerException ex){
            System.out.println(ex.getMessage());  //prints: null
        }

        //String obj = null;
        try {
            Objects.requireNonNull(obj, "Parameter 'obj' is null");
        } catch (NullPointerException ex){
            System.out.println(ex.getMessage());  //prints: Parameter 'obj' is null
        }

        //String obj = null;
        Supplier<String> msg1 = () -> {
            String msg = "Message";
            return msg;

        };
        try {
            Objects.requireNonNull(obj, msg1);
        } catch (NullPointerException ex){
            System.out.println(ex.getMessage());  //prints: Message
        }

        Supplier<String> msg2 = () -> null;
        try {
            Objects.requireNonNull(obj, msg2);
        } catch (NullPointerException ex){
            System.out.println(ex.getMessage());  //prints: null
        }

        Supplier<String> msg3 = null;
        try {
            Objects.requireNonNull(obj, msg3);
        } catch (NullPointerException ex){
            System.out.println(ex.getMessage());  //prints: null
        }

        //String obj = null;
        System.out.println(Objects.requireNonNullElse(obj, "Default value"));   //prints: Default value

        try {
            Objects.requireNonNullElse(obj, null);
        } catch (NullPointerException ex){
            System.out.println(ex.getMessage());  //prints: defaultObj
        }

        //String obj = null;
        System.out.println(Objects.requireNonNullElseGet(obj, msg1));   //prints: Message

        Integer objInt = null;
        Supplier<Integer> msg4 = () -> {
            Integer val = 42;
            return val;

        };
        try {
            System.out.println(Objects.requireNonNullElseGet(objInt, msg4));
        } catch (NullPointerException ex){
            System.out.println(ex.getMessage());  //prints: 42
        }

        try {
            System.out.println(Objects.requireNonNullElseGet(obj, null));
        } catch (NullPointerException ex){
            System.out.println(ex.getMessage());  //prints: supplier
        }

    }

    private static void checkIndex(){
        List<Integer> list = List.of(1, 2);
        try {
            Objects.checkIndex(3, list.size());
        } catch (IndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());  //prints: Index 3 out-of-bounds for length 2
        }

        //List<Integer> list = List.of(1, 2);
        try {
            Objects.checkFromIndexSize(1, 3, list.size());
        } catch (IndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());  //prints: Range [1, 1 + 3) out-of-bounds for length 2
        }

        //List<Integer> list = List.of(1, 2);
        try {
            Objects.checkFromToIndex(1, 3, list.size());
        } catch (IndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());  //prints: Range [1, 3) out-of-bounds for length 2
        }
    }

    private static void toStringWhenNull(){
        System.out.println(Objects.toString("a")); //prints: a
        System.out.println(Objects.toString(null)); //prints: null
        System.out.println(Objects.toString("a", "b")); //prints: a
        System.out.println(Objects.toString(null, "b")); //prints: b
    }

    private static void compare(){

        int res = Objects.compare("a", "c", Comparator.naturalOrder());
        System.out.println(res);       //prints: -2
        res = Objects.compare("a", "a", Comparator.naturalOrder());
        System.out.println(res);       //prints: 0
        res = Objects.compare("c", "a", Comparator.naturalOrder());
        System.out.println(res);       //prints: 2
        res = Objects.compare("c", "a", Comparator.reverseOrder());
        System.out.println(res);       //prints: -2

        res = Objects.compare(3, 5, Comparator.naturalOrder());
        System.out.println(res);       //prints: -1
        res = Objects.compare(3, 3, Comparator.naturalOrder());
        System.out.println(res);       //prints: 0
        res = Objects.compare(5, 3, Comparator.naturalOrder());
        System.out.println(res);       //prints: 1
        res = Objects.compare(5, 3, Comparator.reverseOrder());
        System.out.println(res);       //prints: -1
        res = Objects.compare("5", "3", Comparator.reverseOrder());
        System.out.println(res);       //prints: -2

        //res = Objects.compare(null, "c", Comparator.naturalOrder());   //NullPointerException
        //res = Objects.compare("a", null, Comparator.naturalOrder());   //NullPointerException
        res = Objects.compare(null, null, Comparator.naturalOrder());
        System.out.println(res);       //prints: 0


    }


}
