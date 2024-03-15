package com.esprit.cloudcraft.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {
    @GetMapping("/home")
    public String  home()
    {return "this is home page";}
}
