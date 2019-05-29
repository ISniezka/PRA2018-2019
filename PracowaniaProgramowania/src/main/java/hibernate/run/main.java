package hibernate.run;

import javax.persistence.*;
import javax.persistence.Entity;

import hibernate.model.*;

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

            // Pobieranie danych

            Query zapytanie = entityManager.createQuery("Select firstName FROM Czlonek");
            System.out.println("Odczytalem: " + zapytanie.getResultList()+"\n");

            zapytanie = entityManager.createQuery("SELECT e.lastName FROM Czlonek e WHERE e.club = '3'");
            List<String> czlonkowie = zapytanie.getResultList();
            for(String cz : czlonkowie){
                System.out.println(cz);
            }



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

        ArrayList<Klub> ClubList = new ArrayList<>();

        ClubList.add(new Klub("Toastmasters Poznań", ZonedDateTime.parse("2017-07-20T00:00:00Z[CET]"),2,MemberList.get(1)));
        ClubList.add(new Klub("PoznajToastmasters", ZonedDateTime.parse("2018-07-01T00:00:00Z[CET]"),8,MemberList.get(7)));
        ClubList.add(new Klub("Kontestmastes", ZonedDateTime.parse("2019-01-10T00:00:00Z[CET]"),11,MemberList.get(10)));

        for(int i=0; i<MemberList.size(); i++){
            int c = MemberList.get(i).getClub();
            ClubList.get(c-1).addMemberToTheClub(MemberList.get(i));
        }

        ArrayList<Prezes> PresidentList = new ArrayList<>();

        PresidentList.add(new Prezes(1,1,ZonedDateTime.parse("2017-07-20T00:00:00Z[CET]"),ZonedDateTime.parse("2017-12-31T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(2,1,ZonedDateTime.parse("2018-01-01T00:00:00Z[CET]"),ZonedDateTime.parse("2018-06-30T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(3,1,ZonedDateTime.parse("2018-07-01T00:00:00Z[CET]"),ZonedDateTime.parse("2018-12-31T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(4,1,ZonedDateTime.parse("2019-01-01T00:00:00Z[CET]"),ZonedDateTime.parse("2019-06-30T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(7,2,ZonedDateTime.parse("2018-07-01T00:00:00Z[CET]"),ZonedDateTime.parse("2018-12-31T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(6,2,ZonedDateTime.parse("2019-01-01T00:00:00Z[CET]"),ZonedDateTime.parse("2019-06-30T00:00:00Z[CET]")));
        PresidentList.add(new Prezes(11,3,ZonedDateTime.parse("2019-01-10T00:00:00Z[CET]"),ZonedDateTime.parse("2019-06-30T00:00:00Z[CET]")));
//  OSTATECZNE USTAWIEINE PREZESÓW
        for(int i=0; i<PresidentList.size(); i++){
            int c = PresidentList.get(i).getPresidentId();
            PresidentList.get(i).setPresident(MemberList.get(c-1));
        }

        ArrayList<Spotkanie> MeetingList = new ArrayList<>();

// 23 spotkań Poznaj
        MeetingList.add(new Spotkanie(1,"Toastmasters w Poznaniu!", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2017-07-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"a", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2017-08-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"b", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2017-09-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"c", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2017-10-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"d", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2017-11-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"e", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2017-12-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"f", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-01-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"g", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-02-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"h", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-03-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"n", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-04-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"k", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-05-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"u", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-06-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"t", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-07-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"e", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-08-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"g", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-09-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"h", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-10-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"i", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-11-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"o", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2018-12-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"d", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2019-01-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"a", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2019-02-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"p", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2019-03-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"q", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2019-04-20T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(1,"y", "Rondo Kaponiera 5, Poznań", ZonedDateTime.parse("2019-05-20T00:00:00Z[CET]")));
// 11 spotkań Poznaj
        MeetingList.add(new Spotkanie(2,"PoznajToastmasters - Zaczynamy", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2018-07-01T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Gorrrące spotkanie", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2018-08-01T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Papugi", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2018-09-01T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Złota Polska Jesień", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2018-10-01T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Zaduma", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2018-11-01T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Spotkanie Świąteczne", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2018-12-01T00:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Nowy Rok", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2019-01-05T19:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Taniec na lodzie", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2019-02-05T19:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Konkurs mów", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2019-03-05T19:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Wielkanoc", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2019-04-05T19:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(2,"Dzień Dziecka", "Powstańców Wielkopolskich 2A Poznań", ZonedDateTime.parse("2019-05-05T19:00:00Z[CET]")));
// 5 spotkań kontestów
        MeetingList.add(new Spotkanie(3,"Pierwsze spotkanie klubu KontestMasters", "Ratajczaka 5/7 Poznań", ZonedDateTime.parse("2019-01-10T19:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(3,"Jak zaczać", "Ratajczaka 5/7 Poznań", ZonedDateTime.parse("2019-02-01T19:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(3,"Powoli do przodu", "Ratajczaka 5/7 Poznań", ZonedDateTime.parse("2019-03-10T19:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(3,"Unia Europejska", "Ratajczaka 5/7 Poznań", ZonedDateTime.parse("2019-04-10T19:00:00Z[CET]")));
        MeetingList.add(new Spotkanie(3,"Idę na wybory", "Ratajczaka 5/7 Poznań", ZonedDateTime.parse("2019-05-10T19:00:00Z[CET]")));

// OSTATECZNE USTAWIEINE KLUBU
        for(int i=0; i<MeetingList.size(); i++){
            int c = MeetingList.get(i).getClubId();
            ClubList.get(c-1).addClubMeeting(MeetingList.get(i));
        }

//   OSTATECZNE USTAWIEINE CZŁONKÓW
        for(int i=0; i<MemberList.size(); i++){
            int c = MemberList.get(i).getClub();
            MemberList.get(i).setMyClub(ClubList.get(c-1));
        }

        ArrayList<Obecnosc> PresenceList = new ArrayList<>();
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,1)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,2)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,3)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,4)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,5)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,6)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,7)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,8)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,9)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,10)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,11)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,13)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,17)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,18)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,19)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,20)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,21)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,22)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(1,23)));

        PresenceList.add(new Obecnosc(new ObecnoscPK(2,1)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,2)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,3)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,4)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,5)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,6)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,9)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,10)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,12)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,13)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,15)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,17)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,18)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,21)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,22)));
        PresenceList.add(new Obecnosc(new ObecnoscPK(2,23)));

        PresenceList.add(new Obecnosc(new ObecnoscPK(3,37)));

