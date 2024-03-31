package com.esprit.cloudcraft.entities.userEntities.token;

import com.esprit.cloudcraft.entities.userEntities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "longtext")
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;


    private boolean expired;
    private boolean revoked;
    private Timestamp createdAt ;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
}
