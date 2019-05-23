package hibernate.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by agnzim on 06.05.2019.
 */

@Entity
@Table(name = "pp_obecnosc")
public class Obecnosc {


 /*   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private int id; */

    @Column(name = "ID_czlonka", nullable = false)
    private int idPerson;

    @Column(name = "ID_spotkania", nullable = false)
    private int idMeeting;


 /*   @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="add_id", referencedColumnName = "id")
    Address address;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="add_id", referencedColumnName = "id")
    Address address;

 */

    public Obecnosc() {}

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson( int id ) {
        this.idPerson = id;
    }

    public int getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting( int id ) {
        this.idMeeting = id;
    }


    public static Obecnosc copyObecnosci(Obecnosc obecnosc) {
        Obecnosc presence = new Obecnosc();
        presence.setIdPerson(obecnosc.getIdPerson());
        presence.setIdMeeting(obecnosc.getIdMeeting());
        return presence;
    }
}
