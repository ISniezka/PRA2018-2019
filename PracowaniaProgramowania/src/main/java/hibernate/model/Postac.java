package hibernate.model;

import javax.persistence.*;
import java.lang.*;

@Entity
@Table(name = "postacie")
public class Postac<pet> {

    @Id
    @GeneratedValue(generator = "gen2")
    @SequenceGenerator(name = "gen2", sequenceName = "postac_seq")
    @Column(name = "nick", nullable = false)
    private String nick;

    @Column(name = "druzyna")
    private String team;

    @Column(name = "idKonta", nullable = false)
    private int accountID;

    @OneToOne(orphanRemoval = true)//, cascade = CascadeType.MERGE)//, orphanRemoval=true)
    @JoinColumn(name = "idpupila")//, referencedColumnName = "id")
            Pupil pet;

    public Postac() {
    }

    public Postac(String nick, int accountID) {
        this.nick = nick;
        this.accountID = accountID;
        this.team = null;
        this.pet = null;
    }

    public Postac(String nick, String team, int accountID, Pupil pet) {
        this.nick = nick;
        this.team = team;
        this.accountID = accountID;
        this.pet = pet;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Pupil getPet() {
        return pet;
    }

    public void setPet(Pupil pet) {
        this.pet = pet;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public String toString() {

        String cokolwiek;
        if (pet != null) {
            cokolwiek = "Postac{" +
                    "nick='" + nick + '\'' +
                    ", team='" + team + '\'' +
                    ", accountID=" + accountID +
                    ", pet=" + pet.toString() + '}';
        } else {
            cokolwiek = "Postac{" +
                    "nick='" + nick + '\'' +
                    ", team='" + team + '\'' +
                    ", accountID=" + accountID +
                    ", pet= null" + '}';
        }

        return cokolwiek;
    }
}