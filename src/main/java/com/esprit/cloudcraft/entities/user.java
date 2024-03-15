package com.esprit.cloudcraft.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class user implements Serializable , UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id ;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private boolean mfaEnabled; //multifactor authentication
    private String secret;
    Date birthDate;
    @Enumerated(EnumType.STRING)
    classType classType;
    @Enumerated(EnumType.STRING)
    private roleType role;
    private boolean enable;
    @OneToMany(mappedBy = "user")
    private Set<SecureToken> tokens;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;//switch it to true or we will not be able to connect our users
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;//switch it to true or we will not be able to connect our users
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;//switch it to true or we will not be able to connect our users
    }

    @Override
    public boolean isEnabled() {
        return enable;//switch it to true or we will not be able to connect our users
    }
}

