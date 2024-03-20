package com.esprit.cloudcraft.dto;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    String email;
    String newPassword;
    String confirmPassword;
}
