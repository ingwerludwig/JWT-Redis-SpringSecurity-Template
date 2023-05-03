package com.javagrind.oauth2practice.services;

import com.javagrind.oauth2practice.dto.request.Auth.LoginRequest;
import com.javagrind.oauth2practice.dto.request.User.DeleteRequest;
import com.javagrind.oauth2practice.dto.request.User.RegisterRequest;
import com.javagrind.oauth2practice.dto.request.User.UpdateUserRequest;
import com.javagrind.oauth2practice.entity.UserEntity;

public interface UserService {
    UserEntity create(RegisterRequest request);

    UserEntity findByEmail(String requestedEmail);

    UserEntity update(String id,UpdateUserRequest updateRequest);

    String delete(DeleteRequest request);
}
