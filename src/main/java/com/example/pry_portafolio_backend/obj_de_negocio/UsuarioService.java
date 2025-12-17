package com.example.pry_portafolio_backend.obj_de_negocio;

import com.example.pry_portafolio_backend.dto.RegistroUsuarioDTO;
import com.example.pry_portafolio_backend.entidades_negocio.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario crearUsuario(RegistroUsuarioDTO registroUsuarioDTO);

    Usuario obtenerUsuario(Integer id);

    List<Usuario> listarUsuarios();

    void eliminarUsuario(Integer id);

}
