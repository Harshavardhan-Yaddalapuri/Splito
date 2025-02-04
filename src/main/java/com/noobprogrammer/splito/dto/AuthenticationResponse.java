package com.noobprogrammer.splito.dto;

public record AuthenticationResponse(String token) {
    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
