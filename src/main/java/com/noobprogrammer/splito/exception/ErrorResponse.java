package com.noobprogrammer.splito.exception;

public record ErrorResponse (int status, String message, long timestamp) {
}
