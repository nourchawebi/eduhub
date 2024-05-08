package com.esprit.cloudcraft.entities;


import com.esprit.cloudcraft.entities.userEntities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Feedback {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private Journey journey;

    @ManyToOne
    private User user;

    private int rating;
    private String comment;

    @JsonFormat(pattern="yyyy-MM-dd")
    @CreatedDate
    private Date createdAt;
}
