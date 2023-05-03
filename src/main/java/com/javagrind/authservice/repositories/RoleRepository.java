package com.javagrind.authservice.repositories;

import com.javagrind.authservice.entity.RolesEntity;
import com.javagrind.authservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RolesEntity, String> {

    @Query("SELECT r FROM RolesEntity r WHERE r.roleName = :roleName")
    RolesEntity findByRole(Role roleName);

}