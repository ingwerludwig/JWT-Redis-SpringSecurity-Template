package com.javagrind.authservice.services.impl;

import com.javagrind.authservice.dto.request.User.DeleteRequest;
import com.javagrind.authservice.dto.request.User.RegisterRequest;
import com.javagrind.authservice.dto.request.User.UpdateUserRequest;
import com.javagrind.authservice.entity.Role;
import com.javagrind.authservice.entity.RolesEntity;
import com.javagrind.authservice.entity.UserEntity;
import com.javagrind.authservice.repositories.RoleRepository;
import com.javagrind.authservice.repositories.UserRepository;
import com.javagrind.authservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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