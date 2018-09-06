package com.packt.cookbook.ch06_db.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class MyBatis {
    public static void main(String... args) {
        //insertReadPerson();
        //insertReadPersons();
        //updateReadPersons();
        //deleteReadPersons();
        //crudPerson();
        //relationships();
    }

    private static void relationships(){
        String resource = "mybatis/mb-config2.xml";
        String familyMapperNamespace = "mybatis.FamilyMapper";
        String personMapperNamespace = "mybatis.Person2Mapper";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory =
                    new SqlSessionFactoryBuilder().build(inputStream);
            try(SqlSession session = sqlSessionFactory.openSession()){

                Family f = new Family("The Jones");
                session.insert(familyMapperNamespace + ".insertFamily", f);
                System.out.println("Family id=" + f.getId());

                Person2 p = new Person2(25, "Jill", f);
                session.insert(personMapperNamespace + ".insertPerson", p);
                System.out.println(p);

                p = new Person2(30, "John", f);
                session.insert(personMapperNamespace + ".insertPerson", p);
                System.out.println(p);

                session.commit();

                List<Person2> pList = session.selectList(familyMapperNamespace + ".selectMembersOfFamily", f.getId());
                System.out.println("Family (id=" + f.getId() + ") members:");
                for (Person2 p1 : pList) {
                    System.out.println("   " + p1);
                }

                List<Family> fList = session.selectList(familyMapperNamespace + ".selectFamilies");
                for (Family f1: fList) {
                    System.out.println("Family " + f1.getName() + " has " + f1.getMembers().size() + " members:");
                    for(Person2 p1: f1.getMembers()){
                        System.out.println("   " + p1);
                    }
                }

                int c = session.delete(familyMapperNamespace + ".deleteFamilies");
                System.out.println("Deleted " + c + " families");
                session.commit();

                c = session.selectOne(familyMapperNamespace + ".selectFamiliesCount");
                System.out.println("Total family records: " + c);


                c = session.selectOne(personMapperNamespace + ".selectPersonsCount");
                System.out.println("Total person records: " + c);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private static void crudPerson(){
        String resource = "mybatis/mb-config1.xml";
        String mapperNamespace = "mybatis.Person1Mapper";

        List<Person1> persons = List.of(new Person1(10, "John"),
                new Person1(15, "John"),
                new Person1(20, "Bill"));
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try(SqlSession session = sqlSessionFactory.openSession()){
                int id = 0;
                for(Person1 p: persons){
                    session.insert(mapperNamespace + ".insertPerson", p);
                    if(id == 0){
                        id = p.getId();
                    }
                }
                session.commit();
                Person1 p = session.selectOne(mapperNamespace + ".selectPersonById", id);
                System.out.println("By id " + id + ": " + p);

                List<Person1> list = session.selectList(mapperNamespace + ".selectPersons");
                for(Person1 p1: list) {
                    System.out.println("All: " + p1);
                }

                list = session.selectList(mapperNamespace + ".selectPersonsByName", "John");
                for(Person1 p1: list) {
                    p1.setName("Jane");
                    int c = session.update(mapperNamespace + ".updatePersonById", p1);
                    System.out.println("Updated " + c + " records");
                }
                session.commit();

                list = session.selectList(mapperNamespace + ".selectPersons");
                for(Person1 p1: list) {
                    System.out.println("All: " + p1);
                }

                int c = session.delete(mapperNamespace + ".deletePersons");
                System.out.println("Deleted " + c + " persons");
                session.commit();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private static void deleteReadPersons(){
        String resource = "mybatis/mb-config1.xml";
        String mapperNamespace = "mybatis.Person1Mapper";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try(SqlSession session = sqlSessionFactory.openSession()){
                int c = session.delete(mapperNamespace + ".deletePersons");
                System.out.println("Deleted " + c + " persons");
                session.commit();

                List<Person1> list = session.selectList(mapperNamespace + ".selectPersons");
                System.out.println("Total records: " + list.size());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private static void updateReadPersons(){
        String resource = "mybatis/mb-config1.xml";
        String mapperNamespace = "mybatis.Person1Mapper";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try(SqlSession session = sqlSessionFactory.openSession()){
                List<Person1> list = session.selectList(mapperNamespace + ".selectPersonsByName", "John");
                for(Person1 p1: list) {
                    p1.setName("Bill");
                    int c = session.update(mapperNamespace + ".updatePersonById", p1);
                    System.out.println("Updated " + c + " records");
                }
                session.commit();

                list = session.selectList(mapperNamespace + ".selectPersons");
                for(Person1 p1: list) {
                    System.out.println("All: " + p1);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private static void insertReadPersons(){
        String resource = "mybatis/mb-config1.xml";
        String mapperNamespace = "mybatis.Person1Mapper";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try(SqlSession session = sqlSessionFactory.openSession()){
                List<Person1> list = session.selectList(mapperNamespace + ".selectPersons");
                for(Person1 p1: list) {
                    System.out.println("All: " + p1);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private static void insertReadPerson(){
        String resource = "mybatis/mb-config1.xml";
        String mapperNamespace = "mybatis.Person1Mapper";

        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            try(SqlSession session = sqlSessionFactory.openSession()){
                Person1 p = new Person1(10, "John");
                session.insert(mapperNamespace + ".insertPerson", p);
                session.commit();
                p = session.selectOne(mapperNamespace + ".selectPersonById", p.getId());
                System.out.println("By id " + p.getId() + ": " + p);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
