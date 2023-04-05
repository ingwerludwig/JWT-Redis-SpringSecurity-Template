package com.javagrind.oauth2practice.services.impl;

import com.javagrind.oauth2practice.dto.request.User.DeleteRequest;
import com.javagrind.oauth2practice.dto.request.User.RegisterRequest;
import com.javagrind.oauth2practice.entity.UserEntity;
import com.javagrind.oauth2practice.repositories.UserRepository;
import com.javagrind.oauth2practice.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    @Transactional()
    public UserEntity create(RegisterRequest request) {
        if(userRepository.findUserByEmail(request.getEmail()) != null){
            System.err.println(new IllegalArgumentException("Email has been taken"));
            throw new IllegalArgumentException("Email has been taken");
        }

        UserEntity userEntity = new UserEntity(request.getEmail(), request.getPassword(), request.getUsername());
        userEntity.setStatus(Boolean.TRUE);
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity findByEmail(String requestedEmail) {
        UserEntity requestedUser = userRepository.findUserByEmail(requestedEmail);

        try {
            return requestedUser;
        }catch(Exception e){
            System.err.println(e);
            return null;
        }
    }

    @Override
    public UserEntity update(String requestedIdUser) {
        return null;
    }

    @Override
    @Transactional()
    public String delete(DeleteRequest request) {
        UserEntity deletedUser = userRepository.findUserByEmail(request.getEmail());
        int result = userRepository.deleteUserByEmail(request.getEmail());

        if(result > 0)  return deletedUser.getEmail();
        else throw new NoSuchElementException("Result not found");
    }

}
