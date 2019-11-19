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

    private ArrayList <Druzyna> TeamsList;
    private ArrayList <Konto> AccountsList;
    private ArrayList <Osoba> PeopleList;
    private ArrayList <Postac> CharactersList;
    private ArrayList <Pupil> PetsList;

    private Temporary temp;
    private XMLEncoder encoder = null;
    private EntityManager entityManager;

    public SaveToFile(EntityManager entityManager) {
        this.entityManager = entityManager;

        Query zapytanie = entityManager.createQuery("SELECT e FROM Druzyna e");
        TeamsList = (ArrayList) zapytanie.getResultList();

        zapytanie = entityManager.createQuery("SELECT e FROM Konto e");
        AccountsList = (ArrayList) zapytanie.getResultList();

        zapytanie = entityManager.createQuery("SELECT e FROM Osoba e");
        PeopleList = (ArrayList) zapytanie.getResultList();
        //for(Osoba o : PeopleList) System.out.println("Wczytuje W SAVETOFILE: " + o.toString() );
        zapytanie = entityManager.createQuery("SELECT e FROM Postac e");
        CharactersList = (ArrayList) zapytanie.getResultList();

        zapytanie = entityManager.createQuery("SELECT e FROM Pupil e");
        PetsList = (ArrayList) zapytanie.getResultList();

        temp = new Temporary(TeamsList,AccountsList,PeopleList,CharactersList,PetsList); //to ma byc oryginalnie
        //temp = new Temporary(TeamsList,PeopleList,CharactersList);
        //temp = new Temporary(TeamsList); // dla testow
    }


    PersistenceDelegate zonedDateTimeDelegate = new PersistenceDelegate() {
        @Override
        protected Expression instantiate(Object target, Encoder encoder) {
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
        protected Expression instantiate(Object target, Encoder encoder) {
            ZoneId other = (ZoneId) target;
            return new Expression(other, ZoneId.class, "of",
                    new Object[] { other.getId() });
        }
    };

    public void readFromDBAndSaveToXML() {
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(XMLFileName)));
            encoder.setPersistenceDelegate(ZonedDateTime.class, zonedDateTimeDelegate);

            for(Konto k : AccountsList) encoder.setPersistenceDelegate(k.getCreationDate().getZone().getClass(), zoneIdDelegate);
            encoder.writeObject(temp);
            encoder.close();

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: While Creating or Opening the File: " + XMLFileName);
        }
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
