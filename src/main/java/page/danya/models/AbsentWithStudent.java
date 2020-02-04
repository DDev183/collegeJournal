package page.danya.models;

import java.util.ArrayList;
import java.util.List;

public class AbsentWithStudent{

    private int id;
    private String firstname;
    private String lastname;
    private List<Absent> absentList = new ArrayList<Absent>(2);
    private String date;


    public AbsentWithStudent(int id, String firstname, String lastname, List<Absent> absentList, String date) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.absentList = absentList;
        this.date = date;
    }



    public AbsentWithStudent() {
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

    public List<Absent> getAbsentList() {
        return absentList;
    }

    public void setAbsentList(List<Absent> absentList) {
        this.absentList = absentList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
