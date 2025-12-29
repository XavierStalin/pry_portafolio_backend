package com.example.pry_portafolio_backend.usuario;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    // private final RolRepository rolRepository; // <--- ELIMINADO (Ya no hace falta)
    private final PasswordEncoder passwordEncoder; // <--- AGREGADO (Para que el login funcione)

    @Override
    public Usuario crearUsuario(final UsuarioResponse datos) {

        if (usuarioRepository.existsUsuarioByEmail(datos.email())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(datos.nombre());
        nuevoUsuario.setApellido(datos.apellido());
        nuevoUsuario.setEmail(datos.email());

        // IMPORTANTE: Encriptar password, si no, este usuario no podrá hacer Login
        nuevoUsuario.setPassword(passwordEncoder.encode(datos.password()));

        nuevoUsuario.setActivo(true);

        // --- CAMBIO PRINCIPAL AQUI ---
        // Ya no buscas en DB, asignas el valor directo del Enum
        nuevoUsuario.setRol(Role.USER);

        return usuarioRepository.save(nuevoUsuario);
    }

    @Override
    public Usuario obtenerUsuario(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no se encuentra en la base de datos"));
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findUsuariosByActivo(true);
    }

    @Override
    public void eliminarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Error al eliminar el usuario"));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}