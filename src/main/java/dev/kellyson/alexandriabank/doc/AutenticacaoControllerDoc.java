package dev.kellyson.alexandriabank.doc;

import dev.kellyson.alexandriabank.autenticacao.dto.LoginRequest;
import dev.kellyson.alexandriabank.autenticacao.dto.LoginResponse;
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

@Tag(name = "Autenticacao", description = "Operacoes para autenticacao na API")
public interface AutenticacaoControllerDoc {

    @Operation(summary = "Realizar login", description = "Autentica o usuario e retorna um token JWT")
    @SecurityRequirements
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos na requisicao",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "E-mail ou senha invalidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<LoginResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "E-mail e senha do usuario", required = true)
            @RequestBody @Valid LoginRequest request);
}
