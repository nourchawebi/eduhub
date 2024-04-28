package com.esprit.cloudcraft.module;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Content implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long contentId;

private String contentTitle;
    @Column( length = 100000 )
private String contentDescription;

@Enumerated(EnumType.STRING)
private ContentCategory contentCategory;

@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
private List<FileEntity> files;


}
