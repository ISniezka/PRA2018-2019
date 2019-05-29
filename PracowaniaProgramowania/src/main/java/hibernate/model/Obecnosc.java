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
    private Spotkanie presenceDuring;

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

    public Spotkanie getPresenceDuring() {
        return presenceDuring;
    }

    public void setPresenceDuring(Spotkanie presenceDuring) {
        this.presenceDuring = presenceDuring;
    }

    public Czlonek getPresenceBy() {
        return presenceBy;
    }

    public void setPresenceBy(Czlonek presenceBy) {
        this.presenceBy = presenceBy;
    }

    public int getMeeting(){
        return this.obecnoscpk.getIdMeeting();
    }

    public int getPerson(){
        return this.obecnoscpk.getIdPerson();
    }

    @Override
    public String toString() {
        return "Obecnosc{" +
                "obecnoscpk=" + obecnoscpk +'}';
    }
}
