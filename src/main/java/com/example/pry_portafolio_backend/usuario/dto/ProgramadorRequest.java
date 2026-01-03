package com.example.pry_portafolio_backend.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProgramadorRequest(
        @Email(message = "Email inválido")
        @NotBlank(message = "El email es obligatorio")
        String email,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        @NotBlank(message = "La especialidad es obligatoria")
        String especialidad,

        @NotBlank(message = "La biografía es obligatoria")
        String biografia,

        String telefono,
        String linkLinkedin,
        String linkGithub,
        String fotoPerfilUrl
) {

}
