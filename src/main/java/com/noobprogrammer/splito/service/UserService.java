package com.noobprogrammer.splito.service;

import com.noobprogrammer.splito.dto.AuthenticationRequest;
import com.noobprogrammer.splito.dto.AuthenticationResponse;
import com.noobprogrammer.splito.dto.RegistrationRequest;


public interface UserService {

    AuthenticationResponse registerUser(RegistrationRequest request);

    AuthenticationResponse loginUser(AuthenticationRequest request);

}
