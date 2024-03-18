package com.esprit.cloudcraft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangeEmailRequest {
    private String currentEmail;
    private String newEmail;
    private String confirmationEmail;

}
