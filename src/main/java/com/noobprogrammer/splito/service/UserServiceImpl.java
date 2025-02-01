package com.noobprogrammer.splito.service;

import com.noobprogrammer.splito.dto.AuthenticationRequest;
import com.noobprogrammer.splito.dto.AuthenticationResponse;
import com.noobprogrammer.splito.dto.RegistrationRequest;
import com.noobprogrammer.splito.model.User;
import com.noobprogrammer.splito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public AuthenticationResponse registerUser(RegistrationRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }
        User user = new User();
        user.setFirstName(request.fname());
        user.setLastName(request.lname());
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return new AuthenticationResponse("User registered successfully");
    }

    @Override
    public AuthenticationResponse loginUser(AuthenticationRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.username());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return new AuthenticationResponse("Logged in successfully");
    }

}
