package com.example.pry_portafolio_backend.entidades_negocio;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "PW_USUARIOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Integer id;

    @Column(name = "usu_nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "usu_apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "usu_email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "usu_password", nullable = false, length = 200)
    private String password;

    @Column(name = "usu_foto_perfil_url", length = 512)
    private String foto_perfil_url ;

    @CreationTimestamp
    @Column(name = "usu_created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(name = "usu_activo", nullable = false)
    @ColumnDefault("true")
    private Boolean activo = true;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "usu_rol_id", nullable = false)
    private Rol rol;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private ProgramadorDetalle programadorDetalle;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                //", password='" + password + '\'' +
                ", foto_perfil_url='" + foto_perfil_url + '\'' +
                ", created_at=" + created_at +
                ", activo=" + activo +
                '}';
    }
}
