package com.esprit.cloudcraft.controller.userController;

import com.esprit.cloudcraft.dto.userdto.ChangeEmailRequest;
import com.esprit.cloudcraft.dto.userdto.ChangePasswordRequest;
import com.esprit.cloudcraft.dto.userdto.ChangePersonalInfosdRequest;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.userServices.UserService;
import com.google.zxing.NotFoundException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:4200")
public class simpleConnectedUserController {

    @Resource
    private UserService userService;
 /************** listing all registerd user ********************/
    @GetMapping("allusers")
    @ResponseBody
     public ResponseEntity<List<User>> getAllUsers()
    {

        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            return ResponseEntity.notFound().build(); // Handle empty user list
        }
        return ResponseEntity.ok(users); // Return OK status with user list

    }
    /********************* change the password in the data base ************************/

    @PatchMapping("updatepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request)
    {
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }
    private boolean emailVerified = false;
/*****************  sending a verification email the new email set by the user*********************/
/****************** the verification mail contains the api wish will update and set the new email in the database *************/
    @PatchMapping("updateEmail")
    public ResponseEntity<?> changeEmail(@RequestBody ChangeEmailRequest request, Principal connectedUser)
    {
        userService.changeEmail(request, connectedUser);
        return ResponseEntity.ok().build();

    }
/******************************* the api set in the email sent : to update the new email for the user *************/
    @Async
    @GetMapping("update/email/verify")
    @ResponseBody
    public  ResponseEntity<?> verifyCustomer(@RequestParam(required = false) String token)
    {
        emailVerified = true;
        return ResponseEntity.ok(userService.verifyNewEmail(token));

    }

/******************** change user personal infos(password and email not included ******************************/
 @PatchMapping("updatePersonalData")
    public  ResponseEntity<?> updateUserInfos(@RequestBody ChangePersonalInfosdRequest request, Principal connectedUser)
 {
     return ResponseEntity.ok(userService.changePersonalInfos(request,connectedUser));
 }
}
