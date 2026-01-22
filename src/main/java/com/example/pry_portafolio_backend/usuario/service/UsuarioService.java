package com.example.pry_portafolio_backend.usuario.service;

import com.example.pry_portafolio_backend.usuario.dto.UsuarioRequest;
import com.example.pry_portafolio_backend.usuario.dto.UsuarioResponse;

public interface UsuarioService {

    UsuarioResponse buscarUsuario(String email);

    UsuarioResponse guardarUsuario(UsuarioRequest request);

}
