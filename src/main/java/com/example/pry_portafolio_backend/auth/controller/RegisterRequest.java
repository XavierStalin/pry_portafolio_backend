package com.example.pry_portafolio_backend.auth.controller;

public record RegisterRequest(
        String email,
        String password,
        String nombre,
        String apellido
) {
}
