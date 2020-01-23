package page.danya.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class typeOfMark {
    // everyDay, mediumScore, finalScore;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;


    @OneToMany(mappedBy = "typeOfMark")
    private List<Mark> mark;



    public typeOfMark() {
    }


    //TODO: Add setters and getters
}
