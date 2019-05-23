package hibernate.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ObecnoscPK implements Serializable {

    @Column(name = "ID_czlonka", nullable = false)
    protected Integer idPerson;

    @Column(name = "ID_spotkania", nullable = false)
    protected Integer idMeeting;

    public ObecnoscPK() {
    }

    public ObecnoscPK(Integer idPerson, Integer idMeeting) {
        this.idPerson = idPerson;
        this.idMeeting = idMeeting;
    }

    public Integer getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Integer idPerson) {
        this.idPerson = idPerson;
    }

    public Integer getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(Integer idMeeting) {
        this.idMeeting = idMeeting;
    }
}