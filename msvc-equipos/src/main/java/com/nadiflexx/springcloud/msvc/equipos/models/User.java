package com.nadiflexx.springcloud.msvc.equipos.models;


import lombok.*;

@Getter
@Setter
public class User {
    private Long id;
    private String nombre;
    private String email;
    private String password;
}