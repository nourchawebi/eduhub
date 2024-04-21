package com.esprit.cloudcraft.controller.userController;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping
    @ResponseBody
    public String  home()
    {return "this is home page";}


}
