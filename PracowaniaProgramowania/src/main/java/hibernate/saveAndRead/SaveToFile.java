package hibernate.saveAndRead;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.sun.xml.internal.stream.buffer.stax.StreamWriterBufferCreator;
import hibernate.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.channels.ClosedByInterruptException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class SaveToFile {

    private String XMLFileName = "zapisXML.xml";
    private String JSONFileName = "zapisJSON.json";

    private ArrayList <Czlonek> MemberList;
    private ArrayList <Klub> ClubList;
    private ArrayList <Prezes> PresidentList;
    private ArrayList <Spotkanie> MeetingList;
    private ArrayList <Obecnosc> PresenceList;

    private Temporary temp;
    public XMLEncoder encoder = null;
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

      //  temp = new Temporary(MemberList,ClubList,PresidentList,MeetingList,PresenceList); //to ma byc oryginalnie
        temp = new Temporary(MemberList); // dla testow
    }


    PersistenceDelegate zonedDateTimeDelegate = new PersistenceDelegate() {
        @Override
        protected Expression instantiate(Object target, Encoder encoder2) {
            ZonedDateTime other = (ZonedDateTime) target;
            return new Expression(other, ZonedDateTime.class, "of",
                    new Object[] {
                            other.getYear(),
                            other.getMonthValue(),
                            other.getDayOfMonth(),
                            other.getHour(),
                            other.getMinute(),
                            other.getSecond(),
                            other.getNano(),
                            other.getZone()
                    });
        }
    };

    PersistenceDelegate zoneIdDelegate = new PersistenceDelegate() {
        @Override
        protected Expression instantiate(Object target,
                                         Encoder encoder2) {
            ZoneId other = (ZoneId) target;
            return new Expression(other, ZoneId.class, "of",
                    new Object[] { other.getId() });
        }
    };

    public void readFromDBAndSaveToXML() {
       //encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(XMLFileName)));

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: While Creating or Opening the File: " + XMLFileName);
        }

        encoder.setPersistenceDelegate(ZonedDateTime.class, zonedDateTimeDelegate);
        for (Czlonek c: MemberList) {
            encoder.setPersistenceDelegate(c.getJoingDate().getZone().getClass(), zoneIdDelegate);
        }
        for (Klub k: ClubList) {
            encoder.setPersistenceDelegate(k.getOpeningDate().getZone().getClass(), zoneIdDelegate);
        }
        for (Prezes p: PresidentList) {
            encoder.setPersistenceDelegate(p.getcadencyBegin().getZone().getClass(), zoneIdDelegate);
            encoder.setPersistenceDelegate(p.getcadencyEnd().getZone().getClass(), zoneIdDelegate);
        }
        for (Spotkanie s: MeetingList) {
            encoder.setPersistenceDelegate(s.getMeetingDate().getZone().getClass(), zoneIdDelegate);
        }


        encoder.writeObject(temp);
        encoder.close();
    }


    public void readFromDBAndSaveToJSON() {
       // JsonSerializer serializer = new JsonSerializer();
        //using (StreamWriter sw = new StreamWriter(JSONFileName))
        //using (JsonWriter writer = new JsonTextWriter(sw))
        //{            serializer.Serialize(writer, temp);        }

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(JSONFileName);
        try {
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.writeValue(file,temp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