//   OSTATECZNE USTAWIEINE SPOTKAŃ
        for(int i=0; i<PresenceList.size(); i++){
            int c = PresenceList.get(i).getMeeting();
            MeetingList.get(c-1).addPresencesAtTheMeeting(PresenceList.get(i));
        }
        for(int i=0; i<MeetingList.size(); i++){
            int c = MeetingList.get(i).getClubId();
            MeetingList.get(i).setHostedClub(ClubList.get(c-1));
        }

//   OSTATECZNE USTAWIEINE OBECNOŚCI
        for (int i = 0; i< PresenceList.size(); i++){
            int c = PresenceList.get(i).getMeeting();
            int e = PresenceList.get(i).getPerson();
            PresenceList.get(i).setPresenceDuring(MeetingList.get(c-1));
            PresenceList.get(i).setPresenceBy(MemberList.get(e-1));
        }

        for (int i = 0; i<PresenceList.size(); i++) {
            entityManager.persist(PresenceList.get(i));
        }

        for (int i = 0; i<MemberList.size() ; i++) {
            entityManager.persist(MemberList.get(i));
        }

        for (int i = 0; i<ClubList.size(); i++) {
            entityManager.persist(ClubList.get(i));
        }

        for (int i = 0; i<PresidentList.size(); i++) {
            entityManager.persist(PresidentList.get(i));
        }

        for (int i = 0; i<MeetingList.size(); i++) {
            entityManager.persist(MeetingList.get(i));
        }
    };

}




