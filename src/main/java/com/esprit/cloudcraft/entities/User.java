package com.esprit.cloudcraft.entities;

import com.esprit.cloudcraft.Enum.ClassType;
import com.esprit.cloudcraft.Enum.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    private String firstName;
    private String lastName;

    private String email;
    private String password;
    private boolean mfaEnabled;
    private boolean notLocker=true;

   Date birthDate;
    @Enumerated(EnumType.STRING)
    ClassType classType;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    private boolean enable;


//    @OneToMany(mappedBy = "user")
//    private Set<SecureToken> tokens;
   /* @OneToMany(mappedBy = "user")
    private List<Token> tokensAuth;*/



//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }

//
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;//switch it to true or we will not be able to connect our users
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return notLocker;//switch it to true or we will not be able to connect our users
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;//switch it to true or we will not be able to connect our users
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enable;//switch it to true or we will not be able to connect our users
//    }
}

