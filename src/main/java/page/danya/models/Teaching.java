package page.danya.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

//    @ManyToOne(optional = false, cascade=CascadeType.ALL)
//    @JoinColumn(name = "group_id")
//    private Group group;

//    @ManyToOne(optional = false, cascade = CascadeType.ALL)
//    private Subject subject;
//
//    @OneToMany
//    private Collection<Mark> marks;
//

    //    private int teacher_id;
//    @ManyToOne(optional = true, cascade = CascadeType.ALL)
//    @JoinColumn(name = "teacher_id")
//    private APP_User teacher;


}
