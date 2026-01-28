package com.example.pry_portafolio_backend.usuario.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PW_USUARIOS")
public class Usuario implements UserDetails {

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

    @Column(name = "usu_password", nullable = true, length = 200)
    private String password;

    @CreationTimestamp
    @Column(name = "usu_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "usu_updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Role rol;


    @Enumerated(EnumType.STRING)
    @Column(name = "usu_auth_provider", nullable = false, length = 20)
    private AuthProvider authProvider;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}