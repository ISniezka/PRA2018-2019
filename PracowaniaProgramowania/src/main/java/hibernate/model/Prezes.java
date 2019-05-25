package hibernate.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by agnzim on 06.05.2019.
 */

@Entity
@Table(name = "pp_prezesi")
public class Prezes {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "ID_prezesa", nullable = false)
    private int presidentId;

    @Column(name = "ID_klubu", nullable = false)
    private int clubId;

    @Column(name = "start", nullable = false)
    private ZonedDateTime cadencyBegin;

    @Column(name = "koniec", nullable = false)
    private ZonedDateTime cadencyEnd;

 /*   @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="add_id", referencedColumnName = "id")
    Address address;

    @ManyToMany(mappedBy = "subworkers", cascade = CascadeType.ALL)
    private List<Employee> managers = new ArrayList<Employee>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Employee> subworkers = new ArrayList<>();   */


    public Prezes() {}

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getPresidentId() {
        return presidentId;
    }

    public void setPresidentId( int id ) { this.presidentId = id; }

    public int getClubId() {
        return clubId;
    }

    public void setClubId( int id ) { this.clubId = id; }

    public ZonedDateTime getcadencyBegin() {
        return cadencyBegin;
    }

    public void setcadencyBegin( ZonedDateTime cadency_begin ) {
        this.cadencyBegin = cadency_begin;
    }

    public ZonedDateTime getcadencyEnd() {
        return cadencyEnd;
    }

    public void setcadencyEnd( ZonedDateTime cadency_end ) {
        this.cadencyEnd = cadency_end;
    }


    public static Prezes copyPrezesa(Prezes prezes) {
        Prezes president = new Prezes();
        president.setPresidentId(prezes.getPresidentId());
        president.setClubId(prezes.getClubId());
        president.setcadencyBegin(prezes.getcadencyBegin());
        president.setcadencyEnd(prezes.getcadencyEnd());
        return president;
    }
}
