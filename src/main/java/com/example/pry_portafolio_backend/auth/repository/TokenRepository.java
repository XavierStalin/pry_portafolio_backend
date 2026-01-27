package com.example.pry_portafolio_backend.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
  select t from PW_Tokens t inner join Usuario u on t.usuario.id = u.id
      where u.id = :usuarioId and (t.expired = false or t.revoked = false)
    """)
    List<Token> findAllValidTokensByUsuarioId(Integer usuarioId);

    Optional<Token> findByToken(String token);
}
