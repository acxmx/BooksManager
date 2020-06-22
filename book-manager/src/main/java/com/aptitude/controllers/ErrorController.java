package com.aptitude.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping(value = "/404Page")
    public String notFound1 () {
        return "404";
    }

}
