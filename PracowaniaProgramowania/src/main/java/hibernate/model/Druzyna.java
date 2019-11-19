package hibernate.model;

import javax.persistence.*;
import java.lang.*;
import java.util.*;

/**
 * Created by agnzim on 12.11.2019.
 */

@Entity
@Table(name = "druzyny")
public class Druzyna {

    @Id
    @GeneratedValue(generator = "gen5")
    @SequenceGenerator(name = "gen5", sequenceName = "pupil5_seq")
    @Column(name = "nazwa", nullable = false)
    private String teamName;

    @Column(name = "punkty", nullable = false)
    private int points;

    @OneToMany(orphanRemoval=true)//, cascade = CascadeType.MERGE)//, orphanRemoval=true) //, cascade = CascadeType.ALL) //<dodano 08-09-2019>
    @JoinColumn(name = "druzyna")//, referencedColumnName = "druzyna")
    private Set<Postac> teamMembers  = new HashSet<Postac>();

    public Druzyna() {}

    public Druzyna(String teamName) {
        this.teamName = teamName;
        this.points = 0;
    }

    public Druzyna(String teamName, int points) { //tego konstruktora nie uzywac!!
        this.teamName = teamName;
        this.points = points;
    }

    public Druzyna(String teamName, int points, Set<Postac> teamMembers) {
        this.teamName = teamName;
        this.points = points;
        this.teamMembers = teamMembers;
    }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    @Override
    public String toString() {
        return "Druzyna{" +
                "teamName='" + teamName + '\'' +
                ", points=" + points +
                '}';
    }
}
