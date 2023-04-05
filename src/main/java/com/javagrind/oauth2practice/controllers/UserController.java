package com.javagrind.oauth2practice.controllers;

import com.javagrind.oauth2practice.dto.Response;
import com.javagrind.oauth2practice.dto.request.User.DeleteRequest;
import com.javagrind.oauth2practice.dto.request.User.RegisterRequest;
import com.javagrind.oauth2practice.entity.UserEntity;
import com.javagrind.oauth2practice.handler.BadRequestExceptionHandler;
import com.javagrind.oauth2practice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.logging.Logger;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
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

    @PutMapping("/delete")
    public ResponseEntity<Response<UserEntity>> deleteUser(@Valid @RequestBody DeleteRequest request, Errors errors){
        Response<UserEntity> response;

        if (errors.hasErrors()) BadRequestExceptionHandler.handle(errors);

        try {
            String result = userService.delete(request);
            response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Delete "+ result + " successfully", null);
            return ResponseEntity.ok().body(response);
        } catch (HttpClientErrorException.Unauthorized ex) {
            response = new Response<>(HttpStatus.UNAUTHORIZED.value(), Boolean.FALSE, ex.getMessage(), null);
            System.err.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (HttpClientErrorException.Forbidden ex) {
            response = new Response<>(HttpStatus.FORBIDDEN.value(), Boolean.FALSE, ex.getMessage(), null);
            System.err.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception ex) {
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE, ex.getMessage(), null);
            System.err.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
