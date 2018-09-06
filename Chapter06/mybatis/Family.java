package com.packt.cookbook.ch06_db.mybatis;

import java.util.ArrayList;
import java.util.List;

public class Family {
    private int id;
    private String name;
    private final List<Person2> members = new ArrayList<>();

    public Family(){}

    public Family(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() { return name; }

    public List<Person2> getMembers(){ return this.members; }
}
