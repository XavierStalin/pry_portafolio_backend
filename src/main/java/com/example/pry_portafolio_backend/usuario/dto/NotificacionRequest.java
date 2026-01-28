package com.example.pry_portafolio_backend.usuario.dto;

import com.example.pry_portafolio_backend.usuario.entity.NotificationType;

public record NotificacionRequest(
        Integer usuarioId,
        NotificationType tipo,
        String mensaje,
        Boolean exitoso
) {
}
