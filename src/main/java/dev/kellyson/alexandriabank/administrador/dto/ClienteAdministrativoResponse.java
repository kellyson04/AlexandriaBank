package dev.kellyson.alexandriabank.administrador.dto;

import dev.kellyson.alexandriabank.cliente.conta.Conta;

import java.time.Instant;

public record ClienteAdministrativoResponse(
        Long id,
        Long contaId,
        String nome,
        String cpf,
        String email,
        Instant criadoEm
) {

    public static ClienteAdministrativoResponse from(Conta conta) {
        return new ClienteAdministrativoResponse(
                conta.getUsuario().getId(),
                conta.getId(),
                conta.getUsuario().getNome(),
                conta.getUsuario().getCpf(),
                conta.getUsuario().getEmail(),
                conta.getUsuario().getCriadoEm()
        );
    }
}
