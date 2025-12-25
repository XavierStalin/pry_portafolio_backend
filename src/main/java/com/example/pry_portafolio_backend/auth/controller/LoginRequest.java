package com.example.pry_portafolio_backend.auth.controller;

public record LoginRequest(
        String email,
        String password
) {
}
