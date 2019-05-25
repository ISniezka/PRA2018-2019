package hibernate.run;

import javax.persistence.*;
import javax.persistence.Entity;
import hibernate.model.Czlonek;
import hibernate.model.Klub;
import hibernate.model.Prezes;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by agnzim on 08.05.2019.
 */
public class main {


    public List<Czlonek> getMembersByName(String name, EntityManager entityManager) {

        TypedQuery<Czlonek> query = entityManager.createQuery("SELECT c FROM Czlonek c WHERE c.nazwisko LIKE :name", Czlonek.class);
        return query.setParameter("name", "%" + name + "%").getResultList();
    }

    public static void main(String[] args) {

        System.out.println("Włączyłem się");

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic"); //taka nazwa jak w persistence.xml
            entityManager = entityManagerFactory.createEntityManager(); //utworz entityManagera
            entityManager.getTransaction().begin(); // rozpocznij transakcje

            generacjaDanych(entityManager);
            entityManager.flush();
            entityManager.getTransaction().commit();
            System.out.println("Nadpisano bazę danych");
/*
            entityManager.getTransaction().begin();
            Czlonek f = entityManager.createQuery("select c.lastName from Czlonek c", Czlonek.class).getSingleResult();
            entityManager.getTransaction().commit(); */
            //Query query = entityManager.createQuery("Select a FROM Czlonek a");
            /*String zapytanie = "SELECT e.firstName FROM Czlonek e";
            Query query2 = entityManager.createQuery(zapytanie);
            List<Czlonek> czlonkowie = query2.getResultList();
            for(Czlonek cz : czlonkowie){
            System.out.println(czlonkowie); */

        }catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed, tu masz dlaczego\n" + ex);
        } finally {
            entityManagerFactory.close();
        }

    }


    public static void generacjaDanych(EntityManager entityManager){

        ArrayList<Czlonek> MemberList = new ArrayList<>();
        MemberList.add(new Czlonek(true,"Jakub","Borewicz",1, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"Bożena","Tomaszewicz",1, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"Tomasz","Kurowicz",1, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"Krzysztof","Grunkiewicz",1, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"Joanna","Polewicz",1, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"Błażej","Janicki",2, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"olaf","Kurnicki",2, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"Cytryna","Słodzicka",2, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"Karolina","Sznycelnicka",2, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"Jolanta","Zanicka",2, ZonedDateTime.now() ));
        MemberList.add(new Czlonek(true,"Krystyna","Radziwiłówna",3, ZonedDateTime.now() ));
        for (int i = 0; i<11; i++) {
            entityManager.persist(MemberList.get(i));
        }

        ArrayList<Klub> ClubList = new ArrayList<>();
        ClubList.add(new Klub("VerbalVictory", ZonedDateTime.parse("2017-07-01T00:00:00Z[CET]"),2));
        ClubList.add(new Klub("PoznajToastmasters", ZonedDateTime.parse("2018-07-01T00:00:00Z[CET]"),8));
        ClubList.add(new Klub("Kontestmastes", ZonedDateTime.parse("2019-01-01T00:00:00Z[CET]"),11));
        for (int i = 0; i<3; i++) {
            entityManager.persist(ClubList.get(i));
        }

        ArrayList<Prezes> PresidentList = new ArrayList<>();
        PresidentList.add(new Prezes(1,1,ZonedDateTime.parse("2017-07-01T00:00:00Z[CET]"),ZonedDateTime.parse("2017-12-31T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(2,1,ZonedDateTime.parse("2018-01-01T00:00:00Z[CET]"),ZonedDateTime.parse("2018-06-30T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(3,1,ZonedDateTime.parse("2018-07-01T00:00:00Z[CET]"),ZonedDateTime.parse("2018-12-31T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(4,1,ZonedDateTime.parse("2019-01-01T00:00:00Z[CET]"),ZonedDateTime.parse("2019-06-30T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(7,2,ZonedDateTime.parse("2018-07-01T00:00:00Z[CET]"),ZonedDateTime.parse("2018-12-31T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(6,2,ZonedDateTime.parse("2019-01-01T00:00:00Z[CET]"),ZonedDateTime.parse("2019-06-30T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(11,3,ZonedDateTime.parse("2019-01-01T00:00:00Z[CET]"),ZonedDateTime.parse("2019-06-30T00:00:00Z[CET]")));
        for (int i = 0; i<7; i++) {
            entityManager.persist(PresidentList.get(i));
        }
    };

}




