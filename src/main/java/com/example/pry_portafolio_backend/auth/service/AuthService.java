package com.example.pry_portafolio_backend.auth.service;

import com.example.pry_portafolio_backend.auth.controller.LoginRequest;
import com.example.pry_portafolio_backend.auth.controller.RegisterRequest;
import com.example.pry_portafolio_backend.auth.controller.TokenResponse;
import com.example.pry_portafolio_backend.auth.repository.Token;
import com.example.pry_portafolio_backend.auth.repository.TokenRepository;
import com.example.pry_portafolio_backend.usuario.Role;
import com.example.pry_portafolio_backend.usuario.Usuario;
import com.example.pry_portafolio_backend.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request){

        // ELIMINADO: Toda la lógica de buscar el rol en la DB
        // Rol defaultRol = rolRepository.findByNombre("ROLE_USER")...

        var user = Usuario.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .activo(true)
                .rol(Role.USER) // <--- ASIGNACIÓN DIRECTA DEL ENUM
                .build();

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        savedUserToken(savedUser, jwtToken); // Guarda el token del registro

        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (Exception e) {
            System.out.println("ERROR DE AUTENTICACION: " + e.getMessage());
            throw e;
        }

        var usuario = userRepository.findByEmail(request.email())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(usuario);
        var refreshToken = jwtService.generateRefreshToken(usuario);

        revokeAllUserTokens(usuario); // 1. Mata los tokens viejos
        savedUserToken(usuario, jwtToken); // 2. IMPORTANTE: Guarda el nuevo token (Esto faltaba antes)

        return new TokenResponse(jwtToken, refreshToken);
    }

    private void revokeAllUserTokens(final Usuario usuario) {
        final List<Token> validUserTokens = tokenRepository
                .findAllValidTokensByUsuarioId(usuario.getId());
        if (!validUserTokens.isEmpty()){
            for (final Token token: validUserTokens){
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    private void savedUserToken(Usuario usuario, String jwtToken){
        var token = Token.builder()
                .usuario(usuario)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public TokenResponse refreshToken(final String authHeader){
        // Aquí implementarás la lógica de refresh token luego
        return null;
    }
}