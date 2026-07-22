package dev.kellyson.alexandriabank.administrador;

import dev.kellyson.alexandriabank.administrador.dto.CartaoAdministrativoResponse;
import dev.kellyson.alexandriabank.administrador.dto.ClienteAdministrativoResponse;
import dev.kellyson.alexandriabank.administrador.dto.ContaAdministrativaResponse;
import dev.kellyson.alexandriabank.doc.AdministradorControllerDoc;
import dev.kellyson.alexandriabank.cliente.transacao.dto.ItemExtratoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
@RequiredArgsConstructor
public class AdministradorController implements AdministradorControllerDoc {

    private final AdministradorService administradorService;

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteAdministrativoResponse>> consultarClientes() {
        return ResponseEntity.ok(administradorService.consultarClientes());
    }

    @GetMapping("/contas/{contaId}")
    public ResponseEntity<ContaAdministrativaResponse> consultarConta(
            @PathVariable Long contaId) {
        return ResponseEntity.ok(administradorService.consultarConta(contaId));
    }

    @PatchMapping("/contas/{contaId}/bloqueio")
    public ResponseEntity<Void> bloquearConta(@PathVariable Long contaId) {
        administradorService.bloquearConta(contaId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/contas/{contaId}/desbloqueio")
    public ResponseEntity<Void> desbloquearConta(@PathVariable Long contaId) {
        administradorService.desbloquearConta(contaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/contas/{contaId}/movimentacoes")
    public ResponseEntity<List<ItemExtratoResponse>> consultarMovimentacoes(
            @PathVariable Long contaId) {
        return ResponseEntity.ok(administradorService.consultarMovimentacoes(contaId));
    }

    @GetMapping("/cartoes")
    public ResponseEntity<List<CartaoAdministrativoResponse>> consultarCartoes() {
        return ResponseEntity.ok(administradorService.consultarCartoes());
    }

    @PatchMapping("/cartoes/{cartaoId}/bloqueio")
    public ResponseEntity<Void> bloquearCartao(@PathVariable Long cartaoId) {
        administradorService.bloquearCartao(cartaoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cartoes/{cartaoId}/desbloqueio")
    public ResponseEntity<Void> desbloquearCartao(@PathVariable Long cartaoId) {
        administradorService.desbloquearCartao(cartaoId);
        return ResponseEntity.noContent().build();
    }
}
