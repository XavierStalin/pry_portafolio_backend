package com.example.pry_portafolio_backend.proyecto.dto;

public record ProyectoResponse(
        Integer id,
        String programadorEmail,
        String categoria,
        String nombre,
        String descripcion,
        String tecnologiasUsadas,
        String urlRepositorio,
        String urlDespliegue,
        String urlImagenPreview,
        Boolean activo
) {
}
