package com.example.pry_portafolio_backend.asesoria.entity;

import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "PW_DISPONIBILIDADES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dsp_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "dsp_programador_id", nullable = false)
    private Usuario programador;

    @Column(name = "dsp_dia_semana", nullable = false, length = 20)
    private String diaSemana;

    @Column(name = "dsp_hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "dsp_hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "dsp_modalidad", nullable = false, length = 50)
    private String modalidad; // virtual o presencial
}