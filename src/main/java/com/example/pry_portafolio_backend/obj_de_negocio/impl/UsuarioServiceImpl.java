package com.example.pry_portafolio_backend.obj_de_negocio.impl;

import com.example.pry_portafolio_backend.dto.RegistroUsuarioDTO;
import com.example.pry_portafolio_backend.entidades_negocio.Rol;
import com.example.pry_portafolio_backend.entidades_negocio.Usuario;
import com.example.pry_portafolio_backend.obj_acceso_datos.RolRepository;
import com.example.pry_portafolio_backend.obj_acceso_datos.UsuarioRepository;
import com.example.pry_portafolio_backend.obj_de_negocio.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;


    @Override
    public Usuario crearUsuario(RegistroUsuarioDTO datos) {


        if (usuarioRepository.existsUsuarioByEmail(datos.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(datos.getNombre());
        nuevoUsuario.setApellido(datos.getApellido());
        nuevoUsuario.setEmail(datos.getEmail());
        nuevoUsuario.setPassword(datos.getPassword());

        nuevoUsuario.setActivo(true);

        Rol rolCliente = rolRepository.findByNombre("USER");
        nuevoUsuario.setRol(rolCliente);

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
    //@Transactional
    public void eliminarUsuario(Integer id) {

        Usuario usuario = usuarioRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Error al eliminar el usuario"));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}
