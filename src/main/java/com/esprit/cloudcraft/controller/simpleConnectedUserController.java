package com.esprit.cloudcraft.controller;


import com.esprit.cloudcraft.dto.ChangeEmailRequest;
import com.esprit.cloudcraft.dto.ChangePasswordRequest;
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
        CompletableFuture<Boolean> verificationFuture = CompletableFuture.supplyAsync(() -> {
            synchronized (this) {
                try {
                    // Wait until emailVerified is true or until interrupted
                    while (!emailVerified) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }
            return emailVerified;
        });


        // Wait for verification to complete
        boolean verificationResult = verificationFuture.join();


        if (verificationResult) {

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Async
    @GetMapping("update/email/verify")
    public ResponseEntity<String> verifyCustomer(@RequestParam(required = false) String token) {
        emailVerified = true;
        boolean verificationResult = userService.verifyNewEmail(token);
        if (verificationResult) {
            emailVerified = true;
            synchronized (this) {
                this.notify(); // Notify waiting thread in changeEmail method
            }
            return ResponseEntity.ok("Verification successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed.");
        }
    }


}
