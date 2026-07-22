package dev.kellyson.alexandriabank.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Ambiente local")
                ))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Informe o token JWT retornado no login")
                        ))
                .info(new Info()
                        .title("Alexandria Bank API")
                        .version("1.0")
                        .description("""
                                API REST de um banco digital ficticio.

                                Recursos disponiveis:
                                - cadastro e autenticacao de clientes;
                                - consulta de saldo e aporte simulado;
                                - realizacao de Pix e consulta de extrato;
                                - solicitacao, bloqueio e desbloqueio de cartao;
                                - administracao de clientes, contas, movimentacoes e cartoes.

                                Fluxo para testar rotas protegidas:
                                1. Crie um cliente em POST /clientes.
                                2. Faca login em POST /autenticacao/login.
                                3. Copie o token JWT retornado.
                                4. Clique em Authorize e informe o token.

                                Perfis utilizados: ADMIN e CLIENTE.
                                """)
                        .contact(new Contact()
                                .name("Kellyson Lopes")
                                .url("https://github.com/kellyson04")
                        )
                );
    }
}
