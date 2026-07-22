package dev.kellyson.alexandriabank.administrador.dto;

import dev.kellyson.alexandriabank.cliente.conta.Conta;
import dev.kellyson.alexandriabank.cliente.conta.StatusConta;

import java.math.BigDecimal;
import java.time.Instant;

public record ContaAdministrativaResponse(
        Long id,
        Long clienteId,
        String nomeCliente,
        BigDecimal saldo,
        StatusConta status,
        Instant criadaEm
) {

    public static ContaAdministrativaResponse from(Conta conta) {
        return new ContaAdministrativaResponse(
                conta.getId(),
                conta.getUsuario().getId(),
                conta.getUsuario().getNome(),
                conta.getSaldo(),
                conta.getStatus(),
                conta.getCriadaEm()
        );
    }
}
