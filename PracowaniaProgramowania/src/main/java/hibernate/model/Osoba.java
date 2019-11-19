package hibernate.model;

import javax.persistence.*;
import java.lang.*;

@Entity
@Table(name = "osoby")
public class Osoba {

    @Id
    @GeneratedValue(generator = "gen3")
    @SequenceGenerator(name = "gen3", sequenceName = "osoba_seq")
    @Column(name = "pesel", nullable = false)
    private String pesel;

    @Column(name = "imie", nullable = false)
    private String firstName;

    @Column(name = "nazwisko", nullable = false)
    private String lastName;

    @OneToOne(orphanRemoval=true)//, cascade = CascadeType.MERGE)//, orphanRemoval = true)
    @JoinColumn(name="idkonta")//, referencedColumnName = "id")
    Konto account;

    public Osoba() {}

    public Osoba(String pesel, String firstName, String lastName) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = null;
    }

    public Osoba(String pesel, String firstName, String lastName, Konto account) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
    }

    public String getPesel() {return pesel;}
    public void setPesel(String pesel) { this.pesel = pesel; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Konto getAccount() { return account; }
    public void setAccount(Konto account) { this.account = account; }

    @Override
    public String toString() {
        String cokolwiek;
        if (account != null)
        {
         cokolwiek = "Osoba{" +
                "pesel=" + pesel +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", account='" + account.toString() + '}';}
        else {
            cokolwiek = "Osoba{" +
                "pesel=" + pesel +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", account= null'" +'}';}

        return cokolwiek;
    }
}
