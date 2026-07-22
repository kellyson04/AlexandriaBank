package dev.kellyson.alexandriabank.doc;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Tag(name = "Cartoes", description = "Operacoes do cliente sobre seu cartao")
public interface CartaoControllerDoc {

    @Operation(summary = "Solicitar cartao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cartao solicitado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para CLIENTE"),
            @ApiResponse(responseCode = "404", description = "Conta nao encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "A conta ja possui um cartao",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "A conta precisa estar ativa",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> solicitarCartao(
            @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario);

    @Operation(summary = "Bloquear cartao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cartao bloqueado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para CLIENTE"),
            @ApiResponse(responseCode = "404", description = "Cartao nao encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Cartao ja esta bloqueado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> bloquearCartao(
            @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario);

    @Operation(summary = "Desbloquear cartao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cartao desbloqueado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para CLIENTE"),
            @ApiResponse(responseCode = "404", description = "Cartao nao encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Cartao ja esta ativo",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> desbloquearCartao(
            @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario);

    @Operation(summary = "Consultar extrato do cartao",
            description = "Lista somente as movimentacoes vinculadas ao cartao em ordem decrescente de data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Extrato do cartao consultado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para CLIENTE"),
            @ApiResponse(responseCode = "404", description = "Cartao nao encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<ItemExtratoResponse>> consultarExtrato(
            @Parameter(hidden = true) @AuthenticationPrincipal Usuario usuario);
}
