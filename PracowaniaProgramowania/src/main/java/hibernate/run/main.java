package hibernate.run;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.mysql.cj.exceptions.CJCommunicationsException;
import hibernate.model.*;
import hibernate.queries.Queries;
import hibernate.saveAndRead.ReadFromFile;
import hibernate.saveAndRead.SaveToFile;

import java.sql.Array;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by agnzim on 08.05.2019.
 */
public class main {


   /* public List<Czlonek> getMembersByName(String name, EntityManager entityManager) {

        TypedQuery<Czlonek> query = entityManager.createQuery("SELECT c FROM Czlonek c WHERE c.nazwisko LIKE :name", Czlonek.class);
        return query.setParameter("name", "%" + name + "%").getResultList();
    } */

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        //EntityManager entityManager2 = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic"); //taka nazwa jak w persistence.xml
            entityManager = entityManagerFactory.createEntityManager(); //utworz entityManagera
            //entityManager2 = entityManagerFactory.createEntityManager(); //utworz entityManagera
            entityManager.getTransaction().begin(); // rozpocznij transakcje

            generacjaDanych(entityManager);

            entityManager.flush();
            entityManager.getTransaction().commit();
            System.out.println("Nadpisano bazę danych");

            // Pobieranie danych

            Query zapytanie = entityManager.createQuery("Select firstName FROM Osoba");
            System.out.println("Odczytalem: " + zapytanie.getResultList()+"\n");

            SaveToFile s1 = new SaveToFile(entityManager);
            System.out.println("ROZPOCZYNAM FAZE ZAPISU DO PLIKU JSONOWEGO !");
            s1.readFromDBAndSaveToJSON();
            System.out.println("ZAKONCZYLEM FAZE ZAPISU DO PLIKU JSONOWEGO !");

            System.out.println("ROZPOCZYNAM FAZE ZAPISU DO PLIKU XMLOWEGO !");
            s1.readFromDBAndSaveToXML();
            System.out.println("ZAKONCZYLEM FAZE ZAPISU DO PLIKU XMLOWEGO !");

