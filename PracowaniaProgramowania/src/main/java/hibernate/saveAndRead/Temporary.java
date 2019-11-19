package hibernate.saveAndRead;

import hibernate.model.*;

import java.util.ArrayList;

public class Temporary {

    private ArrayList <Druzyna> TeamsList;
    private ArrayList <Konto> AccountsList;
    private ArrayList <Osoba> PeopleList;
    private ArrayList <Postac> CharactersList;
    private ArrayList <Pupil> PetsList;

    public Temporary() { }

    public Temporary(ArrayList<Druzyna> teamsList, ArrayList<Osoba> peopleList, ArrayList<Postac> charactersList) {
        TeamsList = teamsList;
        PeopleList = peopleList;
        CharactersList = charactersList;
    }

    public Temporary(ArrayList<Druzyna> teamsList, ArrayList<Konto> accountsList, ArrayList<Osoba> peopleList, ArrayList<Postac> charactersList, ArrayList<Pupil> petsList) {
        TeamsList = teamsList;
        AccountsList = accountsList;
        PeopleList = peopleList;
        CharactersList = charactersList;
        PetsList = petsList;
    }

    public ArrayList<Druzyna> getTeamsList() { return TeamsList; }
    public void setTeamsList(ArrayList<Druzyna> teamsList) { TeamsList = teamsList; }

    public ArrayList<Konto> getAccountsList() { return AccountsList; }
    public void setAccountsList(ArrayList<Konto> accountsList) { AccountsList = accountsList; }

    public ArrayList<Osoba> getPeopleList() { return PeopleList; }
    public void setPeopleList(ArrayList<Osoba> peopleList) { PeopleList = peopleList; }

    public ArrayList<Postac> getCharactersList() { return CharactersList; }
    public void setCharactersList(ArrayList<Postac> charactersList) { CharactersList = charactersList; }

    public ArrayList<Pupil> getPetsList() { return PetsList; }
    public void setPetsList(ArrayList<Pupil> petsList) { PetsList = petsList; }
}
