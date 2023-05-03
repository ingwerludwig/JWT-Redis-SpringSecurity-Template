package com.javagrind.authservice.services;

import com.javagrind.authservice.dto.request.Auth.LoginRequest;
import com.javagrind.authservice.dto.request.Auth.LogoutRequest;

public interface AuthService {
    Object login(LoginRequest request);
    Object logout(LogoutRequest request);
}
