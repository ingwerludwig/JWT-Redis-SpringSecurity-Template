package com.javagrind.authservice.repositories;

import com.javagrind.authservice.entity.RolesEntity;
import com.javagrind.authservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    @Query("SELECT user FROM UserEntity user WHERE user.id= :id")
    UserEntity findUserById(String id);

    @Query("SELECT user FROM UserEntity user WHERE user.email= :email AND user.status = true")
    UserEntity findUserByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserEntity user SET user.status=false WHERE user.email = :email AND user.status=true")
    int deleteUserByEmail(String email);

    @Query("SELECT u.roles FROM UserEntity u WHERE u.id = :userId")
    Set<RolesEntity> findRolesByUserId(String userId);

}
