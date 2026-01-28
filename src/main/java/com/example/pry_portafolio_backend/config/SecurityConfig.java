package com.example.pry_portafolio_backend.config;

import com.example.pry_portafolio_backend.auth.repository.TokenRepository;
import com.example.pry_portafolio_backend.auth.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // <--- IMPORTANTE
import org.springframework.web.cors.CorsConfigurationSource; // <--- IMPORTANTE
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // <--- IMPORTANTE

import java.util.List; // <--- IMPORTANTE

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final TokenRepository tokenRepository;
    private final LogoutService logoutService;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                // 1. ACTIVAR CORS (Esto busca el bean corsConfigurationSource definido abajo)
                .cors(Customizer.withDefaults())

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        // 2. RUTAS PÚBLICAS
                        req.requestMatchers("/auth/**").permitAll()
                                .requestMatchers(
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/webjars/**"
                                ).permitAll()

                                // 3. PERMISOS DE USUARIOS
                                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/usuarios/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")

                                // 4. PERMISOS DE NOTIFICACIONES
                                .requestMatchers(HttpMethod.GET, "/api/notificaciones/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/notificaciones/**").hasAnyRole("ADMIN", "DEV")
                                .requestMatchers(HttpMethod.PUT, "/api/notificaciones/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/notificaciones/**").hasRole("ADMIN")

                                // 5. PERMISOS DE ASESORIAS
                                .requestMatchers(HttpMethod.GET, "/api/asesorias/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/asesorias/**").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/api/asesorias/*/responder").hasRole("DEV")
                                .requestMatchers(HttpMethod.PUT, "/api/asesorias/**").hasAnyRole("ADMIN", "DEV")
                                .requestMatchers(HttpMethod.DELETE, "/api/asesorias/**").hasAnyRole("ADMIN", "USER")

                                // 6. OTRAS RUTAS
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/programador/**").hasRole("DEV")

                                // 7. CUALQUIER OTRA
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutService)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }

    // --- NUEVO BEAN DE CONFIGURACIÓN CORS ---
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // AQUI: Pon la URL de tu Angular. Si usas Vite es 5173, si es Angular CLI es 4200.
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:5173"));

        // Métodos permitidos (OPTIONS es vital para el preflight 403)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Cabeceras permitidas
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        // Permitir credenciales (cookies/tokens) si fuera necesario
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}