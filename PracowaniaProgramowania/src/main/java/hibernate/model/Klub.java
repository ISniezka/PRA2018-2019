package hibernate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by agnzim on 06.05.2019.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "pp_kluby")
public class Klub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "nazwa", nullable = false)
    private String clubName;

    @Column(name = "data_powstania", nullable = false)
    private ZonedDateTime openingDate;

    @Column(name = "zalozyciel", nullable = false)
    private int founder;

    @OneToOne(cascade = CascadeType.PERSIST)//, orphanRemoval = true)
    @JoinColumn(name="founder", referencedColumnName = "id")
    Czlonek founder2;

    //@OneToMany(mappedBy = "myClub", fetch = FetchType.LAZY, cascade=CascadeType.ALL) //cascade = CascadeType.ALL, orphanRemoval = true
    @OneToMany(mappedBy = "myClub", fetch = FetchType.EAGER, cascade=CascadeType.ALL) // <dodano 08-09-2019>
    private Set<Czlonek> clubMember = new HashSet<Czlonek>();

    //@OneToMany(mappedBy = "hostedClub", fetch = FetchType.LAZY, cascade=CascadeType.ALL) //cascade = CascadeType.ALL, orphanRemoval = true
    @OneToMany(mappedBy = "hostedClub", fetch = FetchType.EAGER, cascade=CascadeType.ALL) //<dodano 08-09-2019>
    private Set<Spotkanie> meeting = new HashSet<Spotkanie>();

    public Klub() {}

    public Klub(String clubName, ZonedDateTime openingDate, int founder) {
        this.clubName = clubName;
        this.openingDate = openingDate;
        this.founder = founder;
    }

    public Klub(String clubName, ZonedDateTime openingDate, int founder, Czlonek founder2) {
        this.clubName = clubName;
        this.openingDate = openingDate;
        this.founder = founder;
        this.founder2 = founder2;
    }

    public void addMemberToTheClub(Czlonek c) {
        clubMember.add(c);
    }

    public void addClubMeeting(Spotkanie s){
        meeting.add(s);
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName( String club_name ) {
        this.clubName = club_name;
    }

    public ZonedDateTime getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate( ZonedDateTime opening_date ) {
        this.openingDate = opening_date;
    }

    public int getFounder() {
        return founder;
    }

    public void setFounder( int founder ) {
        this.founder = founder;
    }


    @Override
    public String toString() {
        return "Klub{" +
                "id=" + id +
                ", clubName='" + clubName + '\'' +
                ", openingDate=" + openingDate +
                ", founder=" + founder +'}';
    }
}