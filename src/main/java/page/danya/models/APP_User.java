package page.danya.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usr")
public class APP_User implements UserDetails {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String firstname;
    private String lastname;
    private String middlename;

    @JsonIgnore
    private String password;
    private String username;
    private String telnumber;
    private String email;
    private boolean banState;

    public APP_User(@NotNull String firstname, @NotNull String lastname, String middlename, @NotNull String password, @NotNull String username, String telnumber, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.password = password;
        this.username = username;
        this.telnumber = telnumber;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public APP_User(String firstname, String lastname, String password, String username, String telnumber, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = lastname;       //WARNING!!!
        this.password = password;
        this.username = username;
        this.telnumber = telnumber;
        this.email = email;
    }

    public APP_User(){

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        this.firstname = name;
    }

    //
    //
    //




//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_id")
//    private Role role;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "group_id")
    private Group group;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<Mark> mark;


    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private List<Teaching> teachings;

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public boolean isBanstate() {
        return banState;
    }

    public void setBanstate(boolean banstate) {
        this.banState = banstate;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public List<Role> getRole() {
        return roles;
    }

    public void setRole(List<Role> roles) {
        this.roles = roles;
    }


    public List<Teaching> getTeachings() {
        return teachings;
    }

    public void setTeachings(List<Teaching> teachings) {
        this.teachings = teachings;
    }

    public List<Mark> getMark() {
        return mark;
    }

    public void setMark(List<Mark> mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "APP_User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", group=" + group +
                ", role=" + roles.toString() +
                '}';
    }
}
