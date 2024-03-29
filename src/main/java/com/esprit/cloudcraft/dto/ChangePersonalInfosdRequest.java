package com.esprit.cloudcraft.dto;

import com.esprit.cloudcraft.entities.ClassType;
import lombok.Data;

import java.util.Date;
@Data
public class ChangePersonalInfosdRequest {
 String firstName;
    String lastName;


    Date birthDate;
    ClassType classType;
}
