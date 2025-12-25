package com.example.pry_portafolio_backend.usuario;

import java.util.List;

public interface UsuarioService {

    Usuario crearUsuario(UsuarioResponse registroUsuarioDTO);

    Usuario obtenerUsuario(Integer id);

    List<Usuario> listarUsuarios();

    void eliminarUsuario(Integer id);



}
