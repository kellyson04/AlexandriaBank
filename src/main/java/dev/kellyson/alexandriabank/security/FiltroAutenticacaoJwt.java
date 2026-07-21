package dev.kellyson.alexandriabank.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FiltroAutenticacaoJwt extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AutenticacaoService autenticacaoService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String cabecalhoAutorizacao = request.getHeader("Authorization");

        if (!StringUtils.hasText(cabecalhoAutorizacao)
                || !cabecalhoAutorizacao.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = cabecalhoAutorizacao.substring(7);

        if (tokenService.tokenValido(token)) {
            String nomeUsuario = tokenService.extrairNomeUsuario(token);

            UserDetails usuario = autenticacaoService.loadUserByUsername(nomeUsuario);

            UsernamePasswordAuthenticationToken tokenAutenticacao =
                    new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(tokenAutenticacao);
        }

        filterChain.doFilter(request, response);
    }
}
