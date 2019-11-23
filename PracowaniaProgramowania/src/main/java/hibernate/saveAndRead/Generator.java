package hibernate.saveAndRead;

import hibernate.model.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Generator {

    private EntityManager entityManager;
    public void generate() {

        ArrayList<Konto> AccountsList = generateAccounts();
        ArrayList<Osoba> PeopleList = generatePeople(AccountsList);
        ArrayList<Druzyna> TeamsList = generateTeams();
        ArrayList<Pupil> PetsList = generatePets();
        ArrayList<Postac> CharactersList = generateCharacters(TeamsList, PetsList);

        for(Pupil p : PetsList) entityManager.persist(p);
        for(Konto k : AccountsList) entityManager.persist(k);
        for(Osoba o : PeopleList) entityManager.persist(o);
        for(Druzyna d : TeamsList) entityManager.persist(d);
        for(Postac p : CharactersList) entityManager.persist(p);
    }

    public static ArrayList<Konto> generateAccounts() {
        ArrayList<Konto> AccountsList = new ArrayList<>();
        AccountsList.add(new Konto("kubaBor","kubaBor123", ZonedDateTime.now().minusDays(2)));
        AccountsList.add(new Konto("BoTo","BoTo123",ZonedDateTime.now().minusDays(3)));
        AccountsList.add(new Konto("ToKura","ToKura123",ZonedDateTime.now().minusDays(4)));
        AccountsList.add(new Konto("KrGr","KrGr123",ZonedDateTime.now().minusDays(5)));
        return AccountsList;
    }

    public static ArrayList<Osoba> generatePeople(ArrayList<Konto> AccountsList) {
        ArrayList<Osoba> PeopleList = new ArrayList<>();
        PeopleList.add(new Osoba("00240857435","Jakub", "Borewicz", AccountsList.get(0)));
        PeopleList.add(new Osoba("81031971754","Bożena", "Tomaszewicz", AccountsList.get(1)));
        PeopleList.add(new Osoba("92102662558","Tomasz", "Kurowicz", AccountsList.get(2)));
        PeopleList.add(new Osoba("86051865717","Krzysztof", "Grunkiewicz", AccountsList.get(3)));
        PeopleList.add(new Osoba("78090512633","Błażej", "Polewicz"));
        //PeopleList.add(new Osoba("72390512633","Błażeina", "Polewiczowa"));
        return PeopleList;
    }

    public static ArrayList<Druzyna> generateTeams() {
        ArrayList<Druzyna> TeamsList = new ArrayList<>();
        TeamsList.add(new Druzyna("RocketPower"));
        return TeamsList;
    }

    public static ArrayList<Pupil> generatePets() {
        ArrayList<Pupil> PetsList = new ArrayList<>();
        PetsList.add(new Pupil("Okruszek","Pies"));
        return PetsList;
    }

    public static ArrayList<Postac> generateCharacters(ArrayList<Druzyna> TeamsList, ArrayList<Pupil> PetsList) {
        ArrayList<Postac> CharactersList = new ArrayList<>();
        CharactersList.add(new Postac("WalecznaWalkyria",TeamsList.get(0).getTeamName(),1,PetsList.get(0)));
        CharactersList.add(new Postac("JohnRambo",TeamsList.get(0).getTeamName(),2));
        CharactersList.add(new Postac("JamesBond",2));
        CharactersList.add(new Postac("LeeMinHoo",TeamsList.get(0).getTeamName(),4));
        CharactersList.add(new Postac("NaniNani",4));
        CharactersList.add(new Postac("KimSeJin",4));
        return CharactersList;
    }

    public Generator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
