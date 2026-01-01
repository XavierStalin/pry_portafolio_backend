package com.example.pry_portafolio_backend.usuario.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@Table(name = "PW_PROGRAMADOR_DETALLES")
@NoArgsConstructor
@AllArgsConstructor

public class ProgramadorDetalle {

    @Id
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "det_programador_id")
    private Usuario usuario;

    @Column(name = "det_especialidad", length = 50)
    private String especialidad;

    @Column(name = "det_biografia_breve", columnDefinition = "TEXT")
    private String biografiaBreve;

    @Column(name = "det_telefono", length = 15)
    private String telefono;

    @Column(name = "det_link_linkedin", length = 512)
    private String linkLinkedin;

    @Column(name = "det_link_github", length = 512)
    private String linkGithub;

    @Column(name = "det_foto_perfil_url", length = 512)
    private String fotoPerfilUrl ;

    @Column(name = "det_activo", nullable = false)
    @ColumnDefault("true")
    @Builder.Default
    private Boolean activo = true;

}
