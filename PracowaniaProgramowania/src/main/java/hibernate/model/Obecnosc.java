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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMeeting", referencedColumnName = "id")
    private Klub presenceIn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPerson", referencedColumnName = "id")
    private Czlonek presenceBy;

    public Obecnosc() {}

    public Obecnosc(ObecnoscPK obecnoscpk) {
        this.obecnoscpk = obecnoscpk;
    }

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
