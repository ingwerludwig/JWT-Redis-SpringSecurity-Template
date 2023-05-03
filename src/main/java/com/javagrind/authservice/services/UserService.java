package com.javagrind.authservice.services;

import com.javagrind.authservice.dto.request.User.DeleteRequest;
import com.javagrind.authservice.dto.request.User.RegisterRequest;
import com.javagrind.authservice.dto.request.User.UpdateUserRequest;
import com.javagrind.authservice.entity.UserEntity;

public interface UserService {
    UserEntity create(RegisterRequest request);
    UserEntity findByEmail(String requestedEmail);
    UserEntity update(String id,UpdateUserRequest updateRequest);
    String delete(DeleteRequest request);
}
