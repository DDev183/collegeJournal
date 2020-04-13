package page.danya.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @JsonIgnore
    @OneToMany(mappedBy = "teaching")
    private List<Lesson> lessons;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "teacher_id")
    private APP_User teacher;

//    @OneToMany
//    private Collection<Mark> marks;
//

    //    private int teacher_id;
//    @ManyToOne(optional = true, cascade = CascadeType.ALL)
//    @JoinColumn(name = "teacher_id")
//    private APP_User teacher;


    public Teaching() {
    }

    public Teaching(Group group, Subject subject, APP_User teacher, List<Lesson> lessons) {
        this.group = group;
        this.subject = subject;
        this.teacher = teacher;
        this.lessons = lessons;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public APP_User getTeacher() {
        return teacher;
    }

    public void setTeacher(APP_User teacher) {
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
