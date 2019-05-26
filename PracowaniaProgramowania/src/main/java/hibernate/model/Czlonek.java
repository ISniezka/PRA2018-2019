package hibernate.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.*;

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

    @Column(name = "aktywny")
    private boolean activity;

    @Column(name = "imie", nullable = false)
    private String firstName;

    @Column(name = "nazwisko", nullable = false)
    private String lastName;

    @Column(name = "czlonek_klubu", nullable = false)
    private int club;

    @Column(name = "dolaczenie", nullable = false)
    private ZonedDateTime joinDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Klub myClub;

    @OneToMany(mappedBy = "presenceBy", fetch = FetchType.LAZY)
    private Set<Obecnosc> myMeeting = new HashSet<Obecnosc>();

    public Czlonek() {}

    public Czlonek(boolean activity, String firstName, String lastName, int club, ZonedDateTime joinDate) {
        this.activity = activity;
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

    public boolean getActivity() {
        return activity;
    }

    public void setActivity( boolean activity ) {
        this.activity = activity;
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
        person.setActivity(osoba.getActivity());
        person.setFirstName(osoba.getFirstName());
        person.setLastName(osoba.getLastName());
        person.setClub(osoba.getClub());
        person.setJoingDate(osoba.getJoingDate());
        return person;
    }
}