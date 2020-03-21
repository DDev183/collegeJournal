package page.danya.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Person {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnoreProperties
    private int id;

    private String firstname;
    private String lastname;
    private String middlename;
    private String username;
    private String password;
    private String email;
    private String telnumber;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telnumber='" + telnumber + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public Person(int id, String firstname, String lastname, String middlename, String username, String password, String email, String telnumber) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telnumber = telnumber;
    }

    public Person(String firstname, String lastname, String middlename, String username, String password, String email, String telnumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.username = username;
        this.password = password;
        this.email = email;
        this.telnumber = telnumber;
    }

    public Person() {
    }
}
