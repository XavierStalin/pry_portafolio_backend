package com.example.pry_portafolio_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistroUsuarioDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}
