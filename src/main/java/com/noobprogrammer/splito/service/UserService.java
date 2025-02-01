package com.noobprogrammer.splito.service;

import com.noobprogrammer.splito.dto.LoginRequest;
import com.noobprogrammer.splito.model.User;

import java.util.Optional;

public interface UserService {

    User registerUser(LoginRequest user);

    Optional<User> findByEmail(String email);
}
