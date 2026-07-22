package dev.kellyson.alexandriabank.doc;

import dev.kellyson.alexandriabank.administrador.dto.CartaoAdministrativoResponse;
import dev.kellyson.alexandriabank.administrador.dto.ClienteAdministrativoResponse;
import dev.kellyson.alexandriabank.administrador.dto.ContaAdministrativaResponse;
import dev.kellyson.alexandriabank.exception.dto.ErrorResponse;
import dev.kellyson.alexandriabank.cliente.transacao.dto.ItemExtratoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Administracao", description = "Operacoes exclusivas do perfil ADMIN")
public interface AdministradorControllerDoc {

    @Operation(summary = "Consultar clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes consultados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para ADMIN")
    })
    ResponseEntity<List<ClienteAdministrativoResponse>> consultarClientes();

    @Operation(summary = "Consultar dados de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta consultada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para ADMIN"),
            @ApiResponse(responseCode = "404", description = "Conta nao encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ContaAdministrativaResponse> consultarConta(
            @Parameter(description = "ID da conta", required = true) @PathVariable Long contaId);

    @Operation(summary = "Bloquear conta de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta bloqueada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para ADMIN"),
            @ApiResponse(responseCode = "404", description = "Conta nao encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conta ja esta bloqueada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Conta encerrada nao pode ser bloqueada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> bloquearConta(
            @Parameter(description = "ID da conta", required = true) @PathVariable Long contaId);

    @Operation(summary = "Desbloquear conta de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta desbloqueada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para ADMIN"),
            @ApiResponse(responseCode = "404", description = "Conta nao encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conta ja esta ativa",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "422", description = "Conta encerrada nao pode ser desbloqueada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> desbloquearConta(
            @Parameter(description = "ID da conta", required = true) @PathVariable Long contaId);

    @Operation(summary = "Consultar movimentacoes de uma conta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimentacoes consultadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para ADMIN"),
            @ApiResponse(responseCode = "404", description = "Conta nao encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<List<ItemExtratoResponse>> consultarMovimentacoes(
            @Parameter(description = "ID da conta", required = true) @PathVariable Long contaId);

    @Operation(summary = "Consultar cartoes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cartoes consultados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para ADMIN")
    })
    ResponseEntity<List<CartaoAdministrativoResponse>> consultarCartoes();

    @Operation(summary = "Bloquear cartao de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cartao bloqueado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para ADMIN"),
            @ApiResponse(responseCode = "404", description = "Cartao nao encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Cartao ja esta bloqueado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> bloquearCartao(
            @Parameter(description = "ID do cartao", required = true) @PathVariable Long cartaoId);

    @Operation(summary = "Desbloquear cartao de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cartao desbloqueado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuario nao autenticado"),
            @ApiResponse(responseCode = "403", description = "Acesso permitido somente para ADMIN"),
            @ApiResponse(responseCode = "404", description = "Cartao nao encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Cartao ja esta ativo",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<Void> desbloquearCartao(
            @Parameter(description = "ID do cartao", required = true) @PathVariable Long cartaoId);
}
