package hibernate.model;

import javax.persistence.*;
import java.lang.*;
import java.time.ZonedDateTime;
import java.util.*;

@Entity//(name = "Konto")
@Table(name = "konta")
public class Konto {

    @Id
    @GeneratedValue(generator = "gen4")
    @SequenceGenerator(name = "gen4", sequenceName = "konto_seq")
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "haslo", nullable = false)
    private String password;

    @Column(name = "dataZalozenia", nullable = false)
    private ZonedDateTime creationDate;

    @OneToMany //( cascade = CascadeType.MERGE) //<dodano 08-09-2019>
    @JoinColumn(name = "idKonta")//, referencedColumnName = "idKonta")
    private Set<Postac> myCharacters = new HashSet<Postac>();

    public Konto() {}
    public Konto(String login, String password, ZonedDateTime creationDate) {
        this.login = login;
        this.password = password;
        this.creationDate = creationDate;
        this.myCharacters = null;
    }

    public Konto(String login, String password, ZonedDateTime creationDate, Set<Postac> myCharacters) {
        this.login = login;
        this.password = password;
        this.creationDate = creationDate;
        this.myCharacters = myCharacters;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public ZonedDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(ZonedDateTime creationDate) { this.creationDate = creationDate; }

    @Override
    public String toString() {
        return "Konto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
