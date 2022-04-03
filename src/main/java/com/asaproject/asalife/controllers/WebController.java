package com.asaproject.asalife.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController extends HandlerController {
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }
}
