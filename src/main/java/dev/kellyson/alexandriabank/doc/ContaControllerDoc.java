package dev.kellyson.alexandriabank.doc;

import dev.kellyson.alexandriabank.cliente.conta.dto.AporteSimuladoRequest;
import dev.kellyson.alexandriabank.cliente.conta.dto.SaldoResponse;
import dev.kellyson.alexandriabank.cliente.transacao.dto.ItemExtratoResponse;
import dev.kellyson.alexandriabank.exception.dto.ErrorResponse;
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

import java.util.List;

@Tag(name = "Contas", description = "Operacoes do cliente sobre sua conta bancaria")
public interface ContaControllerDoc {

    @Operation(summary = "Consultar saldo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo consultado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para CLIENTE"),
            @ApiResponse(responseCode = "404", description = "Conta nao encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<SaldoResponse> consultarSaldo(
            @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario);

    @Operation(summary = "Consultar extrato da conta",
            description = "Lista todas as movimentacoes da conta em ordem decrescente de data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extrato da conta consultado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para CLIENTE"),
            @ApiResponse(responseCode = "404", description = "Conta nao encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<ItemExtratoResponse>> consultarExtrato(
            @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario);

    @Operation(summary = "Realizar aporte simulado", description = "Adiciona saldo e registra a movimentacao na conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aporte realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor invalido",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para CLIENTE"),
            @ApiResponse(responseCode = "404", description = "Conta nao encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Conta nao esta ativa",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> realizarAporteSimulado(
            @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Valor do aporte simulado", required = true)
            @RequestBody @Valid AporteSimuladoRequest request);
}
