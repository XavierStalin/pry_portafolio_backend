package com.example.pry_portafolio_backend.usuario.service;

import com.example.pry_portafolio_backend.usuario.dto.ProgramadorRequest;
import com.example.pry_portafolio_backend.usuario.dto.ProgramadorResponse;
import com.example.pry_portafolio_backend.usuario.dto.UsuarioResponse;
import com.example.pry_portafolio_backend.usuario.entity.ProgramadorDetalle;
import com.example.pry_portafolio_backend.usuario.entity.Role;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import com.example.pry_portafolio_backend.usuario.repository.ProgramadorDetalleRepository;
import com.example.pry_portafolio_backend.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ProgramadorDetalleRepository programadorDetalleRepository;

    @Override
    public UsuarioResponse buscarUsuario(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UsuarioResponse(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail()
        );
    }

    @Override
    public List<ProgramadorResponse> listarProgramadores() {

        List<ProgramadorDetalle> detalles = programadorDetalleRepository.findByActivo(true);

        return detalles.stream().map(
                d -> new ProgramadorResponse(
                        d.getUsuario().getEmail(),
                        d.getUsuario().getNombre(),
                        d.getUsuario().getApellido(),
                        d.getEspecialidad(),
                        d.getBiografiaBreve(),
                        d.getTelefono(),
                        d.getLinkLinkedin(),
                        d.getLinkGithub(),
                        d.getFotoPerfilUrl()
                )
        ).toList();
    }


    @Override
    @Transactional
    public ProgramadorResponse agregarProgramador(ProgramadorRequest request) {

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Error al encontrar al Usuario."));

        if (programadorDetalleRepository.existsByUsuario(usuario)) {
            throw new RuntimeException("El programador ya existe");
        }

        usuario.setRol(Role.DEV);
        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuarioRepository.save(usuario);

        ProgramadorDetalle programadorDetalle = new ProgramadorDetalle();
        programadorDetalle.setUsuario(usuario);
        programadorDetalle.setEspecialidad(request.especialidad());
        programadorDetalle.setBiografiaBreve(request.biografia());
        programadorDetalle.setTelefono(request.telefono());
        programadorDetalle.setLinkLinkedin(request.linkLinkedin());
        programadorDetalle.setLinkGithub(request.linkGithub());
        programadorDetalle.setFotoPerfilUrl(request.fotoPerfilUrl());

        programadorDetalleRepository.save(programadorDetalle);

        return new ProgramadorResponse(
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                programadorDetalle.getEspecialidad(),
                programadorDetalle.getBiografiaBreve(),
                programadorDetalle.getTelefono(),
                programadorDetalle.getLinkLinkedin(),
                programadorDetalle.getLinkGithub(),
                programadorDetalle.getFotoPerfilUrl()
        );
    }


    @Override
    @Transactional
    public ProgramadorResponse actualizarProgramador(String email, ProgramadorResponse request) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error al encontrar al usuario"));

        ProgramadorDetalle programadorDetalle =  programadorDetalleRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Error: No se encuantra el perfil programador para este usuario"));

        // Validar que el email que se quiere actualizar no se encuentre registrado en la BD
        if (!usuario.getEmail().equals(request.email())) {
            if (usuarioRepository.existsByEmail((request.email()))) {
                throw new RuntimeException("Error: El email " + request.email() + " ya está registrado por otro usuario");
            }
            usuario.setEmail(request.email());
        }

        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        usuario.setEmail(request.email());

        usuarioRepository.save(usuario);

        programadorDetalle.setUsuario(usuario);
        programadorDetalle.setEspecialidad(request.especialidad());
        programadorDetalle.setBiografiaBreve(request.biografia());
        programadorDetalle.setTelefono(request.telefono());
        programadorDetalle.setLinkLinkedin(request.linkLinkedin());
        programadorDetalle.setLinkGithub(request.linkGithub());
        programadorDetalle.setFotoPerfilUrl(request.fotoPerfilUrl());

        programadorDetalleRepository.save(programadorDetalle);


        return new ProgramadorResponse(
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                programadorDetalle.getEspecialidad(),
                programadorDetalle.getBiografiaBreve(),
                programadorDetalle.getTelefono(),
                programadorDetalle.getLinkLinkedin(),
                programadorDetalle.getLinkGithub(),
                programadorDetalle.getFotoPerfilUrl()
        );
    }

    @Override
    @Transactional
    public void eliminarProgramador(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Error al encontrar el email del programador"));

        ProgramadorDetalle programadorDetalle = programadorDetalleRepository.findByUsuario(usuario)
                        .orElseThrow(() -> new RuntimeException("El programador no existe"));

        programadorDetalle.setActivo(false);
        programadorDetalleRepository.save(programadorDetalle);
    }


    @Override
    public void recuperarProgramador(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Error al eliminar el usuario"));

        ProgramadorDetalle programadorDetalle = programadorDetalleRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("El programador no existe"));

        programadorDetalle.setActivo(true);
        programadorDetalleRepository.save(programadorDetalle);
    }
}