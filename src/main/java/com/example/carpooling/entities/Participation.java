package com.example.carpooling.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Participation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer participationId;
    private String response;
    @JsonFormat(pattern="yyyy-MM-dd")
    @CreatedDate
    private Date participationDate;
    @JsonIgnore
    @ManyToOne
    private User carpooled;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Journey journey;
}
