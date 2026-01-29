package com.example.pry_portafolio_backend.auth.service;

import com.example.pry_portafolio_backend.auth.controller.LoginRequest;
import com.example.pry_portafolio_backend.auth.controller.RegisterRequest;
import com.example.pry_portafolio_backend.auth.controller.TokenResponse;
import com.example.pry_portafolio_backend.auth.repository.Token;
import com.example.pry_portafolio_backend.auth.repository.TokenRepository;
import com.example.pry_portafolio_backend.usuario.entity.Role;
import com.example.pry_portafolio_backend.usuario.entity.AuthProvider;
import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import com.example.pry_portafolio_backend.usuario.repository.UsuarioRepository;
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


    public TokenResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }

        var user = Usuario.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .rol(Role.USER)
                .authProvider(AuthProvider.LOCAL)
                .build();

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        savedUserToken(savedUser, jwtToken);// Guardamos el token de registro
        savedUserToken(savedUser, refreshToken); // Guardamos el refresh token generado

        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
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
        savedUserToken(usuario, jwtToken);

        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse loginWithFirebase(String idToken) {
        try {
            var decoded = com.google.firebase.auth.FirebaseAuth.getInstance().verifyIdToken(idToken);

            String email = decoded.getEmail();
            String name = (String) decoded.getClaims().getOrDefault("name", "Usuario Google");

            if (email == null || email.isBlank()) {
                throw new RuntimeException("Firebase token no contiene email");
            }

            var existingOpt = userRepository.findByEmail(email);
            Usuario usuario;

            if (existingOpt.isPresent()) {
                usuario = existingOpt.get();

                if (usuario.getAuthProvider() == AuthProvider.LOCAL) {
                    throw new RuntimeException("Este email está registrado con contraseña. Usa login normal.");
                }

                // Si es GOOGLE, permitimos login
            } else {
                // Crear nuevo usuario GOOGLE
                String[] parts = name.trim().split("\\s+");
                String nombre = parts.length > 0 ? parts[0] : "Usuario";
                String apellido = parts.length > 1 ? String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length)) : "-";

                usuario = Usuario.builder()
                        .nombre(nombre)
                        .apellido(apellido)
                        .email(email)
                        .password(null)
                        .rol(Role.USER)
                        .authProvider(AuthProvider.GOOGLE)
                        .build();

                usuario = userRepository.save(usuario);
            }

            var jwtToken = jwtService.generateToken(usuario);
            var refreshToken = jwtService.generateRefreshToken(usuario);

            revokeAllUserTokens(usuario);
            savedUserToken(usuario, jwtToken);

            return new TokenResponse(jwtToken, refreshToken);

        } catch (com.google.firebase.auth.FirebaseAuthException e) {
            throw new RuntimeException("Token de Firebase inválido o expirado", e);
        }
    }





    private void revokeAllUserTokens(final Usuario usuario) {
        final List<Token> validUserTokens = tokenRepository
                .findAllValidTokensByUsuarioId(usuario.getId());
        if (!validUserTokens.isEmpty()) {
            for (final Token token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
            tokenRepository.saveAll(validUserTokens);
        }
    }

    private void savedUserToken(Usuario usuario, String jwtToken) {
        var token = Token.builder()
                .usuario(usuario)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public TokenResponse refreshToken(final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido");
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var usuario = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            var tokenEnBaseDeDatos = tokenRepository.findByToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("El token no existe en la base de datos"));

            if (tokenEnBaseDeDatos.isExpired() || tokenEnBaseDeDatos.isRevoked()) {
                throw new RuntimeException("El token ha sido revocado o expiró (Logout previo)");
            }

            if (jwtService.isTokenValid(refreshToken, usuario)) {

                var accessToken = jwtService.generateToken(usuario);

                revokeAllUserTokens(usuario);

                savedUserToken(usuario, accessToken);


                var newRefreshToken = jwtService.generateRefreshToken(usuario);

                return new TokenResponse(accessToken, newRefreshToken);
            }
        }

        throw new RuntimeException("Refresh Token inválido o expirado");

    }
}