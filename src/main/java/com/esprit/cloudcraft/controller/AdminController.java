package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.dto.AuthenticationResponse;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.services.AdminService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;
    @PatchMapping("/lock")
    public ResponseEntity<?> lockUser(@RequestParam("email") String email) {
        if (adminService.lockUser(email)) {
            return ResponseEntity.status(HttpStatus.OK).body("User locked successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to lock user. User may already be locked or not found.");
        }
    }

    @PatchMapping("/unlock")
    public ResponseEntity<?> unlockUser(@RequestParam("email") String email) {
        if (adminService.unlockUser(email)) {
            return ResponseEntity.status(HttpStatus.OK).body("User unlocked successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to lock user. User may already be locked or not found.");
        }
    }

}
