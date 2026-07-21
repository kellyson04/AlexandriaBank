package dev.kellyson.alexandriabank.autenticacao;

import dev.kellyson.alexandriabank.autenticacao.dto.LoginRequest;
import dev.kellyson.alexandriabank.autenticacao.dto.LoginResponse;
import dev.kellyson.alexandriabank.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken tokenAutenticacao =
                new UsernamePasswordAuthenticationToken(request.email(), request.senha());

        try {
            Authentication usuarioAutenticado = authenticationManager.authenticate(tokenAutenticacao);

            String token = tokenService.gerarToken(usuarioAutenticado);

            return new LoginResponse(token);
        } catch (BadCredentialsException exception) {
            throw new RuntimeException("Credenciais invalidas");
        }
    }
}
