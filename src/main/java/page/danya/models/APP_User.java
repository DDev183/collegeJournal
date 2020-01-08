package page.danya.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.springframework.boot.devtools.restart.AgentReloader.isActive;

@Entity
@Table(name = "usr")
public class APP_User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    private String middlename;
    @NotNull
    private String password;
    @NotNull
    private String username;
    private String telNumber;
    private String email;
    private boolean banState;


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

    public APP_User(String firstname, String lastname, String password, String username, String telNumber, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = lastname;       //WARNING!!!
        this.password = password;
        this.username = username;
        this.telNumber = telNumber;
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




    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToOne(optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Group group;


//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private Collection<Teaching> teachings;
//

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public boolean isBanState() {
        return banState;
    }

    public void setBanState(boolean banState) {
        this.banState = banState;
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
        return getRoles();

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

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Override
    public String toString() {
        return "APP_User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", group=" + group +
                '}';
    }
}
