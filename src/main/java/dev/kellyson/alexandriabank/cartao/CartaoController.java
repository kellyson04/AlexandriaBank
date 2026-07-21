package dev.kellyson.alexandriabank.cartao;

import dev.kellyson.alexandriabank.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas/cartao")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<Void> solicitarCartao(
            @AuthenticationPrincipal Usuario usuario) {
        cartaoService.solicitarCartao(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/bloqueio")
    public ResponseEntity<Void> bloquearCartao(
            @AuthenticationPrincipal Usuario usuario) {
        cartaoService.bloquearCartao(usuario);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/desbloqueio")
    public ResponseEntity<Void> desbloquearCartao(
            @AuthenticationPrincipal Usuario usuario) {
        cartaoService.desbloquearCartao(usuario);

        return ResponseEntity.noContent().build();
    }
}
