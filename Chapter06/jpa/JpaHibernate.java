package com.packt.cookbook.ch06_db.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class JpaHibernate {
    public static void main(String... args) {

        //crudPerson();
        //relationships();
    }

    private static void relationships(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-demo");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Family f = new Family("The Jones");
            em.persist(f);

            Person2 p1 = new Person2(10, "John", f);
            em.persist(p1);

            Person2 p2 = new Person2(15, "Jane", f);
            em.persist(p2);

            f.getMembers().add(p1);
            f.getMembers().add(p2);
            em.getTransaction().commit();

            Query q = em.createQuery("select f from Family f");
            List<Family> fList = q.getResultList();
            for (Family f1 : fList) {
                System.out.println("Family " + f1.getName() + ": " +f1.getMembers().size() + " members:");
                for(Person2 p: f1.getMembers()){
                    System.out.println("   " + p);
                }
            }
            em.getTransaction().begin();
            f.getMembers().remove(p1);
            em.getTransaction().commit();

            q = em.createQuery("select f from Family f");
            fList = q.getResultList();
            for (Family f1 : fList) {
                System.out.println("Family " + f1.getName() + ": " +f1.getMembers().size() + " members:");
                for(Person2 p: f1.getMembers()){
                    System.out.println("   " + p);
                }
            }

            em.getTransaction().begin();
            q = em.createQuery("select f from Family f");
            fList = q.getResultList();
            for (Family f1 : fList) {
                em.remove(f1);
            }
            em.getTransaction().commit();

            q = em.createQuery("select f from Family f");
            fList = q.getResultList();
            System.out.println("Families (after delete): " + fList.size());

        } catch (Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }

    }

    private static void crudPerson(){
        List<Person1> persons = List.of(new Person1(10, "John"),
                new Person1(15, "John"),
                new Person1(20, "Bill"));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-demo");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            int id = 0;
            for(Person1 p: persons){
                em.persist(p);
                if(id == 0){
                    id = p.getId();
                }
            }
            em.getTransaction().commit();

            Query q = em.createQuery("select p from Person1 p");
            List<Person1> pList = q.getResultList();
            System.out.println("All: ");
            for (Person1 p1 : pList) {
                System.out.println("     " + p1);
            }

            CriteriaQuery<Person1> cq = em.getCriteriaBuilder().createQuery(Person1.class);
            cq.select(cq.from(Person1.class));
            pList = em.createQuery(cq).getResultList();
            System.out.println("Size: " + pList.size());

            q = em.createQuery("select p from Person1 p where id = " + id);
            pList = q.getResultList();
            System.out.println("Size (id="+id+"): " + pList.size());
            System.out.println("get(0): " + pList.get(0));


            em.getTransaction().begin();
            q = em.createQuery("select p from Person1 p where name = 'John'");
            pList = q.getResultList();
            System.out.println("Size (John): " + pList.size());
            for (Person1 p1 : pList) {
                p1.setName("Jane");
            }
            em.getTransaction().commit();

            q = em.createQuery("select p from Person1 p");
            pList = q.getResultList();
            System.out.println("After update: ");
            for (Person1 p1 : pList) {
                System.out.println("    " + p1);
            }

            em.getTransaction().begin();
            q = em.createQuery("select p from Person1 p");
            pList = q.getResultList();
            for (Person1 p1 : pList) {
                em.remove(p1);
            }
            em.getTransaction().commit();

            q = em.createQuery("select p from Person1 p");
            pList = q.getResultList();
            System.out.println("Size (after delete): " + pList.size());

        } catch (Exception ex){
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }

    }

}
