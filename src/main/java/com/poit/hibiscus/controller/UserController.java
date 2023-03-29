package com.poit.hibiscus.controller;

import com.poit.hibiscus.dto.PassportDto;
import com.poit.hibiscus.entity.User;
import com.poit.hibiscus.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConversionService conversionService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("passport")
    public ResponseEntity<PassportDto> getPassport(
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        var currentUser = userService.findUserByEmail(userDetails.getUsername());

        var passportDto = conversionService.convert(currentUser.getPassport(), PassportDto.class);

        return new ResponseEntity<>(passportDto, HttpStatus.OK);
    }
}
