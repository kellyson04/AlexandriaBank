package dev.kellyson.alexandriabank.doc;

import dev.kellyson.alexandriabank.cliente.dto.ClienteCriadoResponse;
import dev.kellyson.alexandriabank.cliente.dto.CriarClienteRequest;
import dev.kellyson.alexandriabank.exception.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Clientes", description = "Operacoes para cadastro de clientes")
public interface ClienteControllerDoc {

    @Operation(summary = "Cadastrar cliente", description = "Cadastra um cliente e abre sua conta bancaria")
    @SecurityRequirements
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos na requisicao",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "CPF ou e-mail ja cadastrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<ClienteCriadoResponse> criarCliente(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do cliente a ser cadastrado", required = true)
            @RequestBody @Valid CriarClienteRequest clienteRequest);
}
