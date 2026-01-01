package com.example.pry_portafolio_backend.usuario.dto;

public record ProgramadorRequest(
        String email,
        String nombre,
        String apellido,
        String especialidad,
        String biografia,
        String telefono,
        String linkLinkedin,
        String linkGithub,
        String fotoPerfilUrl
) {

}
