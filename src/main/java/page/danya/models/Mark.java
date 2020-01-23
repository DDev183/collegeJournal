package page.danya.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;

@Entity
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


//    private int student_id;
    private Date date;
//    private int value;


//    private int Absent_id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "absent_id")
    private Absent absent;

    @ManyToOne()
    @JoinColumn(name = "teaching_id")
    private Teaching teaching;

    @ManyToOne
    @JoinColumn(name = "typeOfMark_id")
    private typeOfMark typeOfMark;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private APP_User student;









}
