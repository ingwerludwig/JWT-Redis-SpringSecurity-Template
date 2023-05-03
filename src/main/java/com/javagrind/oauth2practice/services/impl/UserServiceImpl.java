package com.javagrind.oauth2practice.services.impl;

import com.javagrind.oauth2practice.dto.request.User.DeleteRequest;
import com.javagrind.oauth2practice.dto.request.User.RegisterRequest;
import com.javagrind.oauth2practice.dto.request.User.UpdateUserRequest;
import com.javagrind.oauth2practice.entity.Role;
import com.javagrind.oauth2practice.entity.RolesEntity;
import com.javagrind.oauth2practice.entity.UserEntity;
import com.javagrind.oauth2practice.repositories.RoleRepository;
import com.javagrind.oauth2practice.repositories.UserRepository;
import com.javagrind.oauth2practice.security.jwt.JwtUtils;
import com.javagrind.oauth2practice.security.services.RedisService;
import com.javagrind.oauth2practice.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RedisService redisService;

    @Override
    @Transactional()
    public UserEntity create(RegisterRequest request) {

        RolesEntity newRole;
        if(userRepository.findUserByEmail(request.getEmail()) != null){
            System.err.println(new IllegalArgumentException("Email has been taken"));
            throw new IllegalArgumentException("Email has been taken");
        }

        UserEntity userEntity = new UserEntity(request.getEmail(), encoder.encode(request.getPassword()), request.getUsername());
        userEntity.setStatus(Boolean.TRUE);

        if (request.getEmail().contains("@admin.org"))
            newRole = roleRepository.findByRole(Role.ROLE_ADMIN);
        else
            newRole = roleRepository.findByRole(Role.ROLE_USER);

        Set<RolesEntity> currentRoles = userEntity.getRoles();
        currentRoles.add(newRole);
        userEntity.setRoles(currentRoles);
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
    @Transactional()
    public UserEntity update(String id,UpdateUserRequest request) {

        UserEntity requestedUser = userRepository.findUserById(id);
        if(requestedUser != null) {
            modelMapper.map(request, requestedUser);
            UserEntity savedEntity = userRepository.save(requestedUser);
            return savedEntity;
        }else{
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    @Transactional()
    public String delete(DeleteRequest request) {
        UserEntity deletedUser = userRepository.findUserByEmail(request.getEmail());
        int result = userRepository.deleteUserByEmail(request.getEmail());

        if(result > 0)  return deletedUser.getEmail();
        else throw new NoSuchElementException("User not found");
    }

}