package dev.kellyson.alexandriabank.doc;

import dev.kellyson.alexandriabank.exception.dto.ErrorResponse;
import dev.kellyson.alexandriabank.cliente.transacao.dto.RealizarPixRequest;
import dev.kellyson.alexandriabank.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Transacoes", description = "Operacoes de transferencia do cliente")
public interface TransacaoControllerDoc {

    @Operation(summary = "Realizar Pix", description = "Transfere saldo entre contas e registra as duas movimentacoes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pix realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor invalido ou conta de destino igual a conta de origem",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para CLIENTE"),
            @ApiResponse(responseCode = "404", description = "Conta de origem ou destino nao encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Saldo insuficiente ou conta nao esta ativa",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> realizarPix(
            @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Conta de destino e valor da transferencia", required = true)
            @RequestBody @Valid RealizarPixRequest request);
}
