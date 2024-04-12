package com.esprit.cloudcraft.controller.userController;

import com.esprit.cloudcraft.dto.userdto.ChangeEmailRequest;
import com.esprit.cloudcraft.dto.userdto.ChangePasswordRequest;
import com.esprit.cloudcraft.dto.userdto.ChangePersonalInfosdRequest;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.userServices.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
@CrossOrigin(origins="*")
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class simpleConnectedUserController {

    @Resource
    private UserService userService;
 /************** listing all registerd user ********************/
    @GetMapping("allusers")
    @ResponseBody
     public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }
    /********************* change the password in the data base ************************/

    @PatchMapping("updatePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser)
    {
        userService.changePassword(request, connectedUser);
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
