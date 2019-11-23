package hibernate.queries;

import hibernate.model.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class Queries {

    EntityManager entityManager;

    public Queries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
/*
    public List<Employee> getEmployeeByName(String name) {
        TypedQuery<Employee> query = entityManager.createQuery(
                "SELECT c FROM Employee c WHERE c.lastName LIKE :name", Employee.class);
        return query.setParameter("name", "%" + name + "%").getResultList();
    }  */

    public Konto getAccountByLogin(String login) {
        TypedQuery<Konto> query = entityManager.createQuery(
                "SELECT k FROM Konto k WHERE k.login LIKE :login", Konto.class);
        return query.setParameter("login", "%" + login + "%").getSingleResult();
    }

    public List <Osoba> getPeopleByPESELInRange(ArrayList<String> PeselsList) {
        TypedQuery<Osoba> query = entityManager.createQuery(
                "SELECT o FROM Osoba o WHERE o.pesel IN (:PeselsList) ", Osoba.class);
        return query.setParameter("PeselsList", PeselsList).getResultList();
    }

    public void addPerson(Osoba person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera
    }

    public void addPet(Pupil p) {
        entityManager.getTransaction().begin();
        entityManager.persist(p);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera
    }

    public void addTeam(Druzyna team) { //przekazujemy jako parametr obiekt np. Druzyna
        entityManager.getTransaction().begin();
        entityManager.persist(team);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera
    }

    public List <Druzyna> getAllTeams() {
        Query query = entityManager.createQuery("Select d from Druzyna d");
        return query.getResultList();
    }

    public List <Pupil> getAllPets() {
        Query query = entityManager.createQuery("Select d from Pupil d");
        return query.getResultList();
    }

   /* public int sumPages(int ...pageSize) {

        Query queryTotal = entityManager.createQuery("Select count(o) from Osoba o");
        long countResult = (long) queryTotal.getSingleResult();
        int pageNumber = (int) ((countResult / pageSize) + 1);
        return pageNumber;
    }

    public ArrayList <Obecnosc> getAllPresenceByPage(int size, int pagenr) {
        //calculate total number
        Query queryTotal = entityManager.createQuery
                ("Select count(e) from Obecnosc e");
        long countResult = (long)queryTotal.getSingleResult();

        //create query
        Query query = entityManager.createQuery("Select e FROM Obecnosc e");
        //set pageSize
        int pageSize = size;
        //calculate number of pages
        int pageNumber = (int) ((countResult / pageSize) + 1);

        if (pagenr > pageNumber) pagenr = pageNumber;
        query.setFirstResult((pagenr-1) * pageSize);
        query.setMaxResults(pageSize);

        return (ArrayList) query.getResultList();
    } */

/*
    public void updateTable(String tableName, String fieldName, String newValueOfTheField, int ...id) {
        entityManager.getTransaction().begin();

        if(id.length > 1) // jest to tabela Obecnosc bo podano wiecej niz jedno id
        {
            int idPerson = id[0];
            int idMeeting = id[1];
            //ObecnoscPK obecnoscPK = new ObecnoscPK(idPerson, idMeeting); // COS TUTAJ NIE DZIALA :/ (traverse cannnot be null!)
            entityManager.createQuery("UPDATE " + tableName + " SET " + fieldName + " = " + newValueOfTheField + " WHERE idPerson = " + idPerson + " AND idMeeting = " + idMeeting).executeUpdate();
        }
        else {
            int index = id[0];
            entityManager.createQuery("UPDATE " + tableName + " SET " + fieldName + " = " + newValueOfTheField + " WHERE id= " + index).executeUpdate();
        }

        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera
    }

    public void updateRecordInTableCzlonek(int idOfTheField, String fieldName, String newValueOfTheField) {
        updateTable("Czlonek", fieldName, newValueOfTheField, idOfTheField);
    }

    public void updateRecordInTableKlub(int idOfTheField, String fieldName, String newValueOfTheField) {
        updateTable( "Klub", fieldName, newValueOfTheField, idOfTheField);
    }

    public void updateRecordInTableObecnosc(int idPerson, int idMeeting, String fieldName, String newValueOfTheField) {
        updateTable( "Obecnosc", fieldName, newValueOfTheField, idPerson, idMeeting);
    }

    public void updateRecordInTablePrezes(int idOfTheField, String fieldName, String newValueOfTheField) {
        updateTable( "Prezes", fieldName, newValueOfTheField, idOfTheField);
    }

    public void updateRecordInTableSpotkanie(int idOfTheField, String fieldName, String newValueOfTheField) {
        updateTable( "Spotkanie", fieldName, newValueOfTheField, idOfTheField);
    }

    public void deleteRecordFromDB(String tableName, int idOfTheRecord) {
        entityManager.getTransaction().begin();

        entityManager.createQuery("DELETE FROM " + tableName + " WHERE id = " + idOfTheRecord).executeUpdate(); //nie dziala (bo istnieje klucz obcy do Klubu)

        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera
    }

    public <T> void addRecordToDB(T c) { //przekazujemy jako parametr obiekt np. Czlonek, Klub, Spotkanie
        entityManager.getTransaction().begin();

        entityManager.persist(c);

        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera
    }

    public int sumPages(int pageSize) {
        Query queryTotal = entityManager.createQuery("Select count(e) from Obecnosc e");
        long countResult = (long) queryTotal.getSingleResult();
        int pageNumber = (int) ((countResult / pageSize) + 1);
        return pageNumber;
    }

    public ArrayList <Obecnosc> getAllPresenceByPage(int size, int pagenr) {
        //calculate total number
        Query queryTotal = entityManager.createQuery
                ("Select count(e) from Obecnosc e");
        long countResult = (long)queryTotal.getSingleResult();

        //create query
        Query query = entityManager.createQuery("Select e FROM Obecnosc e");
        //set pageSize
        int pageSize = size;
        //calculate number of pages
        int pageNumber = (int) ((countResult / pageSize) + 1);

        if (pagenr > pageNumber) pagenr = pageNumber;
        query.setFirstResult((pagenr-1) * pageSize);
        query.setMaxResults(pageSize);

        return (ArrayList) query.getResultList();
    }
*/
}
