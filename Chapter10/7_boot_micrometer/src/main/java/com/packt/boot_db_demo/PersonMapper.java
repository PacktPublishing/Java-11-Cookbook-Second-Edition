package com.packt.boot_db_demo;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PersonMapper {

    @Select("SELECT * FROM person")
    public List<Person> getPersons();

    @Select("SELECT * FROM person WHERE id = #{id}")
    public Person getPerson(Integer id);

    @Update("UPDATE person SET first_name = #{firstName}, last_name = #{lastName}, place = #{place} " +
            " WHERE id = #{id}")
    public void save(Person person);

    @Insert("INSERT INTO person(first_name, last_name, place) " +
            " VALUES (#{firstName}, #{lastName}, #{place})")
    @Options(useGeneratedKeys = true)
    public void insert(Person person);

    @Delete("DELETE FROM person WHERE id = #{id}")
    public void delete(Integer id);
}
