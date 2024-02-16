package com.nadiflexx.springcloud.msvc.users.msvcusuarios.exceptions;


import com.nadiflexx.springcloud.msvc.users.msvcusuarios.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerDataNotFoundException(DataNotFoundException dataNotFoundException) {
        ApiResponse apiResponse = new ApiResponse().builder()
                .message(dataNotFoundException.getMessage())
                .success(true)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
