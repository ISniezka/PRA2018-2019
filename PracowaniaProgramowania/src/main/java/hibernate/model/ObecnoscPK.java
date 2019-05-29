package hibernate.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ObecnoscPK implements Serializable {

    @Column(name = "ID_czlonka", nullable = false)
    protected Integer idPerson;

    @Column(name = "ID_spotkania", nullable = false)
    protected Integer idMeeting;

    public ObecnoscPK() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObecnoscPK)) return false;
        ObecnoscPK that = (ObecnoscPK) o;
        return Objects.equals(getIdMeeting(), that.getIdMeeting()) && Objects.equals(getIdPerson(), that.getIdPerson());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdMeeting(), getIdPerson());
    }

    @Override
    public String toString() {
        return "ObecnoscPK{" +
                "idPerson=" + idPerson +
                ", idMeeting=" + idMeeting +
                '}';
    }
}