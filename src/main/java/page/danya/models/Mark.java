package page.danya.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Set;

@Entity
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


//    private int student_id;
//    private Date date;
    private int value;

    private LocalDate date;
    private LocalTime time;


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    //    private int Absent_id;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "absent_id")
    private Absent absent;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "typeOfMark_id")
    private typeOfMark typeOfMark;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id")
    private APP_User student;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;


    public Mark() {
    }

    public Mark(Absent absent, APP_User student, Lesson lesson, LocalTime time, LocalDate date) {
        this.absent = absent;
        this.student = student;
        this.lesson = lesson;
        this.time = time;
        this.date = date;
    }

    public Mark(Absent absent, APP_User student, Lesson lesson) {
        this.absent = absent;
        this.student = student;
        this.lesson = lesson;
    }



    public Mark(int id, int value, Absent absent, page.danya.models.typeOfMark typeOfMark, APP_User student, Lesson lesson) {
        this.id = id;
        this.value = value;
        this.absent = absent;
        this.typeOfMark = typeOfMark;
        this.student = student;
        this.lesson = lesson;
    }

    public Mark(int value, Absent absent, page.danya.models.typeOfMark typeOfMark, APP_User student, Lesson lesson) {
        this.value = value;
        this.absent = absent;
        this.typeOfMark = typeOfMark;
        this.student = student;
        this.lesson = lesson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Absent getAbsent() {
        return absent;
    }

    public void setAbsent(Absent absent) {
        this.absent = absent;
    }

    public page.danya.models.typeOfMark getTypeOfMark() {
        return typeOfMark;
    }

    public void setTypeOfMark(page.danya.models.typeOfMark typeOfMark) {
        this.typeOfMark = typeOfMark;
    }

    public APP_User getStudent() {
        return student;
    }

    public void setStudent(APP_User student) {
        this.student = student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }



}
