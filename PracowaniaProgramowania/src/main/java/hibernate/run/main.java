package hibernate.run;
// swager
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
import hibernate.saveAndRead.Generator;

import java.sql.Array;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by agnzim on 08.05.2019.
 */
public class main {

    public static void main(String[] args) {
        System.out.println("START\nUstawienie polaczenia i usuniecie aktualnej bazy danych\n");

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("hibernate-dynamic"); //taka nazwa jak w persistence.xml
            entityManager = entityManagerFactory.createEntityManager(); //utworz entityManagera
            entityManager.getTransaction().begin(); // rozpocznij transakcje

            // generowanie danych do bazy
            Generator generator = new Generator(entityManager);
            System.out.println("\nROZPOCZYNAM NADPISANIE BAZY WYGENEROWANYMI DANYMI");
            generator.generate();

            entityManager.flush();
            entityManager.getTransaction().commit();
            System.out.println("\nNADPISANO BAZE WYGENEROWANYMI DANYMI");

            Queries zapytania = new Queries(entityManager);

            System.out.println("Dane konta o lognie BoTo = " + zapytania.getAccountByLogin("BoTo").toString());
            System.out.println("imiona osob = " + zapytania.getPeopleNames());
            Osoba a = new Osoba("11111111111","Usun","Mnie");
            Konto b = new Konto("doUsuniecia","123",ZonedDateTime.now().minusDays(7));
            zapytania.addPerson(a);
            zapytania.addAccount(a,b);
            //zapytania.addAccountToPerson(b,a);

            //
            /*
            ArrayList<String> listaPeseli = new ArrayList<>();
            listaPeseli.add("00240857435");
            listaPeseli.add("98248876435");
            listaPeseli.add("22240857435");
            for(Osoba o : zapytania.getPeopleByPESELInRange(listaPeseli)) System.out.println("Osoby, ktorych pesel znalazl sie w liscie peseli = " + o.toString());
            */

            // Pobieranie danych

            SaveToFile s1 = new SaveToFile(entityManager);
            //System.out.println("\nZAPISUJE XML !");
            //s1.readFromDBAndSaveToXML();
            //System.out.println("ZAPISALEM XML !");

            ReadFromFile r1 = new ReadFromFile(entityManager);
            //r1.readFromXML();

            System.out.println("\nDODAJE DANE DO BAZY");
            zapytania.addTeam(new Druzyna("Heroes"));
            zapytania.addPet(new Pupil ("Little Tiger Wrrr","kot"));
            zapytania.addPet(new Pupil ("Leon","kameleon"));
            ArrayList<Pupil> petList = (ArrayList<Pupil>) zapytania.getAllPets();

            zapytania.addPetToCaracter(petList.get(1), "JohnRambo");
            zapytania.addPetToCaracter(petList.get(2),"JamesBond");

            System.out.println("\nUSUWAM Z BAZY:  " + b.toString());
            //zapytania.deletePerson(a);
            for(Konto i : zapytania.getAllAccounts()) System.out.println("Z bazy danych: " + i);
            //zapytania.deleteAccount2(a);

            zapytania.updateLastname("92102662558","Lis");
            System.out.println("\nSPRAWDZAM ZMIANY");

            for(Pupil l : petList) System.out.println("Wszystkie pupile: " + l.toString());
            for(Druzyna o : zapytania.getAllTeams()) System.out.println("Wszystkie teamy: " + o.toString());
            for(Osoba o : zapytania.getAllPeople()) System.out.println("Wszytskie osoby" + o.toString());
            for(Konto o : zapytania.getAllAccounts()) System.out.println("Wszytskie konta" + o.toString());

            System.out.println("\nZAPISUJE JSON !");
            s1.readFromDBAndSaveToJSON();
            System.out.println("ZAPISALEM JSON !");

            r1.readFromJSON();

            System.out.println("\nROZPOCZYNAN ZAPYTANIE STRONICOWANE");
            int ileOsobNaStronie = 2;
            int ileStron = zapytania.sumPages(ileOsobNaStronie); //rozmiar strony to ileOsobNaStronie
            System.out.println("Dane dziela sie na: " + ileStron + " stron, zakladajac " + ileOsobNaStronie + " osob na stronie");
            ArrayList <Osoba> listaWynikow = new ArrayList<>();
            for(int i=1; i<=ileStron; i++) {
                listaWynikow = zapytania.getAllPeopleByPage(ileOsobNaStronie, i);
                for(Osoba o : listaWynikow) {
                    System.out.println("Strona " + i + ": " + o);
                }
            }
            System.out.println("SKONCZYLEM ZAPYTANIE STRONICOWANE\n");


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
}




