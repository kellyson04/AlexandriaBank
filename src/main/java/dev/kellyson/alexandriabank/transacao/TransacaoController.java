package dev.kellyson.alexandriabank.transacao;

import dev.kellyson.alexandriabank.transacao.dto.ItemExtratoResponse;
import dev.kellyson.alexandriabank.transacao.dto.RealizarPixRequest;
import dev.kellyson.alexandriabank.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping("/extrato")
    public ResponseEntity<List<ItemExtratoResponse>> consultarExtrato(
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(transacaoService.consultarExtrato(usuario));
    }

    @PostMapping("/pix")
    public ResponseEntity<Void> realizarPix(@AuthenticationPrincipal Usuario usuario,
                                            @RequestBody @Valid RealizarPixRequest request) {
        transacaoService.realizarPix(usuario, request.idContaDestino(), request.valor());
        return ResponseEntity.noContent().build();
    }
}
