package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.dto.AuthenticationRequest;
import com.esprit.cloudcraft.dto.AuthenticationResponse;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.services.AuthenticationService;
import com.esprit.cloudcraft.services.UserService;
import com.esprit.cloudcraft.implement.UserImplement;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081", exposedHeaders = "token")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserImplement userimp;
    @Resource
    private final AuthenticationService service;

    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request )
    {
        return ResponseEntity.ok(userService.register(request));


    }


    @GetMapping("register/verify")
    public String verifyCustomer(@RequestParam(required = false) String token){


            userimp.verifyUser(token);
            return "verifSuccess";}



/*
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    )
    {
        return ResponseEntity.ok(service.authenticate(request));
    }*/
@PostMapping("/login")
public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request //RegisterRequest will all the registration information
) {
    return ResponseEntity.ok(service.authenticate(request));
}




}
