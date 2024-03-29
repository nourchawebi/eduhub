package com.esprit.cloudcraft.controller;


import com.esprit.cloudcraft.dto.ChangeEmailRequest;
import com.esprit.cloudcraft.dto.ChangePasswordRequest;
import com.esprit.cloudcraft.dto.ChangePersonalInfosdRequest;
import com.esprit.cloudcraft.services.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class simpleConnectedUserController {
    Logger logger = LoggerFactory.getLogger(simpleConnectedUserController.class);
    @Resource
    private UserService userService;
    @PatchMapping("updatePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    private boolean emailVerified = false;

    @PatchMapping("updateEmail")
    public ResponseEntity<?> changeEmail(
            @RequestBody ChangeEmailRequest request,
            Principal connectedUser
    ) {
        // Start the email change process
        userService.changeEmail(request, connectedUser);
        return ResponseEntity.ok().build();

    }

    @Async
    @GetMapping("update/email/verify")
    public  ResponseEntity<?> verifyCustomer(@RequestParam(required = false) String token) {
        emailVerified = true;


            emailVerified = true;
        return ResponseEntity.ok(userService.verifyNewEmail(token));

        }

/* chnage personal infos request*/
 @PatchMapping("updatePersonalData")
    public  ResponseEntity<?> updateUserInfos(
            @RequestBody ChangePersonalInfosdRequest request
         , Principal connectedUser)
 {
     return ResponseEntity.ok(userService.changePersonalInfos(request,connectedUser));
 }
}
