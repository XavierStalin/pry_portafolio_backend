package com.example.pry_portafolio_backend.asesoria.entity;

import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "ase_estado")
    private AdvisoryStatus estado = AdvisoryStatus.PENDIENTE;

    @Column(name = "ase_mensaje_respuesta", columnDefinition = "TEXT")
    private String mensajeRespuesta;

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

    public Asesoria() {}

    public Asesoria(Integer id, Usuario cliente, Usuario programador, LocalDateTime fechaHoraInicio, String motivoConsulta, AdvisoryStatus estado, String mensajeRespuesta, Integer duracionMinutos, String linkReunion, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.cliente = cliente;
        this.programador = programador;
        this.fechaHoraInicio = fechaHoraInicio;
        this.motivoConsulta = motivoConsulta;
        this.estado = estado;
        this.mensajeRespuesta = mensajeRespuesta;
        this.duracionMinutos = duracionMinutos;
        this.linkReunion = linkReunion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Usuario getCliente() { return cliente; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }
    public Usuario getProgramador() { return programador; }
    public void setProgramador(Usuario programador) { this.programador = programador; }
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }
    public String getMotivoConsulta() { return motivoConsulta; }
    public void setMotivoConsulta(String motivoConsulta) { this.motivoConsulta = motivoConsulta; }
    public AdvisoryStatus getEstado() { return estado; }
    public void setEstado(AdvisoryStatus estado) { this.estado = estado; }
    public String getMensajeRespuesta() { return mensajeRespuesta; }
    public void setMensajeRespuesta(String mensajeRespuesta) { this.mensajeRespuesta = mensajeRespuesta; }
    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public String getLinkReunion() { return linkReunion; }
    public void setLinkReunion(String linkReunion) { this.linkReunion = linkReunion; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}