package com.example.pry_portafolio_backend.usuario.service;

import com.example.pry_portafolio_backend.usuario.dto.UsuarioRequest;
import com.example.pry_portafolio_backend.usuario.dto.UsuarioResponse;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import com.example.pry_portafolio_backend.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
    public UsuarioResponse guardarUsuario(UsuarioRequest request) {

        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .rol(request.rol())
                .build();

        Usuario savedUser = usuarioRepository.save(usuario);

        return new UsuarioResponse(
                savedUser.getNombre(),
                savedUser.getApellido(),
                savedUser.getEmail()
        );
    }
}