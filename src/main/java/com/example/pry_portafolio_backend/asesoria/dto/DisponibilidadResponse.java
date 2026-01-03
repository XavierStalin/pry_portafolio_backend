package com.example.pry_portafolio_backend.asesoria.dto;

import java.time.LocalTime;

public record DisponibilidadResponse(
    Integer id,
    String programadorEmail,
    String diaSemana,
    LocalTime horaInicio,
    LocalTime horaFin,
    String modalidad
) {
}
