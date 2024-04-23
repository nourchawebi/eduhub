package com.esprit.cloudcraft.dto.userdto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
