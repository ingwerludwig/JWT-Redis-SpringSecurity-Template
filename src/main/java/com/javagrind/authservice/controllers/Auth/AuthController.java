package com.javagrind.authservice.controllers.Auth;

import com.javagrind.authservice.dto.Response;
import com.javagrind.authservice.dto.request.Auth.LoginRequest;
import com.javagrind.authservice.dto.request.Auth.LogoutRequest;
import com.javagrind.authservice.dto.request.User.RegisterRequest;
import com.javagrind.authservice.entity.UserEntity;
import com.javagrind.authservice.handler.BadRequestExceptionHandler;
import com.javagrind.authservice.services.AuthService;
import com.javagrind.authservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<Response<Object>> authenticateUser(@Valid @RequestBody LoginRequest request, BindingResult errors) {
        Response<Object> response;

        if (errors.hasErrors()) {return BadRequestExceptionHandler.handle(errors);
        } else {

            try {
                Object result = authService.login(request);
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
    }

    @PostMapping("/signup")
    public ResponseEntity<Response<Object>> registerUser(@Valid @RequestBody RegisterRequest request, BindingResult errors){
        Response<Object> response;

        if (errors.hasErrors()) {return BadRequestExceptionHandler.handle(errors);
        } else {

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

    @PostMapping("/signout")
    public ResponseEntity<Response<Object>> logoutUser(@Valid @RequestBody LogoutRequest request, BindingResult errors){
        Response<Object> response;

        if (errors.hasErrors()) {return BadRequestExceptionHandler.handle(errors);
        } else {

            try {
                Object result = authService.logout(request);
                response = new Response<>(HttpStatus.OK.value(), Boolean.TRUE, "Logout success", result);
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


}