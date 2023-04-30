package com.javagrind.oauth2practice.repositories;

import com.javagrind.oauth2practice.entity.RolesEntity;
import com.javagrind.oauth2practice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RolesEntity, String> {

    @Query("SELECT r FROM RolesEntity r WHERE r.roleName = :roleName")
    RolesEntity findByRole(Role roleName);

}