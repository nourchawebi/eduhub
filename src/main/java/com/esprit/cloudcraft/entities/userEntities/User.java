package com.esprit.cloudcraft.entities.userEntities;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Car;
import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.Location;
import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.userEntities.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @Temporal(TemporalType.DATE)
    @Column( nullable = true)
    private Date LockedDate;
    @Temporal(TemporalType.DATE)
    @Column( nullable = true)
    private Date uLockedDate;

    private String secret;
    @Column(nullable = true)
    private String picture;
    @Temporal(TemporalType.DATE)
   Date birthDate;
    @Temporal(TemporalType.DATE)

    @Column(name = "created_date"/*, nullable = false, updatable = false*/)
    private Date createdDate = new Date();
    @Enumerated(EnumType.STRING)
    ClassType classType;
    @Enumerated(EnumType.STRING)
    private RoleType role=RoleType.USER;
    private boolean enable;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<SecureToken> tokens;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokensAuth;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Book> books;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Participation> participations;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "motorized",fetch = FetchType.EAGER)
    private List<Journey> journeys;

    @OneToOne
    private Location location;

    @JsonIgnore
    @OneToMany
    private List<Car> cars;



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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return notLocker;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}

