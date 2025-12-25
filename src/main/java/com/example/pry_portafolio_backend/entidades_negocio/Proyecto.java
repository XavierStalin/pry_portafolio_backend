package com.example.pry_portafolio_backend.entidades_negocio;

import com.example.pry_portafolio_backend.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "PW_PROYECTOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pry_id")
    private Integer id;

    // Relacion
    @ManyToOne
    @JoinColumn(name = "pry_programador_id", nullable = false)
    private Usuario programador;

    @Column(name = "pry_categoria", nullable = false, length = 50)
    private String categoria; // academico o laboral

    @Column(name = "pry_nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "pry_descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "pry_tecnologias_usadas", length = 255)
    private String tecnologiasUsadas;

    @Column(name = "pry_url_repositorio", length = 512)
    private String urlRepositorio;

    @Column(name = "pry_url_despliegue", length = 512)
    private String urlDespliegue;

    @Column(name = "pry_url_imagen_preview", length = 512)
    private String urlImagenPreview;

    @CreationTimestamp
    @Column(name = "pry_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "pry_activo", nullable = false)
    @ColumnDefault("true")
    private Boolean activo = true;
}