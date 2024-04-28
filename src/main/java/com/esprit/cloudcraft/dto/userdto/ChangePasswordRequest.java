package com.esprit.cloudcraft.dto.userdto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {
    private  String email;
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}