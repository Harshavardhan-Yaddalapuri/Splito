package com.noobprogrammer.splito.controller;

import com.noobprogrammer.splito.dto.LoginRequest;
import com.noobprogrammer.splito.model.User;
import com.noobprogrammer.splito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody LoginRequest loginRequest){
        User registeredUser = userService.registerUser(loginRequest);
        return ResponseEntity.ok(registeredUser);
    }
}
