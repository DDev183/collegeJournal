package page.danya.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;


@Entity
public class Role implements GrantedAuthority {
//    USER, TEACHER, ADMIN;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

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

    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }


    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<APP_User> users;

    public List<APP_User> getUsers() {
        return users;
    }

    public void setUsers(List<APP_User> users) {
        this.users = users;
    }
}
