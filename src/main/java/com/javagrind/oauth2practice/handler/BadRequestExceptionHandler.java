package com.javagrind.oauth2practice.handler;

import com.javagrind.oauth2practice.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public class BadRequestExceptionHandler {
    static Response<Object> response;
    public BadRequestExceptionHandler() {
    }

    public static ResponseEntity<Response<Object>> handle(Errors errors) {

        String errorMessages = errors.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("; "));
        response = new Response<>(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE, errorMessages, null);
        System.err.println(errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }
}
