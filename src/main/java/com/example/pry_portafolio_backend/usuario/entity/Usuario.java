package com.example.pry_portafolio_backend.usuario.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

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

    @Column(name = "usu_password", nullable = false, length = 200)
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

    public Usuario() {}

    public Usuario(Integer id, String nombre, String apellido, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt, Role rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.rol = rol;
    }

    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

    public static class UsuarioBuilder {
        private String nombre;
        private String apellido;
        private String email;
        private String password;
        private Role rol;

        public UsuarioBuilder nombre(String nombre) { this.nombre = nombre; return this; }
        public UsuarioBuilder apellido(String apellido) { this.apellido = apellido; return this; }
        public UsuarioBuilder email(String email) { this.email = email; return this; }
        public UsuarioBuilder password(String password) { this.password = password; return this; }
        public UsuarioBuilder rol(Role rol) { this.rol = rol; return this; }
        public Usuario build() {
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setEmail(email);
            u.setPassword(password);
            u.setRol(rol);
            return u;
        }
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Role getRol() { return rol; }
    public void setRol(Role rol) { this.rol = rol; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
