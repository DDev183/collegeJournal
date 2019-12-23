package page.danya.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
//    private int teacher_id;
    private int student_id;
    private Date date;
    private int value;
//    private int Absent_id;

    @ElementCollection(targetClass = typeOfMark.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "typeOfMark", joinColumns = @JoinColumn(name = "typeOfMark_id"))
    private Set<typeOfMark> typeOfMark;


}
