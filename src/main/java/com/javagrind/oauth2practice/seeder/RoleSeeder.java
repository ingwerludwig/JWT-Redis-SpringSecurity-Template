package com.javagrind.oauth2practice.seeder;
import com.javagrind.oauth2practice.entity.Role;
import com.javagrind.oauth2practice.entity.RolesEntity;
import com.javagrind.oauth2practice.repositories.RoleRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RoleSeeder {
    private RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void seedRoles() {
        if (roleRepository.count() == 0) {
            for (Role role : Role.values()) {
                roleRepository.save(new RolesEntity(role));
            }
        }
    }
}