package page.danya.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public class JwtUser implements UserDetails {





    private final int id;
    private final String firstname;
    private final String lastname;
    private final String middlename;
    private final String password;
    private final String username;
    private final String telnumber;
    private final String email;
    private final boolean banState;
    private final Collection<? extends GrantedAuthority> authorities;


    public JwtUser(
            int id,
            String username,
            String firstName,
            String lastName,
            String middlename,
            String telnumber,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean banState
    ) {
        this.id = id;
        this.username = username;
        this.firstname = firstName;
        this.lastname = lastName;
        this.email = email;
        this.telnumber = telnumber;
        this.middlename = middlename;
        this.password = password;
        this.authorities = authorities;
        this.banState = false;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    @JsonIgnore
    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBanState() {
        return banState;
    }


}
