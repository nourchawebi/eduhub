package com.esprit.cloudcraft.controller.userController;

import com.esprit.cloudcraft.services.userServices.AdminService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {
    @Resource
    private AdminService adminService;
    /******************** lock user api *************/
    @PatchMapping("/lock")
    public ResponseEntity<?> lockUser(@RequestParam("email") String email)
    {
        if (adminService.lockUser(email))
        {
            return ResponseEntity.accepted().build();
        } else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to lock user. User may already be locked or not found.");
        }
    }
/******************* unlock user api ***********************/
    @PatchMapping("/unlock")
    public ResponseEntity<?> unlockUser(@RequestParam("email") String email)
    {
        if (adminService.unlockUser(email))
        {
            return ResponseEntity.accepted().build();
        } else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to lock user. User may already be locked or not found.");
        }
    }

}
