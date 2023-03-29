package com.poit.hibiscus.controller;

import com.poit.hibiscus.dto.PassportDto;
import com.poit.hibiscus.dto.UserDto;
import com.poit.hibiscus.entity.Passport;
import com.poit.hibiscus.entity.User;
import com.poit.hibiscus.service.RegistrationService;
import com.poit.hibiscus.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account/")
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final ConversionService conversionService;
    private final RegistrationService registrationService;

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void signUp(@RequestBody UserDto userDto) {
        var user = conversionService.convert(userDto, User.class);
        registrationService.saveUser(user);
    }

    @PostMapping("passport")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void passportData(
            @RequestBody PassportDto passportDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        var passport = conversionService.convert(passportDto, Passport.class);
        registrationService.savePassport(passport);

        userService.updateUser(userDetails.getUsername(), passport);
    }
}
