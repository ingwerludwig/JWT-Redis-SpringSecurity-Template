package com.javagrind.authservice.dto.request.User;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateUserRequest {
    private String oauth_str;
    private String email;
    private String password;
    private String username;
    private String name;
    private LocalDate dob;
    private String phone_num;
}
