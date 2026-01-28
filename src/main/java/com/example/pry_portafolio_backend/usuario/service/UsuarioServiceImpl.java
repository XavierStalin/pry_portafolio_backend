package com.example.pry_portafolio_backend.usuario.service;

import com.example.pry_portafolio_backend.usuario.dto.UsuarioRequest;
import com.example.pry_portafolio_backend.usuario.dto.UsuarioResponse;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import com.example.pry_portafolio_backend.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok genera el constructor para los campos 'final'
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
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

        return mapToResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        return mapToResponse(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapToResponse(usuario);
    }

    //por ahora no se puede actualizar el correo, eso es constante
    @Override
    @Transactional
    public UsuarioResponse actualizarUsuario(Integer id, UsuarioRequest request) {
        // Buscar al usuario existente
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        // Actualizar campos básicos
        usuario.setNombre(request.nombre());
        usuario.setApellido(request.apellido());
        // ROL (Ahora sí se verá reflejado en la DB)
        if (request.rol() != null) {
            usuario.setRol(request.rol());
        }
        // Funciona password solo si se envía una nueva
        if (request.password() != null && !request.password().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.password()));
        }
        return mapToResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    // Método privado para evitar repetir código de mapeo (DRY)
    private UsuarioResponse mapToResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail()
        );
    }
}