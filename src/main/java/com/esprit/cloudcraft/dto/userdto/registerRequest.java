package com.esprit.cloudcraft.dto.userdto;

import com.esprit.cloudcraft.entities.userEntities.ClassType;
import com.esprit.cloudcraft.entities.userEntities.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class registerRequest {
    private String firstName;
    private String lastName;

    MultipartFile picture;
    private String email;
    private String password;
    private boolean mfaEnabled; //multifactor authentication
    private String secret;
    Date birthDate;
    @Enumerated(EnumType.STRING)
    ClassType classType;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    private boolean enable;
}
