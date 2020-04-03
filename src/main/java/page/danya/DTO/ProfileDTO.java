package page.danya.DTO;

import page.danya.models.APP_User;
import page.danya.models.Role;

import java.util.List;

public class ProfileDTO {

    private String username;
    private String firstname;
    private String lastname;
    private String middlename;
    private String role;
    private String email;
    private String telnumber;
    private String group;
    private boolean banstate;

    public APP_User toUser(){
        APP_User user = new APP_User();
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setMiddlename(middlename);
        user.setEmail(email);
        user.setTelnumber(telnumber);
        user.setBanstate(banstate);

        return user;
    }

    public static ProfileDTO fromUser(APP_User user){
        ProfileDTO dto = new ProfileDTO();

        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setMiddlename(user.getMiddlename());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().get(0).getName());  //BE CAREFULLY
        dto.setTelnumber(user.getTelnumber());
        dto.setGroup(user.getGroup().getName());
        dto.setBanstate(user.isBanstate());
        dto.setUsername(user.getUsername());

        return dto;
    }


    public ProfileDTO() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isBanstate() {
        return banstate;
    }

    public void setBanstate(boolean banstate) {
        this.banstate = banstate;
    }
}
