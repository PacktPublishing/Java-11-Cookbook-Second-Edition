package com.packt.cookbook.ch06_db.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Person2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int age;
    private String name;

    @ManyToOne
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

