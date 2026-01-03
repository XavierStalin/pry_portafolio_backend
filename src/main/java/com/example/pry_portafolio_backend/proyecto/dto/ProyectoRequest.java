package com.example.pry_portafolio_backend.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProyectoRequest(
        @Email(message = "Email del programador inválido")
        @NotBlank(message = "El email del programador es obligatorio")
        String programadorEmail,

        @NotBlank(message = "La categoría es obligatoria")
        String categoria,

        @NotBlank(message = "El nombre del proyecto es obligatorio")
        String nombre,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

        String tecnologiasUsadas,
        String urlRepositorio,
        String urlDespliegue,
        String urlImagenPreview
) {
}
