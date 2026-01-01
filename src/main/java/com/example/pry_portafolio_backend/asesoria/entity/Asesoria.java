package com.example.pry_portafolio_backend.asesoria.entity;

import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "PW_ASESORIAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Asesoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ase_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ase_cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "ase_programador_id", nullable = false)
    private Usuario programador;

    @Column(name = "ase_fecha_hora_inicio", nullable = false)
    private LocalDateTime fechaHoraInicio;

    @Column(name = "ase_motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(name = "ase_estado", length = 50)
    @ColumnDefault("'PENDIENTE'")
    private String estado = "PENDIENTE";

    @Column(name = "ase_mensaje_respuesta", columnDefinition = "TEXT")
    private String mensajeRespuesta;

    @Column(name = "ase_duracion_minutos")
    @ColumnDefault("60")
    private Integer duracionMinutos = 60;

    @Column(name = "ase_link_reunion", length = 512)
    private String linkReunion;

    @CreationTimestamp
    @Column(name = "ase_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}