package com.javagrind.oauth2practice.controllers.Auth;

import com.javagrind.oauth2practice.dto.Response;
import com.javagrind.oauth2practice.dto.request.Auth.LoginRequest;
import com.javagrind.oauth2practice.dto.request.User.RegisterRequest;
import com.javagrind.oauth2practice.entity.UserEntity;
import com.javagrind.oauth2practice.handler.BadRequestExceptionHandler;
import com.javagrind.oauth2practice.repositories.RoleRepository;
import com.javagrind.oauth2practice.repositories.UserRepository;
import com.javagrind.oauth2practice.security.jwt.JwtUtils;
import com.javagrind.oauth2practice.security.services.UserDetailsImpl;
import com.javagrind.oauth2practice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<Response<Object>> authenticateUser(@Valid @RequestBody LoginRequest request, Errors errors) {

        if (errors.hasErrors()) BadRequestExceptionHandler.handle(errors);
        Response<Object> response;

        try {
            Object result = userService.login(request);
            response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Token created successfully", result);
            return ResponseEntity.ok().body(response);
        } catch (HttpClientErrorException.Unauthorized ex) {
            response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (HttpClientErrorException.Forbidden ex) {
            response = new Response<>(HttpStatus.FORBIDDEN.value(), Boolean.FALSE, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception ex) {
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<Response<UserEntity>> registerUser(@Valid @RequestBody RegisterRequest request, Errors errors){
        Response<UserEntity> response;

        if (errors.hasErrors()) BadRequestExceptionHandler.handle(errors);

        try {
            UserEntity result = userService.create(request);
            response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "User registered successfully", result);
            return ResponseEntity.ok().body(response);
        } catch (HttpClientErrorException.Unauthorized ex) {
            response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (HttpClientErrorException.Forbidden ex) {
            response = new Response<>(HttpStatus.FORBIDDEN.value(), Boolean.FALSE, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception ex) {
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, ex.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}