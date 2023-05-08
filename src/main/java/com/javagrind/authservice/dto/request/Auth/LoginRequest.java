package com.javagrind.authservice.dto.request.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class LoginRequest {

    @NotBlank(message = "password cannot empty")
    @Size(min = 8, message = "password minimal length is 8 character")
    private String password;

    @NotBlank(message = "email cannot empty")
    @Email(message = "must be a valid email")
    private String email;
}
