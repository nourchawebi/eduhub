package com.esprit.cloudcraft.controller;

import ch.qos.logback.core.model.Model;
import com.esprit.cloudcraft.entities.user;
import com.esprit.cloudcraft.services.userService;
import com.esprit.cloudcraft.implement.userImplement;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("user")
public class userController {
    @Resource
    private userService userService;
    @Resource
    private userImplement userimp;

    @PostMapping("register")
    @ResponseBody
    public user register(@RequestBody user user )
    {
   return this.userService.register(user);

    }

    @GetMapping("register/verify")
    public String verifyCustomer(@RequestParam(required = false) String token){


            userimp.verifyUser(token);
            return "verifSuccess";}


}
