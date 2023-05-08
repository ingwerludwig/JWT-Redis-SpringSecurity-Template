package com.javagrind.authservice.dto.request.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import java.util.Set;

@Getter
@Setter
@Validated
public class RegisterRequest {

    @NotBlank(message = "username cannot empty")
    @Size(min = 3, max=20, message = "username only between 3 and 20 character")
    private String username;

    @NotBlank(message = "password cannot empty")
    @Size(min = 8, message = "password minimal length is 8 character")
    private String password;

    private Set<String> role;

    @NotBlank(message = "email cannot empty")
    @Email(message = "must be a valid email")
    private String email;

}
