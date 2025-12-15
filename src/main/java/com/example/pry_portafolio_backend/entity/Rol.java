package com.example.pry_portafolio_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PW_ROLES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Integer id;

    @Column(name = "rol_nombre", nullable = false, length = 50)
    private String nombre;

}
