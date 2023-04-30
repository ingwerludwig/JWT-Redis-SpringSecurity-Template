package com.javagrind.oauth2practice.services;

import com.javagrind.oauth2practice.entity.UserEntity;

public interface RoleService {
    void changeRole(String email, String role);
}
