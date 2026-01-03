package com.example.pry_portafolio_backend.asesoria.dto;

import java.time.LocalDateTime;

public record AsesoriaResponse(
        Integer id,
        String clienteEmail,
        String programadorEmail,
        LocalDateTime fechaHoraInicio,
        String motivoConsulta,
        String estado,
        String mensajeRespuesta,
        Integer duracionMinutos,
        String linkReunion
) {
}
