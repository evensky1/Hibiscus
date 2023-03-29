package com.poit.hibiscus.service;

import com.poit.hibiscus.entity.Passport;
import com.poit.hibiscus.entity.User;

public interface RegistrationService {
    void saveUser(User user);

    void savePassport(Passport passport);
}
