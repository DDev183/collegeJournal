package page.danya.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDate date;

    private String description;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "teaching_id")
    private Teaching teaching;


    @JsonIgnore
    @OneToMany(mappedBy = "lesson")
    private List<Mark> marks;

    public Lesson() {
    }

    public Lesson(LocalDate date, Teaching teaching) {
        this.date = date;
        this.teaching = teaching;
    }

    public Lesson(int id, LocalDate date, String description, Teaching teaching, List<Mark> marks) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.teaching = teaching;
        this.marks = marks;
    }

    public Lesson(LocalDate date, String description, Teaching teaching, List<Mark> marks) {
        this.date = date;
        this.description = description;
        this.teaching = teaching;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teaching getTeaching() {
        return teaching;
    }

    public void setTeaching(Teaching teaching) {
        this.teaching = teaching;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }
}
