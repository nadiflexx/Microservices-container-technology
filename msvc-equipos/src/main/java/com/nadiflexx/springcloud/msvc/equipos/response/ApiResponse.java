package com.nadiflexx.springcloud.msvc.equipos.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    //Nos sirve para mostrar el error en nuestra respuesta de APIREST
    private String message;
    private boolean success;
    private HttpStatus status;
}
