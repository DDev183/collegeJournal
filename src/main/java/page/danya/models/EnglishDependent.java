package page.danya.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class EnglishDependent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @JsonIgnore
    @OneToMany(mappedBy = "englishDependent")
    private List<APP_User> students;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private APP_User teacher;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;


    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "group_id")
    private Group group;

    public EnglishDependent() {
    }

    public EnglishDependent(APP_User teacher, Subject subject, Group group) {
        this.teacher = teacher;
        this.subject = subject;
        this.group = group;
    }

    public EnglishDependent(int id) {
        this.id = id;
    }

    public EnglishDependent(List<APP_User> students, APP_User teacher, Subject subject, Group group) {
        this.students = students;
        this.teacher = teacher;
        this.subject = subject;
        this.group = group;
    }

    public List<APP_User> getStudents() {
        return students;
    }

    public void setStudents(List<APP_User> students) {
        this.students = students;
    }

    public APP_User getTeacher() {
        return teacher;
    }

    public void setTeacher(APP_User teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}

