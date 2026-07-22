package dev.kellyson.alexandriabank.cliente.cartao;

import dev.kellyson.alexandriabank.cliente.transacao.dto.ItemExtratoResponse;
import dev.kellyson.alexandriabank.doc.CartaoControllerDoc;
import dev.kellyson.alexandriabank.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contas/cartao")
@RequiredArgsConstructor
public class CartaoController implements CartaoControllerDoc {

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

    @GetMapping("/extrato")
    public ResponseEntity<List<ItemExtratoResponse>> consultarExtrato(
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(cartaoService.consultarExtrato(usuario));
    }

}
