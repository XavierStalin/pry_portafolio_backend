package com.example.pry_portafolio_backend.usuario.entity;

import jakarta.persistence.*;
import lombok.*; // Importación de Lombok
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter // Genera todos los getters
@Setter // Genera todos los setters
@NoArgsConstructor // Genera constructor vacío (necesario para JPA)
@AllArgsConstructor // Genera constructor con todos los campos
@Builder // Permite usar el patrón Builder: Notificacion.builder()...build()
@Entity
@Table(name = "PW_NOTIFICACIONES")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "not_usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "not_tipo", nullable = false)
    private NotificationType tipo; // Asegúrate de tener este Enum definido

    @Column(name = "not_mensaje", columnDefinition = "TEXT")
    private String mensaje;

    @CreationTimestamp
    @Column(name = "not_fecha_envio", nullable = false, updatable = false)
    private LocalDateTime fechaEnvio;

    @Builder.Default // Evita que Lombok ignore el valor por defecto al usar @Builder
    @Column(name = "not_exitoso")
    private Boolean exitoso = true;
}