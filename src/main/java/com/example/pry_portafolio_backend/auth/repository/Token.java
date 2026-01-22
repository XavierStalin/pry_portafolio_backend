package com.example.pry_portafolio_backend.auth.repository;

import com.example.pry_portafolio_backend.usuario.entity.Usuario;
import jakarta.persistence.*;

@Entity(name = "PW_Tokens")
public class Token {

    public enum TokenType{
        BEARER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 500)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Token() {}

    public Token(Long id, String token, TokenType tokenType, boolean revoked, boolean expired, Usuario usuario) {
        this.id = id;
        this.token = token;
        this.tokenType = tokenType;
        this.revoked = revoked;
        this.expired = expired;
        this.usuario = usuario;
    }

    public static TokenBuilder builder() {
        return new TokenBuilder();
    }

    public static class TokenBuilder {
        private String token;
        private TokenType tokenType = TokenType.BEARER;
        private boolean revoked;
        private boolean expired;
        private Usuario usuario;

        public TokenBuilder token(String token) { this.token = token; return this; }
        public TokenBuilder tokenType(TokenType tokenType) { this.tokenType = tokenType; return this; }
        public TokenBuilder revoked(boolean revoked) { this.revoked = revoked; return this; }
        public TokenBuilder expired(boolean expired) { this.expired = expired; return this; }
        public TokenBuilder usuario(Usuario usuario) { this.usuario = usuario; return this; }
        public Token build() {
            Token t = new Token();
            t.setToken(token);
            t.setTokenType(tokenType);
            t.setRevoked(revoked);
            t.setExpired(expired);
            t.setUsuario(usuario);
            return t;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public TokenType getTokenType() { return tokenType; }
    public void setTokenType(TokenType tokenType) { this.tokenType = tokenType; return; }
    public boolean isRevoked() { return revoked; }
    public void setRevoked(boolean revoked) { this.revoked = revoked; }
    public boolean isExpired() { return expired; }
    public void setExpired(boolean expired) { this.expired = expired; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
