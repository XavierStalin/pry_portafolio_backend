package com.example.pry_portafolio_backend.usuario;

import com.example.pry_portafolio_backend.entidades_negocio.ProgramadorDetalle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pw_user")

public class Usuario implements UserDetails {

    @Id@ToString.Exclude
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
    @Builder.Default
    private Boolean activo = true;

    @Enumerated(EnumType.STRING)
    private Role rol;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @ToString.Exclude
    private ProgramadorDetalle programadorDetalle;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }
}
