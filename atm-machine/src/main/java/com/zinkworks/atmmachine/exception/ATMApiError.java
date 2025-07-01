package com.zinkworks.atmmachine.exception;

import org.springframework.http.HttpStatus;

public record ATMApiError(HttpStatus status, String message) { }