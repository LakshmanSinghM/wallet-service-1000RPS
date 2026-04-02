package com.example.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseWithErrors<T> extends ApiResponse<T> {
    private Map<String, String> errors;

    public ApiResponseWithErrors(T data, String message, Boolean success, String systemCode, HttpStatus httpCode, Map<String, String> errors) {
        super(data, message, success, systemCode, httpCode);
        this.errors = errors;
    }

    public static <T> ApiResponseWithErrors<T> validationError(String message, Map<String, String> errors) {
        return new ApiResponseWithErrors<>(null, message, false, "VALIDATION_ERROR", HttpStatus.BAD_REQUEST, errors);
    }
}
