package com.noobprogrammer.splito.service;

import com.noobprogrammer.splito.dto.*;
import com.noobprogrammer.splito.model.User;

import java.util.List;

public interface UserService {

    AuthenticationResponse registerUser(RegistrationRequest request);

    AuthenticationResponse loginUser(AuthenticationRequest request);
    
    UserProfileDTO getUserProfile(String username);
    
    void updateProfile(String username, UpdateProfileRequest request);
    
    void changePassword(String username, String currentPassword, String newPassword);
    
    List<UserProfileDTO> searchUsers(String query, String currentUsername);
    
    User getUserByUsername(String username);
}
