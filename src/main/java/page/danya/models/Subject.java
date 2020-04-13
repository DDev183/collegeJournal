package page.danya.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

@Entity
@Indexed
public class Subject {

    @Field
    private int memory;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "subject")
    private List<Teaching> teachings;

    public Subject(){
    }

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject(String name) {
        this.name = name;
    }

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
}
