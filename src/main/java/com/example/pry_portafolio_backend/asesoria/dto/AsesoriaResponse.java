package com.example.pry_portafolio_backend.asesoria.dto;

import com.example.pry_portafolio_backend.asesoria.entity.AdvisoryStatus;
import java.time.LocalDateTime;

public record AsesoriaResponse(
        Integer id,
        String clienteEmail,
        String programadorEmail,
        LocalDateTime fechaHoraInicio,
        String motivoConsulta,
        AdvisoryStatus estado,
        String mensajeRespuesta,
        Integer duracionMinutos,
        String linkReunion
) {
}
