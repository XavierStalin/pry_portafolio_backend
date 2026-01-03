package com.example.pry_portafolio_backend.asesoria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record DisponibilidadRequest(
    @NotBlank(message = "El email del programador es obligatorio")
    String programadorEmail,

    @NotBlank(message = "El día de la semana es obligatorio")
    String diaSemana,

    @NotNull(message = "La hora de inicio es obligatoria")
    LocalTime horaInicio,

    @NotNull(message = "La hora de fin es obligatoria")
    LocalTime horaFin,

    @NotBlank(message = "La modalidad es obligatoria")
    String modalidad
) {
}
