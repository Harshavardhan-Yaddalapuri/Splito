package com.noobprogrammer.splito.controller;

import com.noobprogrammer.splito.dto.*;
import com.noobprogrammer.splito.model.User;
import com.noobprogrammer.splito.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(@AuthenticationPrincipal User user) {
        try {
            UserProfileDTO profile = userService.getUserProfile(user.getUsername());
            return ResponseEntity.ok(new ApiResponse(true, "Profile retrieved successfully", profile));
        } catch (Exception e) {
            log.error("Error retrieving profile for user {}: {}", user.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateProfileRequest request) {
        try {
            userService.updateProfile(user.getUsername(), request);
            return ResponseEntity.ok(new ApiResponse(true, "Profile updated successfully"));
        } catch (Exception e) {
            log.error("Error updating profile for user {}: {}", user.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangePasswordRequest request) {
        try {
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "New password and confirm password do not match"));
            }

            userService.changePassword(user.getUsername(), request.getCurrentPassword(), request.getNewPassword());
            return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
        } catch (Exception e) {
            log.error("Error changing password for user {}: {}", user.getUsername(), e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchUsers(
            @RequestParam String query,
            @AuthenticationPrincipal User currentUser) {
        try {
            var users = userService.searchUsers(query, currentUser.getUsername());
            return ResponseEntity.ok(new ApiResponse(true, "Users found", users));
        } catch (Exception e) {
            log.error("Error searching users with query {}: {}", query, e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }
}
