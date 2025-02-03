package com.noobprogrammer.splito.controller;

import com.noobprogrammer.splito.dto.AuthenticationRequest;
import com.noobprogrammer.splito.dto.AuthenticationResponse;
import com.noobprogrammer.splito.dto.RegistrationRequest;
import com.noobprogrammer.splito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegistrationRequest loginRequest){
        AuthenticationResponse response = userService.registerUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest loginRequest){
        AuthenticationResponse response = userService.loginUser(loginRequest);
        return ResponseEntity.ok(response);
    }
}
