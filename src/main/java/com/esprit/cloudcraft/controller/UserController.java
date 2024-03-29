package com.esprit.cloudcraft.controller;


import com.esprit.cloudcraft.dto.AuthenticationRequest;
import com.esprit.cloudcraft.dto.AuthenticationResponse;
import com.esprit.cloudcraft.dto.ForgotPasswordRequest;
import com.esprit.cloudcraft.dto.VerificationRequest;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.repository.UserRepository;
import com.esprit.cloudcraft.services.AuthenticationService;
import com.esprit.cloudcraft.services.UserService;
import com.esprit.cloudcraft.implement.UserImplement;
import jakarta.annotation.Resource;
import jakarta.persistence.Cacheable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller

@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:8081", exposedHeaders = "token")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserRepository userRepository;

    @Resource
    private final AuthenticationService service;
    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody User request )
    {              boolean test=userService.findByEmail(request.getEmail());

        if (userService.findByEmail(request.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(test);
    }
       else{
        var response=userService.register(request);
        if(request.isMfaEnabled())
        { return ResponseEntity.ok(response);}
        else  {
            return ResponseEntity.accepted().build();
        }}


    }


    @GetMapping("register/verify")
    public String verifyCustomer(@RequestParam(required = false) String token){


        userService.verifyUser(token);
        return "verifSuccess";}




    @PostMapping("/login")
    public ResponseEntity<?> authenticate (
            @RequestBody AuthenticationRequest request //RegisterRequest will all the registration information
    ) {


          if (!userService.findByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else

        {
            User user =userService.getByEmail(request.getEmail());
           if(! user.isEnabled())

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User disabled");
           else
               return ResponseEntity.ok(service.authenticate(request));

        }



    }











    /* @PostMapping("/refresh-token")
     public void refreshToken(
             HttpServletRequest request,//the object where we can get or read the authorization header which will hold the refresh token
             HttpServletResponse response//the object that will help us to re-inject or to send back the response
     ) throws IOException {
         service.refreshToken(request,response);
     }*/
    @GetMapping("register/resendToken")
    public String resendtokenToActiveAcount(@RequestParam("email") String email){


        userService.resendToken(email);
        return "emailSent";}
/* these 3 methodes are for forget password */
public String sentmail;

   @GetMapping("login/forgotPassword")

    public ResponseEntity<?>sendForgotPassword(@RequestParam("email") String email){
       if( userService.sendForgotPasswordRequest(email))
       { sentmail=email;

           return ResponseEntity.accepted().build();
       }
       else
           return ResponseEntity.notFound().build();}
   /* @GetMapping("login/changePasswordTemplate")
    public String changepasswordTemplate(@RequestParam(required = false) String token){

        return "changepasswordtemplate";}*/

    @PatchMapping("login/setnewpassword")
    public  ResponseEntity<?> setNewPassword( @RequestBody ForgotPasswordRequest request)
    {
       if(userService.setForgotPassword( request))
       {   return ResponseEntity.accepted().build();}
       else
           return ResponseEntity.notFound().build();
    }
    /* code for mfa */
    @PostMapping("login/verify")
    public ResponseEntity<?> verifyCode(
            @RequestBody VerificationRequest verificationRequest
    ) {
        return ResponseEntity.ok(service.verifyCode(verificationRequest));
    }










}
