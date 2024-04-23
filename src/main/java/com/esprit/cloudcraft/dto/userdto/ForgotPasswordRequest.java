package com.esprit.cloudcraft.dto.userdto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    String email;
    String newPassword;
    String confirmPassword;
}
