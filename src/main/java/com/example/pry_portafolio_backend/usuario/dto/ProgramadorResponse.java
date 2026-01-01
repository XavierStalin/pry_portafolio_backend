package com.example.pry_portafolio_backend.usuario.dto;

import com.example.pry_portafolio_backend.usuario.entity.Role;

import static com.example.pry_portafolio_backend.usuario.entity.Role.DEV;

public record ProgramadorResponse(
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
