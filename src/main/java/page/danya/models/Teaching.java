package page.danya.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne()
    @JoinColumn(name = "subject_id")
    private Subject subject;


    @OneToMany(mappedBy = "teaching")
    private List<Mark> marks;

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

    // TODO: Create setters and getters!!!

    public Teaching() {
    }

    public Teaching(Group group, Subject subject, APP_User teacher) {
        this.group = group;
        this.subject = subject;
        this.teacher = teacher;
    }
}
