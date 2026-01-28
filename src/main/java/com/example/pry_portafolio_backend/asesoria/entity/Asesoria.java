package com.example.pry_portafolio_backend.asesoria.entity;

import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PW_ASESORIAS")
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "ase_estado")
    private AdvisoryStatus estado = AdvisoryStatus.PENDIENTE;

    @Column(name = "ase_mensaje_respuesta", columnDefinition = "TEXT")
    private String mensajeRespuesta;

    @Builder.Default
    @Column(name = "ase_duracion_minutos")
    private Integer duracionMinutos = 60;

    @Column(name = "ase_link_reunion", length = 512)
    private String linkReunion;

    @CreationTimestamp
    @Column(name = "ase_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "ase_updated_at")
    private LocalDateTime updatedAt;
}