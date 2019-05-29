package hibernate.saveAndRead;

import hibernate.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.List;

public class SaveToFile {

    private String XMLFileName = "zapisXML.xml";
    private String JSONFileName = "zapisJSON.json";

    private ArrayList <Czlonek> MemberList;
    private ArrayList <Klub> ClubList;
    private ArrayList <Prezes> PresidentList;
    private ArrayList <Spotkanie> MeetingList;
    private ArrayList <Obecnosc> PresenceList;

    private Temporary temp;

    private EntityManager entityManager;

    public SaveToFile(EntityManager entityManager) {
        this.entityManager = entityManager;

        Query zapytanie = entityManager.createQuery("SELECT e FROM Czlonek e");
        MemberList = (ArrayList) zapytanie.getResultList();

        zapytanie = entityManager.createQuery("SELECT e FROM Klub e");
        ClubList = (ArrayList) zapytanie.getResultList();

        zapytanie = entityManager.createQuery("SELECT e FROM Prezes e");
        PresidentList = (ArrayList) zapytanie.getResultList();

        zapytanie = entityManager.createQuery("SELECT e FROM Spotkanie e");
        MeetingList = (ArrayList) zapytanie.getResultList();

        zapytanie = entityManager.createQuery("SELECT e FROM Obecnosc e");
        PresenceList = (ArrayList) zapytanie.getResultList();

        temp = new Temporary(MemberList,ClubList,PresidentList,MeetingList,PresenceList);
    }

    public void readFromDBAndSaveToXML() {

        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(XMLFileName)));

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: While Creating or Opening the File: " + XMLFileName);
        }

        encoder.writeObject(temp);
        encoder.close();
    }

    public void readFromDBAndSaveToJSON() {

    }
}
