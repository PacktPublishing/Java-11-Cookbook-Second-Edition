package com.packt.cookbook.ch06_db.mybatis;

public class Person1 {
    private int id;
    private int age;
    private String name;

    public Person1(){}  //No constructor found in com.packt.cookbook.ch06_db.mybatis.Person1 matching [java.lang.Integer, java.lang.Integer, java.lang.String]
    public Person1(int age, String name){
        this.age = age;
        this.name = name;
    }

    public int getId() { return id; }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person1{id=" + id + ", age=" + age +
                ", name='" + name + "'}";
    }
}
