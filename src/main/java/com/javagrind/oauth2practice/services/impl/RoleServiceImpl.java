package com.javagrind.oauth2practice.services.impl;

import com.javagrind.oauth2practice.entity.Role;
import com.javagrind.oauth2practice.entity.RolesEntity;
import com.javagrind.oauth2practice.entity.UserEntity;
import com.javagrind.oauth2practice.repositories.RoleRepository;
import com.javagrind.oauth2practice.repositories.UserRepository;
import com.javagrind.oauth2practice.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public class RoleServiceImpl implements RoleService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void changeRole(String email, String role) {
        RolesEntity newRole;
        UserEntity reqUser = userRepository.findUserByEmail(email);
        if(reqUser == null){
            System.err.println(new IllegalArgumentException("User not found"));
            throw new IllegalArgumentException("User not found");
        }

        if(role=="admin")
            newRole = roleRepository.findByRole(Role.ROLE_ADMIN);
        else
            newRole = roleRepository.findByRole(Role.ROLE_USER);

        Set<RolesEntity> currentRoles = reqUser.getRoles();
        currentRoles.clear();
        currentRoles.add(newRole);

        reqUser.setRoles(currentRoles);
        userRepository.save(reqUser);
    }
}
