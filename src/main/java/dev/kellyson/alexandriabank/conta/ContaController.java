package dev.kellyson.alexandriabank.conta;

import dev.kellyson.alexandriabank.conta.dto.AporteSimuladoRequest;
import dev.kellyson.alexandriabank.conta.dto.SaldoResponse;
import dev.kellyson.alexandriabank.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @GetMapping("/saldo")
    public ResponseEntity<SaldoResponse> consultarSaldo(
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(contaService.consultarSaldo(usuario));
    }

    @PostMapping("/aportes-simulados")
    public ResponseEntity<Void> realizarAporteSimulado(
            @AuthenticationPrincipal Usuario usuario,
            @RequestBody @Valid AporteSimuladoRequest request) {
        contaService.realizarAporteSimulado(usuario, request);
        return ResponseEntity.noContent().build();
    }



}
