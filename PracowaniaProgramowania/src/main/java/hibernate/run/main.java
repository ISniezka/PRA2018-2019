package hibernate.run;

import hibernate.model.Czlonek;
import hibernate.model.Employee;

import javax.persistence.*;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.List;
import java.time.ZonedDateTime;
/**
 * Created by agnzim on 08.05.2019.
 */
public class main {


    public List<Czlonek> getEmployeeByName(String name, EntityManager entityManager) {

        TypedQuery<Czlonek> query = entityManager.createQuery(
                "SELECT c FROM pp_czlonkowie c WHERE c.nazwisko LIKE :name", Czlonek.class);
        return query.setParameter("name", "%" + name + "%").getResultList();
    }


    public static void main(String[] args) {

        System.out.println("Włączyłem się");

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            //taka nazwa jak w persistence.xml
            System.out.println("w try, przed hiper-dynamic");

            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic");
            //utworz entityManagera
            System.out.println("po hiper-dynamic, przed entity");

            entityManager = entityManagerFactory.createEntityManager();

            System.out.println("po entity, przed transakcją");

            // rozpocznij transakcje
            entityManager.getTransaction().begin();

            Czlonek c = new Czlonek("Jakub","Borewicz",1, ZonedDateTime.now() );
            Czlonek d = new Czlonek("Aga","Guzioł",1, ZonedDateTime.now() );

            entityManager.persist(c);
            entityManager.persist(d);
            entityManager.flush();
            entityManager.getTransaction().commit();

            //Query query = entityManager.createQuery("Select e FROM pp_czlonkowie e");
            //List<Czlonek> czlonkowie = query.getResultList();
            //for(Czlonek cz : czlonkowie){
              //  System.out.println(cz);
            //}



        }catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
        } finally {
            entityManagerFactory.close();
        }

    }


}




