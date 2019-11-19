package hibernate.saveAndRead;

import hibernate.model.*;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile { //TABELA PREZESURY SIE NIE WYPELNIA :( //a teraz żadna :...(

    private String XMLFileName = "odczytXML.xml";
    private String JSONFileName = "odczytJSON.json";

    private Temporary temp;

    private EntityManager entityManager;

    public ReadFromFile(EntityManager entityManager) {
        this.entityManager = entityManager;
        ClearAllTables();
    }

    public void ClearAllTables() {
        Query zapytanie;
        entityManager.getTransaction().begin();
        zapytanie = entityManager.createQuery("SELECT p FROM Postac p");
        List<Postac> postacie = zapytanie.getResultList();
        for(Postac p : postacie) entityManager.remove(p);
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.clear();

        entityManager.getTransaction().begin();
        zapytanie = entityManager.createQuery("SELECT p FROM Pupil p");
        List<Pupil> pupile = zapytanie.getResultList();
        for(Pupil p : pupile) entityManager.remove(p);
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.clear();

        entityManager.getTransaction().begin();
        zapytanie = entityManager.createQuery("SELECT d FROM Druzyna d");
        List<Druzyna> druzyny = zapytanie.getResultList();
        for(Druzyna d : druzyny) entityManager.remove(d);
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.clear();

        entityManager.getTransaction().begin();
        zapytanie = entityManager.createQuery("SELECT o FROM Osoba o");
        List<Osoba> osoby = zapytanie.getResultList();
        for(Osoba o : osoby) entityManager.remove(o);
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.clear();

        entityManager.getTransaction().begin();
        zapytanie = entityManager.createQuery("SELECT k FROM Konto k");
        List<Konto> konta = zapytanie.getResultList();
        for(Konto k : konta) entityManager.remove(k);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    public void readFromXMLAndWriteToDB() {    //Temporary(TeamsList,PeopleList,CharactersList);
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(XMLFileName)));

            Temporary result = (Temporary) decoder.readObject();
            decoder.close();

            System.out.println("ODCZYTUJE KONTO Z PLIKU XML:");
            List<Konto> kos = result.getAccountsList();
            for(Konto ko : kos) System.out.println("Wczytuje: " + ko.toString() );

            System.out.println("ODCZYTUJE OSOBY Z PLIKU XML:");
            result.getPeopleList().get(0).setAccount(result.getAccountsList().get(0));
            result.getPeopleList().get(1).setAccount(result.getAccountsList().get(1));
            result.getPeopleList().get(2).setAccount(result.getAccountsList().get(2));
            result.getPeopleList().get(3).setAccount(result.getAccountsList().get(3));
            List<Osoba> os = result.getPeopleList();
            for(Osoba o : os) System.out.println("Wczytuje: " + o.toString() );

            System.out.println("ODCZYTUJE POSTACIE Z PLIKU XML:");
            result.getCharactersList().get(0).setPet(result.getPetsList().get(0));
            List<Postac> pos = result.getCharactersList();
            for(Postac p : pos) System.out.println("Wczytuje: " + p.toString() );

            System.out.println("ODCZYTUJE DRUZYNY Z PLIKU XML:");
            List<Druzyna> dru = result.getTeamsList();
            for(Druzyna d : dru) System.out.println("Wczytuje: " + d.toString() );

            System.out.println("ODCZYTUJE PUPILE Z PLIKU XML:");
            List<Pupil> pup = result.getPetsList();
            for(Pupil p : pup) System.out.println("Wczytuje: " + p.toString() );

            System.out.println("rozpoczęcie transakcji");

            entityManager.clear();
            Session session = entityManager.unwrap(Session.class);
            session.clear();
            entityManager.getTransaction().begin();

            System.out.println("persist postaci");
            for(Postac p : result.getCharactersList()) entityManager.persist(p);
            System.out.println("persist osob");
            for(Osoba o : result.getPeopleList()) entityManager.persist(o);
            System.out.println("persist druzyn");
            for(Druzyna d : result.getTeamsList()) entityManager.persist(d);
            System.out.println("persist pupilow");
            for(Pupil p : result.getPetsList()) entityManager.persist(p);
            System.out.println("persist kont");
            for(Konto k : result.getAccountsList()) entityManager.persist(k);


            //entityManager.flush();
            entityManager.getTransaction().commit();
            System.out.println("Nadpisano bazę danych informacjami z pliku XML");
            entityManager.close(); /*

            entityManager.getTransaction().begin();
            for(Konto k : result.getAccountsList()) entityManager.merge(k);
            for(Osoba o : os) entityManager.merge(o);
            for(Druzyna d : result.getTeamsList()) entityManager.merge(d);
            for(Pupil p : result.getPetsList()) entityManager.merge(p);
            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.clear();

            entityManager.getTransaction().begin();
            for(Postac p : result.getCharactersList()) entityManager.merge(p);

            entityManager.flush();
            entityManager.getTransaction().commit();
            entityManager.clear();
*/


        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not opened: " + XMLFileName);
        }
    }

    /*
    public void readFromXMLAndWriteToDB() {
        if(XMLFileWasSavedBefore) {
            XMLFileWasSavedBefore = false;

            System.out.println("Rozpoczynam odczyt z pliku");
            String nazwaPliku = "..\\XMLPlik.ser";
            try {
                ObjectInputStream odczyt = new ObjectInputStream(
                                            new BufferedInputStream(
                                                    new FileInputStream(nazwaPliku)));

                ArrayList <Czlonek> MemberList = new ArrayList<>();


            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("NIE UDALO SIE OTWORZYC PLIKU!");
            }
        }
    }

    public void readFromJSONAndWriteToDB() {
        //if(JSONFileWasSavedBefore) {

       // }
    }*/
}
