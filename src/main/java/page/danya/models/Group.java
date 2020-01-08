package page.danya.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "grp")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Collection<APP_User> user;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "group_id")
//    private Collection<Teaching> teaching;
//


    public Group() {
    }

    public Group(int id, String name, Collection<APP_User> user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Group(int id) {
        this.id = id;
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

    public Collection<APP_User> getUser() {
        return user;
    }

    public void setUser(Collection<APP_User> user) {
        this.user = user;
    }



}
