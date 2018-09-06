package com.packt.boot_rest_demo;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonMapper {
    public List<Person> getPersons();
    public Person getPerson(Integer id);
    public void save(Person person);
    public void insert(Person person);
    public void delete(Integer id);
}
