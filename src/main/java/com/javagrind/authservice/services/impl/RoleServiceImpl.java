package com.javagrind.authservice.services.impl;

import com.javagrind.authservice.entity.Role;
import com.javagrind.authservice.entity.RolesEntity;
import com.javagrind.authservice.entity.UserEntity;
import com.javagrind.authservice.repositories.RoleRepository;
import com.javagrind.authservice.repositories.UserRepository;
import com.javagrind.authservice.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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
