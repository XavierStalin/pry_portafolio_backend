package com.example.pry_portafolio_backend.usuario.dto;

import com.example.pry_portafolio_backend.usuario.entity.NotificationType;
import java.time.LocalDateTime;

public record NotificacionResponse(
        Integer id,
        Integer usuarioId,
        String usuarioEmail,
        NotificationType tipo,
        String mensaje,
        LocalDateTime fechaEnvio,
        Boolean exitoso
) {
}
