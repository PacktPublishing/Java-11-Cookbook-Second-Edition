package com.packt.cookbook.ch06_db.mybatis;

public class Person2 {
    private int id;
    private int age;
    private String name;

    private Family family;

    public Person2(){}
    public Person2(int age, String name, Family family){
        this.age = age;
        this.name = name;
        this.family = family;
    }

    @Override
    public String toString() {
        return "Person2{id=" + id + ", age=" + age +
                ", name='" + name + "', family='" +
                (family == null ? "" : family.getName())+ "'}";
    }
}
