package com.example.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;
    private String message;
    private Boolean success;
    private String systemCode;
    private HttpStatus httpCode;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message, true, "SUCCESS", HttpStatus.OK);
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status, String systemCode) {
        return new ApiResponse<>(null, message, false, systemCode, status);
    }
}
