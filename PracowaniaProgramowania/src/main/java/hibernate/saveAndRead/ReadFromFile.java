package hibernate.saveAndRead;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;

import hibernate.model.*;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.beans.XMLDecoder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFromFile {

    private String XMLFileName = "XML.xml";
    private String JSONFileName = "JSON.json";
    //private Temporary temp;
    private EntityManager entityManager;

    public ReadFromFile(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void ClearAllTables() {
        Query zapytanie;
        entityManager.getTransaction().begin();
        zapytanie = entityManager.createQuery("SELECT p FROM Postac p");
        List<Postac> postacie = zapytanie.getResultList();
        for (Postac p : postacie) entityManager.remove(p);

        zapytanie = entityManager.createQuery("SELECT p FROM Pupil p");
        List<Pupil> pupile = zapytanie.getResultList();
        for (Pupil p : pupile) entityManager.remove(p);

        zapytanie = entityManager.createQuery("SELECT d FROM Druzyna d");
        List<Druzyna> druzyny = zapytanie.getResultList();
        for (Druzyna d : druzyny) entityManager.remove(d);

        zapytanie = entityManager.createQuery("SELECT o FROM Osoba o");
        List<Osoba> osoby = zapytanie.getResultList();
        for (Osoba o : osoby) entityManager.remove(o);

        zapytanie = entityManager.createQuery("SELECT k FROM Konto k");
        List<Konto> konta = zapytanie.getResultList();
        for (Konto k : konta) entityManager.remove(k);

        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    public void readFromXML() {
        ClearAllTables();
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(XMLFileName)));

            Temporary result = (Temporary) decoder.readObject();
            result.getPeopleList().get(0).setAccount(result.getAccountsList().get(0));
            result.getPeopleList().get(1).setAccount(result.getAccountsList().get(1));
            result.getPeopleList().get(2).setAccount(result.getAccountsList().get(2));
            result.getPeopleList().get(3).setAccount(result.getAccountsList().get(3));
            result.getCharactersList().get(0).setPet(result.getPetsList().get(0));

            writeToDB(result);
            decoder.close();

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not opened: " + XMLFileName);
        }
    }

    public void readFromJSON() {

        ClearAllTables();
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

            byte[] jsonData = Files.readAllBytes(Paths.get(JSONFileName));
            Temporary result = mapper.readValue(jsonData, Temporary.class);

            result.getPeopleList().get(0).setAccount(result.getAccountsList().get(0));
            result.getPeopleList().get(1).setAccount(result.getAccountsList().get(1));
            result.getPeopleList().get(2).setAccount(result.getAccountsList().get(2));
            result.getPeopleList().get(3).setAccount(result.getAccountsList().get(3));
            result.getCharactersList().get(0).setPet(result.getPetsList().get(0));
            for (Konto k : result.getAccountsList()) k.setId(null);

            for(Pupil p : result.getPetsList()) System.out.println("To wczytalem z pliku JSON: " + p.toString());

            writeToDB(result);

        } catch (IOException e) { e.printStackTrace(); }
    }

    public void writeToDB(Temporary result) {

        System.out.println("rozpoczęcie transakcji");

        entityManager.clear();
        Session session = entityManager.unwrap(Session.class);
        session.clear();
        entityManager.getTransaction().begin();

        System.out.println("persist pupilow");
        for (Pupil p : result.getPetsList()) { p.setId(null); entityManager.persist(p); }
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        System.out.println("persist  druzyn");
        for (Druzyna d : result.getTeamsList()) entityManager.persist(d);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        System.out.println("persist  postaci");
        for (Postac p : result.getCharactersList()) entityManager.persist(p);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        System.out.println("persist  osob");
        for (Osoba o : result.getPeopleList())  {entityManager.persist(o);}
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        System.out.println("persist  kont");
        for (Konto k : result.getAccountsList()) { entityManager.persist(k);}
        entityManager.getTransaction().commit();

        System.out.println("Nadpisano bazę danych informacjami z pliku");
    }
}
