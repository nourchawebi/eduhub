package com.esprit.cloudcraft.entities.userEntities;

import com.esprit.cloudcraft.entities.userEntities.token.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable , UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;

    private String firstName;

    private String lastName;
 //private String priv ="normal";

    private String email;

    private String password;

    private boolean mfaEnabled;
    private boolean notLocker=true;

    private String secret;

    @Temporal(TemporalType.DATE)
   Date birthDate;

    @Enumerated(EnumType.STRING)
    ClassType classType;
    @Enumerated(EnumType.STRING)
    private RoleType role=RoleType.USER;
    private boolean enable;
    @OneToMany(mappedBy = "user")
    private Set<SecureToken> tokens;
    @OneToMany(mappedBy = "user")
    private List<Token> tokensAuth;


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
        return notLocker;//switch it to true or we will not be able to connect our users
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

