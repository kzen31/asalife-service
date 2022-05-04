package com.asaproject.asalife.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController extends HandlerController {
//    @Autowired
    private ResponseEntity responseEntity;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(String error, String logout) {
//        if (responseEntity.hasBody()) {
//            return "redirect:/index";
//        }

        if (error != null)
            return ("Your username and password is invalid.");

        if (logout != null)
            return("You have been logged out successfully.");

        return "login";
    }
}
