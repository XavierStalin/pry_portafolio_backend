package com.example.pry_portafolio_backend.usuario.dto;

import com.example.pry_portafolio_backend.usuario.entity.Role;

public record UsuarioRequest(
        String nombre,
        String apellido,
        String email,
        String password,
        Role rol
) {
}
