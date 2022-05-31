package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.entity.Passport;
import com.poit.hibiscus.entity.User;
import com.poit.hibiscus.error.factory.configuration.HandleError;
import com.poit.hibiscus.error.factory.model.SignInException;
import com.poit.hibiscus.error.factory.model.SignUpException;
import com.poit.hibiscus.repository.UserRepository;
import com.poit.hibiscus.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @HandleError
    public Optional<User> saveUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new SignUpException("Current email already exists in database");
        }
        return Optional.of(userRepository.save(user));
    }

    @Override
    public void updateUser(String email, Passport passport) {
        userRepository.updateUser(email, passport);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new SignInException("Email not found"));
    }

    @Override
    @HandleError
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new SignInException("Email not found"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
