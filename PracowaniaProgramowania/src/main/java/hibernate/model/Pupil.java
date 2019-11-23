package hibernate.model;

import javax.persistence.*;
import java.lang.*;

/**
 * Created by agnzim on 12.11.2019.
 */

@Entity
@Table(name = "pupile")
public class Pupil {

    @Id
    @GeneratedValue(generator = "gen")
    @SequenceGenerator(name = "gen", sequenceName = "pupil_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "imie", nullable = false)
    private String name;

    @Column(name = "typ", nullable = false)
    private String animalType;

    @OneToOne(mappedBy = "pet")
    Postac postac;


    public Pupil() {}

    public Pupil(String name, String animalType) {
        this.name = name;
        this.animalType = animalType;
    }



    public int getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAnimalType() { return animalType; }
    public void setAnimalType(String animalType) { this.animalType = animalType; }

    @Override
    public String toString() {
        return "Pupil{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", animalType='" + animalType + '\'' +
                '}';
    }
}
