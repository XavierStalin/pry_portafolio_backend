package com.example.pry_portafolio_backend.entidades_negocio;

import com.example.pry_portafolio_backend.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "PW_PROGRAMADOR_DETALLES")
@NoArgsConstructor
@AllArgsConstructor

public class ProgramadorDetalle {

    @Id
    @Column(name = "det_id")
    private Integer id;

    @OneToOne
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

    @Override

    public String toString() {
        return "ProgramadorDetalle{" +
                "especialidad='" + especialidad + '\'' +
                ", biografiaBreve='" + biografiaBreve + '\'' +
                ", telefono='" + telefono + '\'' +
                ", linkLinkedin='" + linkLinkedin + '\'' +
                ", linkGithub='" + linkGithub + '\'' +
                ", id=" + id +
                '}';
    }
}
