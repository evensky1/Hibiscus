package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.error.factory.configuration.HandleError;
import com.poit.hibiscus.error.factory.model.SignInException;
import com.poit.hibiscus.repository.UserRepository;
import com.poit.hibiscus.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @HandleError
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new SignInException(String.format("User with email: %s wasn't found", email))
        );

        return new UserDetailsImpl(user);
    }
}
