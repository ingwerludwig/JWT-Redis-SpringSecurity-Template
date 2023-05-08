package com.javagrind.authservice.services.impl;

import com.javagrind.authservice.dto.request.Auth.LoginRequest;
import com.javagrind.authservice.dto.request.Auth.LogoutRequest;
import com.javagrind.authservice.dto.response.LoginResponse;
import com.javagrind.authservice.dto.response.LogoutResponse;
import com.javagrind.authservice.repositories.RoleRepository;
import com.javagrind.authservice.repositories.UserRepository;
import com.javagrind.authservice.security.jwt.JwtUtils;
import com.javagrind.authservice.security.services.RedisService;
import com.javagrind.authservice.security.services.UserDetailsImpl;
import com.javagrind.authservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RedisService redisService;

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