            ReadFromFile r1 = new ReadFromFile(entityManager);
            r1.readFromXMLAndWriteToDB();
            //r1.ClearAllTables();

/*            System.out.println("PIERWSZE ODCZYTANIE");
            Query zapytanie1 = entityManager.createQuery("SELECT e FROM Czlonek e");
            List<Czlonek> czlonkowie = zapytanie1.getResultList();
            for(Czlonek cz : czlonkowie){
                System.out.println(cz);
            }

            entityManager.getTransaction().begin();

            System.out.println("USUWANIE");
            //Query usuwanie = entityManager.createQuery("DELETE FROM Czlonek WHERE club = '2'");
            //czlonkowie = usuwanie.getResultList();

            entityManager.createQuery("UPDATE Czlonek SET lastName = 'BBBBB' where club='2'").executeUpdate();
            //entityManager.flush();
            entityManager.getTransaction().commit();

            //entityManager.getEntityManagerFactory().getCache().evictAll();
            entityManager.clear();
//
            System.out.println("DRUGIE ODCZYTANIE");

            //entityManager.getTransaction().begin();
            Query zapytanie = entityManager.createQuery("SELECT e FROM Czlonek e");
            //entityManager.getTransaction().commit();
            List<Czlonek> czlonkowie2 = zapytanie.getResultList();
            for(Czlonek cz : czlonkowie2){
                System.out.println(cz);
            }

            System.out.println("TRZECIE ODCZYTANIE (STRONICOWANIE)");
            ArrayList <Obecnosc> listaWynikow = new ArrayList<>();
            Queries q = new Queries(entityManager);
            int ileStron = q.sumPages(10);
            for(int i=1; i<=ileStron; i++) {
                listaWynikow = q.getAllPresenceByPage(10,i);
                for(Obecnosc o : listaWynikow) {
                    System.out.println(o);
                }
                System.out.println("------------ KOLEJNA STRONA -------------");
            }
            //q.updateRecordInTableCzlonek(2,"nazwisko","nazwisko"); //zmien nazwisko Bozenie
            System.out.println("Tworze obiekt do zapisu z bazy danych do pliku");
            SaveToFile s1 = new SaveToFile(entityManager);
            System.out.println("Obiekt zostal utworzony");
            System.out.println("ROZPOCZYNAM FAZE ZAPISU DO PLIKU JSONOWEGO !");
            s1.readFromDBAndSaveToJSON();
            System.out.println("ZAKONCZYLEM FAZE ZAPISU DO PLIKU JSONOWEGO !");

            System.out.println("ROZPOCZYNAM FAZE ZAPISU DO PLIKU XMLOWEGO !");
            s1.readFromDBAndSaveToXML();
            System.out.println("ZAKONCZYLEM FAZE ZAPISU DO PLIKU XMLOWEGO !");

            ReadFromFile r1 = new ReadFromFile(entityManager);
            r1.readFromXMLAndWriteToDB();
 */
        }catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed, tu masz dlaczego\n" + ex);
        } finally {
            entityManagerFactory.close();
        }
    }




    public static void generacjaDanych(EntityManager entityManager) {
        System.out.println("1) Jestem przed tworzeniem kont");

        ArrayList<Konto> AccountsList = new ArrayList<>();
        AccountsList.add(new Konto("kubaBor","kubaBor123",ZonedDateTime.now().minusDays(2)));
        AccountsList.add(new Konto("BoTo","BoTo123",ZonedDateTime.now().minusDays(3)));
        AccountsList.add(new Konto("ToKura","ToKura123",ZonedDateTime.now().minusDays(4)));
        AccountsList.add(new Konto("KrGr","KrGr123",ZonedDateTime.now().minusDays(5)));

        System.out.println("2) Po stworzeniu kont, przed twrozeniem Osob");

        ArrayList<Osoba> PeopleList = new ArrayList<>();
        PeopleList.add(new Osoba("00240857435","Jakub", "Borewicz", AccountsList.get(0)));
        PeopleList.add(new Osoba("81031971754","Bożena", "Tomaszewicz", AccountsList.get(1)));
        PeopleList.add(new Osoba("92102662558","Tomasz", "Kurowicz", AccountsList.get(2)));
        PeopleList.add(new Osoba("86051865717","Krzysztof", "Grunkiewicz", AccountsList.get(3)));
        PeopleList.add(new Osoba("78090512633","Błażej", "Polewicz"));
        System.out.println("3) Po osobach, przed Druzyna");

        ArrayList<Postac> CharactersList = new ArrayList<>();
        CharactersList.add(new Postac("WalecznaWalkyria",1));
        CharactersList.add(new Postac("JohnRambo",2));
        CharactersList.add(new Postac("JamesBond",2));
        CharactersList.add(new Postac("LeeMinHoo",4));
        CharactersList.add(new Postac("NaniNani",4));
        CharactersList.add(new Postac("KimSeJin",4));

        ArrayList<Druzyna> TeamsList = new ArrayList<>();
        TeamsList.add(new Druzyna("RocketPower"));

        CharactersList.get(0).setTeam(TeamsList.get(0).getTeamName());
        CharactersList.get(1).setTeam(TeamsList.get(0).getTeamName());
        CharactersList.get(3).setTeam(TeamsList.get(0).getTeamName());

        ArrayList<Pupil> PetsList = new ArrayList<>();
        PetsList.add(new Pupil("Okruszek","Pies"));

        CharactersList.get(0).setPet(PetsList.get(0));

        for(Konto k : AccountsList) entityManager.persist(k);
        for(Osoba o : PeopleList) entityManager.persist(o);
        for(Druzyna d : TeamsList) entityManager.persist(d);
        for(Postac p : CharactersList) entityManager.persist(p);
        for(Pupil p : PetsList) entityManager.persist(p);

    };

}




