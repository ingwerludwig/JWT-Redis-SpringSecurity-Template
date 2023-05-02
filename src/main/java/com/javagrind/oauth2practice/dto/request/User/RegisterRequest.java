package com.javagrind.oauth2practice.dto.request.User;

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

    @NotBlank
    @Size(min = 3, max=20)
    private String username;

    @NotBlank
    @Size(min = 8)
    private String password;

    private Set<String> role;

    @NotBlank
    @Email
    private String email;

}
