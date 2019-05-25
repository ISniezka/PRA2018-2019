package hibernate.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by agnzim on 12.04.2019.
 */


@Entity
@Table(name = "pp_czlonkowie")
public class Czlonek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "imie", nullable = false)
    private String firstName;

    @Column(name = "nazwisko", nullable = false)
    private String lastName;

    @Column(name = "klub", nullable = false)
    private int club;

    @Column(name = "dolaczenie", nullable = false)
    private ZonedDateTime joinDate;

 /*   @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="add_id", referencedColumnName = "id")
    Address address;

    @ManyToMany(mappedBy = "subworkers", cascade = CascadeType.ALL)
    private List<Employee> managers = new ArrayList<Employee>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Employee> subworkers = new ArrayList<>();   */

    public Czlonek() {}

    public Czlonek(String firstName, String lastName, int club, ZonedDateTime joinDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.club = club;
        this.joinDate = joinDate;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String first_name ) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String last_name ) {
        this.lastName = last_name;
    }

    public int getClub() {
        return club;
    }

    public void setClub( int club ) {
        this.club = club;
    }

    public ZonedDateTime getJoingDate() {
        return joinDate;
    }

    public void setJoingDate( ZonedDateTime join_date ) {
        this.joinDate = join_date;
    }


    public static Czlonek copyCzlonka(Czlonek osoba) {
        Czlonek person = new Czlonek();
        person.setFirstName(osoba.getFirstName());
        person.setLastName(osoba.getLastName());
        person.setClub(osoba.getClub());
        person.setJoingDate(osoba.getJoingDate());
        return person;
    }
}