package com.esprit.cloudcraft.dto.userdto;

import com.esprit.cloudcraft.entities.userEntities.ClassType;
import lombok.Data;

import java.util.Date;
@Data
public class ChangePersonalInfosdRequest {
    String firstName;
    String lastName;
    Date birthDate;
    String classType;
}
