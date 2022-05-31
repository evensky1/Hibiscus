package com.poit.hibiscus.service;

import com.poit.hibiscus.entity.Passport;
import com.poit.hibiscus.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> saveUser(User user);
    void updateUser(String email, Passport passport);
    User findUserById(Long id);
    User findUserByEmail(String email);
    List<User> getUsers();
}
