package com.example.pry_portafolio_backend.usuario.service;

import com.example.pry_portafolio_backend.usuario.dto.ProgramadorRequest;
import com.example.pry_portafolio_backend.usuario.dto.ProgramadorResponse;
import com.example.pry_portafolio_backend.usuario.dto.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse buscarUsuario(String email);

    List<ProgramadorResponse> listarProgramadores();

    ProgramadorResponse agregarProgramador(ProgramadorRequest request);

    ProgramadorResponse actualizarProgramador(String email, ProgramadorResponse request);

    void eliminarProgramador(String email);

    void recuperarProgramador(String email);

}
