package dev.kellyson.alexandriabank.cliente.conta;

import dev.kellyson.alexandriabank.cliente.conta.dto.AporteSimuladoRequest;
import dev.kellyson.alexandriabank.cliente.conta.dto.SaldoResponse;
import dev.kellyson.alexandriabank.cliente.transacao.dto.ItemExtratoResponse;
import dev.kellyson.alexandriabank.doc.ContaControllerDoc;
import dev.kellyson.alexandriabank.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController implements ContaControllerDoc {

    private final ContaService contaService;

    @GetMapping("/saldo")
    public ResponseEntity<SaldoResponse> consultarSaldo(
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(contaService.consultarSaldo(usuario));
    }

    @GetMapping("/extrato")
    public ResponseEntity<List<ItemExtratoResponse>> consultarExtrato(
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(contaService.consultarExtrato(usuario));
    }

    @PostMapping("/aportes-simulados")
    public ResponseEntity<Void> realizarAporteSimulado(
            @AuthenticationPrincipal Usuario usuario,
            @RequestBody @Valid AporteSimuladoRequest request) {
        contaService.realizarAporteSimulado(usuario, request);
        return ResponseEntity.noContent().build();
    }
}
