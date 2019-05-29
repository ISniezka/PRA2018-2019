package hibernate.saveAndRead;

import hibernate.model.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile { //TABELA PREZESURY SIE NIE WYPELNIA :(

    private String XMLFileName = "zapisXML.xml";
    private String JSONFileName = "zapisJSON.json";

    private Temporary temp;

    private EntityManager entityManager;

    public ReadFromFile(EntityManager entityManager) {
        this.entityManager = entityManager;
        ClearAllTables();
    }

    public void ClearAllTables() {
        entityManager.getTransaction().begin();

        Query zapytanie = entityManager.createQuery("SELECT e FROM Prezes e");
        List<Prezes> prezesi = zapytanie.getResultList();
        for(int i=0; i<prezesi.size(); i++) {
            entityManager.remove(prezesi.get(i));
        }
        entityManager.getTransaction().commit();
        entityManager.flush();
        entityManager.clear();
        entityManager.getTransaction().begin();

        zapytanie = entityManager.createQuery("SELECT e FROM Czlonek e");
        List<Czlonek> czlonkowie = zapytanie.getResultList();
        for(int i=0; i<czlonkowie.size(); i++) {
            entityManager.remove(czlonkowie.get(i));
        }

        zapytanie = entityManager.createQuery("SELECT e FROM Klub e");
        List<Klub> kluby = zapytanie.getResultList();
        for(int i=0; i<kluby.size(); i++) {
            entityManager.remove(kluby.get(i));
        }

        entityManager.getTransaction().commit();
        entityManager.flush();
        entityManager.clear();
    }

    public void readFromXMLAndWriteToDB() {
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(XMLFileName)));

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found: " + XMLFileName);
        }

        Temporary result = (Temporary) decoder.readObject();
        decoder.close();
        System.out.println("------- ROZPOCZYNAM JAKIS PROCES ----------");
        for(int i=0; i<result.getPresidentList().size(); i++) {
            System.out.println("ODCZYTALEM: " + result.getPresidentList().get(i));
        }

        entityManager.getTransaction().begin();

        for (int i = 0; i<result.getPresidentList().size(); i++) {
            entityManager.persist(result.getPresidentList().get(i));
        }

        for (int i = 0; i<result.getPresenceList().size(); i++) {
            entityManager.persist(result.getPresenceList().get(i));
        }

        for (int i = 0; i<result.getMemberList().size() ; i++) {
            entityManager.persist(result.getMemberList().get(i));
        }

        for (int i = 0; i<result.getClubList().size(); i++) {
            entityManager.persist(result.getClubList().get(i));
        }

        for (int i = 0; i<result.getMeetingList().size(); i++) {
            entityManager.persist(result.getMeetingList().get(i));
        }
        entityManager.getTransaction().commit();
        entityManager.flush();
        entityManager.clear();

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
    }*/

    public void readFromJSONAndWriteToDB() {
        //if(JSONFileWasSavedBefore) {

       // }
    }
}
