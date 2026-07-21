package dev.kellyson.alexandriabank.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TokenService {

    @Value("${security.jwt.secret}")
    private String chave;

    @Value("${security.jwt.expiration}")
    private Long tempoExpiracao;

    public String gerarToken(Authentication autenticacao) {
        UserDetails usuario = (UserDetails) autenticacao.getPrincipal();

        return construirToken(usuario.getUsername());
    }

    private String construirToken(String nomeUsuario) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + tempoExpiracao);

        return Jwts.builder()
                .subject(nomeUsuario)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(obterChaveAssinatura())
                .compact();
    }

    private SecretKey obterChaveAssinatura() {
        return Keys.hmacShaKeyFor(chave.getBytes(StandardCharsets.UTF_8));
    }

    public boolean tokenValido(String token) {
        try {
            extrairClaims(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(obterChaveAssinatura())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extrairNomeUsuario(String token) {
        return extrairClaims(token).getSubject();
    }
}
