package com.javagrind.oauth2practice.repositories;

import com.javagrind.oauth2practice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

    @Query("SELECT user FROM UserEntity user WHERE user.email= :email AND user.status = true")
    public UserEntity findUserByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserEntity user SET user.status=false WHERE user.email = :email AND user.status=true")
    public int deleteUserByEmail(String email);
}
