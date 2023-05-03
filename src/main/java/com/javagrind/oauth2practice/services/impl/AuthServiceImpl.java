package com.javagrind.oauth2practice.services.impl;

import com.javagrind.oauth2practice.dto.request.Auth.LoginRequest;
import com.javagrind.oauth2practice.dto.request.Auth.LogoutRequest;
import com.javagrind.oauth2practice.dto.response.LoginResponse;
import com.javagrind.oauth2practice.dto.response.LogoutResponse;
import com.javagrind.oauth2practice.repositories.RoleRepository;
import com.javagrind.oauth2practice.repositories.UserRepository;
import com.javagrind.oauth2practice.security.jwt.JwtUtils;
import com.javagrind.oauth2practice.security.services.RedisService;
import com.javagrind.oauth2practice.security.services.UserDetailsImpl;
import com.javagrind.oauth2practice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RedisService redisService;

    @Override
    public Object login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        redisService.store(jwt);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//        List<Object> data = Arrays.asList(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(),roles);

        return new LoginResponse(jwt,userDetails.getUsername());
    }

    @Override
    public Object logout(LogoutRequest request){

        if(redisService.isThere(request.getEmail())!=null){
            redisService.destroyToken(request.getEmail());
        }

        return new LogoutResponse(request.getEmail());
    }
}
