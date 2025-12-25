package com.example.pry_portafolio_backend.usuario;

public record UsuarioResponse(
        String nombre,
        String apellido,
        String email,
        String password
) {
}
