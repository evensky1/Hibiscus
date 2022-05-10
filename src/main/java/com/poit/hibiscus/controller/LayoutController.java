package com.poit.hibiscus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LayoutController {

    @GetMapping("signup")
    public String getSignUpPage() {
        return "signUp";
    }

    @GetMapping("signin")
    public String getSignInPage() {
        return "signIn";
    }

    @GetMapping("mainPage")
    public String getMainPage() {
        return "mainPage";
    }
}
