package dev.kellyson.alexandriabank.administrador.dto;

import dev.kellyson.alexandriabank.cliente.cartao.Cartao;
import dev.kellyson.alexandriabank.cliente.cartao.StatusCartao;

import java.time.Instant;
import java.time.LocalDate;

public record CartaoAdministrativoResponse(
        Long id,
        Long contaId,
        String numeroMascarado,
        String nomeImpresso,
        LocalDate dataValidade,
        StatusCartao status,
        Instant dataSolicitacao
) {

    public static CartaoAdministrativoResponse from(Cartao cartao) {
        return new CartaoAdministrativoResponse(
                cartao.getId(),
                cartao.getConta().getId(),
                mascararNumero(cartao.getNumero()),
                cartao.getNomeImpresso(),
                cartao.getDataValidade(),
                cartao.getStatus(),
                cartao.getDataSolicitacao()
        );
    }

    private static String mascararNumero(String numero) {
        return "**** **** **** " + numero.substring(numero.length() - 4);
    }
}
