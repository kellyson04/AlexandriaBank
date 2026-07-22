package dev.kellyson.alexandriabank.cliente.transacao;

import dev.kellyson.alexandriabank.doc.TransacaoControllerDoc;
import dev.kellyson.alexandriabank.cliente.transacao.dto.RealizarPixRequest;
import dev.kellyson.alexandriabank.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController implements TransacaoControllerDoc {

    private final TransacaoService transacaoService;

    @PostMapping("/pix")
    public ResponseEntity<Void> realizarPix(@AuthenticationPrincipal Usuario usuario,
                                            @RequestBody @Valid RealizarPixRequest request) {
        transacaoService.realizarPix(usuario, request.idContaDestino(), request.valor());
        return ResponseEntity.noContent().build();
    }
}
