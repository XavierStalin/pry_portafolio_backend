package com.example.pry_portafolio_backend.asesoria.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AsesoriaRequest(
        @Email(message = "Email de cliente inválido")
        @NotBlank(message = "El email del cliente es obligatorio")
        String clienteEmail,

        @Email(message = "Email de programador inválido")
        @NotBlank(message = "El email del programador es obligatorio")
        String programadorEmail,

        @NotNull(message = "La fecha y hora de inicio es obligatoria")
        LocalDateTime fechaHoraInicio,

        @NotBlank(message = "El motivo de la consulta es obligatorio")
        String motivoConsulta,

        Integer duracionMinutos
) {
}
