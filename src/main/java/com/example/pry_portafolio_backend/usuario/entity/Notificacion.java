package com.example.pry_portafolio_backend.usuario.entity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "PW_NOTIFICACIONES")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "not_usuario_id", nullable = false)
    private Usuario usuario; // Who received it

    @Column(name = "not_tipo", length = 20)
    private String tipo; // EMAIL, WHATSAPP

    @Column(name = "not_mensaje", columnDefinition = "TEXT")
    private String mensaje;

    @CreationTimestamp
    @Column(name = "not_fecha_envio", nullable = false, updatable = false)
    private LocalDateTime fechaEnvio;

    @Column(name = "not_exitoso")
    private Boolean exitoso = true;

    public Notificacion() {}

    public Notificacion(Integer id, Usuario usuario, String tipo, String mensaje, LocalDateTime fechaEnvio, Boolean exitoso) {
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.exitoso = exitoso;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    public Boolean getExitoso() { return exitoso; }
    public void setExitoso(Boolean exitoso) { this.exitoso = exitoso; }
}
