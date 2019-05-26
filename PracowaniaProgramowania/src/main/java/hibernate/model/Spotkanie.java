package hibernate.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by agnzim on 06.05.2019.
 */

@Entity
@Table(name = "pp_spotkania")
public class Spotkanie {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "klub", nullable = false)
    private int clubId;

    @Column(name = "temat", nullable = false)
    private String topic;

    @Column(name = "miejsce", nullable = false)
    private String venue;

    @Column(name = "data_powstania", nullable = false)
    private ZonedDateTime meetingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubId", referencedColumnName = "id")
    private Klub hostedClub;

    @OneToMany(mappedBy = "presenceIn", fetch = FetchType.LAZY)
    private Set<Obecnosc> myMeeting = new HashSet<Obecnosc>();


    public Spotkanie() {}

    public Spotkanie(int clubId, String topic, String venue, ZonedDateTime meetingDate) {
        this.clubId = clubId;
        this.topic = topic;
        this.venue = venue;
        this.meetingDate = meetingDate;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId( int id ) {
        this.clubId = id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue( String venue ) {
        this.venue = venue;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic( String topic ) {
        this.topic = topic;
    }

    public ZonedDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate( ZonedDateTime meeting_date ) {
        this.meetingDate = meeting_date;
    }



    public static Spotkanie copySpotkania(Spotkanie spotkanie) {
        Spotkanie meeting = new Spotkanie();
        meeting.setClubId(spotkanie.getClubId());
        meeting.setVenue(spotkanie.getVenue());
        meeting.setTopic(spotkanie.getTopic());
        meeting.setMeetingDate(spotkanie.getMeetingDate());
        return meeting;
    }
}
