package hibernate.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by agnzim on 06.05.2019.
 */

@Entity
@Table(name = "pp_obecnosc")
public class Obecnosc implements Serializable {

    @EmbeddedId
    private ObecnoscPK obecnoscpk;

      /*   @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="add_id", referencedColumnName = "id")
    Address address;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="add_id", referencedColumnName = "id")
    Address address;  */

    public Obecnosc() {}

    public ObecnoscPK getObecnoscpk() {
        return obecnoscpk;
    }

    public void setObecnoscpk(ObecnoscPK obecnoscpk) {
        this.obecnoscpk = obecnoscpk;
    }

    public static Obecnosc copyObecnosci(Obecnosc obecnosc) {
        Obecnosc presence = new Obecnosc();
        presence.obecnoscpk = obecnosc.obecnoscpk;
        return presence;
    }
}
