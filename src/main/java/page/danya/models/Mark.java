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

//    private int teaching_id;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Teaching teaching;

//    private int student_id;
    private Date date;
//    private int value;


//    private int Absent_id;
    @ElementCollection(targetClass = Absent.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "absent", joinColumns = @JoinColumn(name = "mark_id"))
    @Enumerated(EnumType.STRING)
    private Set<Absent> absents;


    @ElementCollection(targetClass = typeOfMark.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "typeOfMark", joinColumns = @JoinColumn(name = "mark_id"))
    @Enumerated(EnumType.STRING)
    private Set<typeOfMark> typeOfMarks;




}
