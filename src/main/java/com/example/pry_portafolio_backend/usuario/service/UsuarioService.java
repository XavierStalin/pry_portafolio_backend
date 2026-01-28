package com.example.pry_portafolio_backend.usuario.service;

import com.example.pry_portafolio_backend.usuario.dto.UsuarioRequest;
import com.example.pry_portafolio_backend.usuario.dto.UsuarioResponse;
import java.util.List;

public interface UsuarioService {
    UsuarioResponse guardarUsuario(UsuarioRequest request);
    List<UsuarioResponse> listarUsuarios();
    UsuarioResponse buscarUsuario(String email);
    UsuarioResponse buscarPorId(Integer id);
    UsuarioResponse actualizarUsuario(Integer id, UsuarioRequest request);
    void eliminarUsuario(Integer id);
}