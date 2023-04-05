package com.javagrind.oauth2practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private Integer statusCode;
    private Boolean success;
    private String message;
    private T data;
}
