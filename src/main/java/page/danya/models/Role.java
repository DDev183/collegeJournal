package page.danya.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
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

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<APP_User> user;

    public List<APP_User> getUser() {
        return user;
    }

    public void setUser(List<APP_User> user) {
        this.user = user;
    }
}
