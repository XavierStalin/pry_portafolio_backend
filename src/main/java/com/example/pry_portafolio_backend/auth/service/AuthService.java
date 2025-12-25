package com.example.pry_portafolio_backend.auth.service;

import com.example.pry_portafolio_backend.auth.controller.LoginRequest;
import com.example.pry_portafolio_backend.auth.controller.RegisterRequest;
import com.example.pry_portafolio_backend.auth.controller.TokenResponse;
import com.example.pry_portafolio_backend.auth.repository.Token;
import com.example.pry_portafolio_backend.auth.repository.TokenRepository;
import com.example.pry_portafolio_backend.entidades_negocio.Rol;
import com.example.pry_portafolio_backend.usuario.RolRepository;
import com.example.pry_portafolio_backend.usuario.Usuario;
import com.example.pry_portafolio_backend.usuario.UsuarioRepository;
import com.example.pry_portafolio_backend.usuario.UsuarioService;
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
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;

    public TokenResponse register(RegisterRequest request){

        Rol defaultRol = rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("El rol USER no existe en la DB. Insértalo manualmente o verifica el nombre."));

        var user = Usuario.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .activo(true)
                .rol(defaultRol)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        savedUserToken(savedUser, jwtToken);
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
        revokeAllUserTokens(usuario);
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

    private void savedUserToken(Usuario usuario,String jwtToken){
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
        return null;
    }

}
