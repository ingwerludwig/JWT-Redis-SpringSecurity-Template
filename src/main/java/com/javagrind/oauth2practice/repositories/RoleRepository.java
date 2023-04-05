package com.javagrind.oauth2practice.repositories;

import com.javagrind.oauth2practice.entity.RolesEntity;
import com.javagrind.oauth2practice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RolesEntity, String> {

    Optional<RolesEntity> findByRole(Role role);

}