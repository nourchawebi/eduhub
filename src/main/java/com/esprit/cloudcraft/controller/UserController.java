package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.services.UserService;
import com.esprit.cloudcraft.implement.UserImplement;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserImplement userimp;

    @PostMapping("register")
    @ResponseBody
    public User register(@RequestBody User user )
    {
   return this.userService.register(user);

    }

    @GetMapping("register/verify")
    public String verifyCustomer(@RequestParam(required = false) String token){


            userimp.verifyUser(token);
            return "verifSuccess";}


}
