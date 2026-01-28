package com.example.pry_portafolio_backend.auth.controller;

import jakarta.validation.constraints.NotBlank;

public record FirebaseLoginRequest(
        @NotBlank String idToken
) {}