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

////////////////   GET

    public List <Druzyna> getAllTeams() {
        Query query = entityManager.createQuery("Select d from Druzyna d");
        return query.getResultList();
    }

    public List <Pupil> getAllPets() {
        Query query = entityManager.createQuery("Select d from Pupil d");
        return query.getResultList();
    }

    public List <Osoba> getAllPeople() {
        Query query = entityManager.createQuery("Select d from Osoba d");
        return query.getResultList();
    }

    public List <Konto> getAllAccounts() {
        Query query = entityManager.createQuery("Select d from Konto d");
        return query.getResultList();
    }

    public List <Postac> getAllCaracters() {
        Query query = entityManager.createQuery("Select d from Postac d");
        return query.getResultList();
    }

    public Konto getAccountByLogin(String login) {
        TypedQuery<Konto> query = entityManager.createQuery(
                "SELECT k FROM Konto k WHERE k.login LIKE :login", Konto.class);
        return query.setParameter("login", "%" + login + "%").getSingleResult();
    }

    public List <String> getPeopleNames() {
        TypedQuery<String> query = (TypedQuery<String>) entityManager.createQuery(
                "Select firstName FROM Osoba");
        return query.getResultList();
    }

    public List <Osoba> getPeopleByPESELInRange(ArrayList<String> PeselsList) {
        TypedQuery<Osoba> query = entityManager.createQuery(
                "SELECT o FROM Osoba o WHERE o.pesel IN (:PeselsList) ", Osoba.class);
        return query.setParameter("PeselsList", PeselsList).getResultList();
    }

/////////////////   ADD

    public void addPerson(Osoba person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera
    }

    public void addTeam(Druzyna team) {
        entityManager.getTransaction().begin();
        entityManager.persist(team);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera
    }

    public void addAccount(Osoba o, Konto k) {
        entityManager.getTransaction().begin();
        entityManager.persist(k);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Osoba SET account = :a WHERE pesel = :p");
        query.setParameter("a",k);
        query.setParameter("p",o.getPesel());
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void addPet(Pupil p) {
        entityManager.getTransaction().begin();
        entityManager.persist(p);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.clear(); //wyczysc pamiec entityMenagera
    }

/////////////////    UPDATE

    public void addPetToCaracter(Pupil pupil, String imiePostaci){
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Postac SET pet = :idP WHERE nick = :imieP");
        query.setParameter("idP",pupil);
        query.setParameter("imieP",imiePostaci);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    public void updateLastname(String psl, String lastName){
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Osoba SET lastName = :l WHERE pesel = :p");
        query.setParameter("l",lastName);
        query.setParameter("p",psl);
        query.executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

///////////////////  DELETE

//Employee employee = em.find(Employee.class, 1);
//
//  em.getTransaction().begin();
//  em.remove(employee);
//  em.getTransaction().commit();


    public  void  deleteAccount(Konto kkk) { System.out.println("1");
        Osoba aaa = kkk.getOsoba(); System.out.println("2");
        Osoba o = entityManager.getReference(Osoba.class, aaa.getPesel()); System.out.println("3");
        entityManager.getTransaction().begin(); System.out.println("4");
        o.setAccount(null);System.out.println("5");
        entityManager.refresh(o);System.out.println("6");
        //entityManager.remove(k); System.out.println("\n4");
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    public  void  deleteAccount2(Osoba kkk) { System.out.println("1");
        System.out.println("w funkcji usuwajacej konto: " + kkk);
        //Osoba aaa = kkk.getOsoba(); System.out.println("2");
        //Osoba o = entityManager.getReference(Osoba.class, aaa.getPesel()); System.out.println("3");
        entityManager.getTransaction().begin(); System.out.println("2");

        entityManager.createQuery("UPDATE Osoba SET account = null WHERE pesel= " + kkk.getPesel() ).executeUpdate();
        System.out.println("3");

        entityManager.getTransaction().commit();  System.out.println("1");
        entityManager.clear();
    }


    public void deletePerson (Osoba ppp){
        Osoba o = entityManager.getReference(Osoba.class, ppp.getPesel());
        entityManager.getTransaction().begin();
        entityManager.remove(o);
        entityManager.getTransaction().commit();
        entityManager.clear();
    };


/////////////////////// STRONNICOWANIE   /////////////////////
    public int sumPages(int ...arguments) {
        int pageSize = 5; //jezeli nie podano zadnego argumentu to domyslny rozmiar = 5
        if(arguments.length == 1) pageSize = arguments[0]; //jezeli podano argument to przypisz ten rozmiar strony, ktory podano
        Query queryTotal = entityManager.createQuery("Select count(o) from Osoba o");
        long countResult = (long) queryTotal.getSingleResult();
        int pageNumber = (int) ((countResult / pageSize) + 1);
        return pageNumber;
    }

    public ArrayList <Osoba> getAllPeopleByPage(int size, int pagenr) {
        //calculate total number
        Query queryTotal = entityManager.createQuery
                ("Select count(o) from Osoba o");
        long countResult = (long)queryTotal.getSingleResult();

        //create query
        Query query = entityManager.createQuery("Select o from Osoba o");
        //set pageSize
        int pageSize = size;
        //calculate number of pages
        int pageNumber = (int) ((countResult / pageSize) + 1);

        if (pagenr > pageNumber) pagenr = pageNumber;
        query.setFirstResult((pagenr-1) * pageSize);
        query.setMaxResults(pageSize);

        return (ArrayList) query.getResultList();
    }

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
