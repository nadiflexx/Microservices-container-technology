package com.nadiflexx.springcloud.msvc.users.msvcusuarios.exceptions;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }
}
