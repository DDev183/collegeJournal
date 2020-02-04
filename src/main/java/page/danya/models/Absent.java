package page.danya.models;


import javax.persistence.*;
import java.util.List;

@Entity
public class Absent {
    /*
    present, notPresent, badNotPresent, goodNotPresent

    present- присуствует
    notPresent- отсуствует
    badNotPresent- неуважительный допуск
    goodNotPresent- уважительный допуск

     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(name = "short_name")
    private String shortname;



    @OneToMany(mappedBy = "absent", fetch = FetchType.EAGER)
    private List<Mark> marks;


    //Constructors


    public Absent(String name, String shortname) {
        this.name = name;
        this.shortname = shortname;
    }

    public Absent() {
    }


    //Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }


}
