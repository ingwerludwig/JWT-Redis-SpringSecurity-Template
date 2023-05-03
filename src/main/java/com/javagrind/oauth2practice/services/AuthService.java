package com.javagrind.oauth2practice.services;

import com.javagrind.oauth2practice.dto.request.Auth.LoginRequest;
import com.javagrind.oauth2practice.dto.request.Auth.LogoutRequest;

public interface AuthService {
    Object login(LoginRequest request);

    Object logout(LogoutRequest request);
}
