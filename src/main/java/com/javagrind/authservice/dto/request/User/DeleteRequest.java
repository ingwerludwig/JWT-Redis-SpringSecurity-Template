package com.javagrind.authservice.dto.request.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class DeleteRequest {
    @NotBlank(message = "email cannot empty")
    @Email(message = "must be a valid email")
    private String email;
}