package com.poit.hibiscus.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/")
@AllArgsConstructor
public class SignInController {
    @GetMapping("signin")
    private ResponseEntity<Void> signIn() {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
